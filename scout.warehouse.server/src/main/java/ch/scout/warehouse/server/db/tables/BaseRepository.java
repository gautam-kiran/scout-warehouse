package ch.scout.warehouse.server.db.tables;

import ch.scout.warehouse.server.db.DB;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import org.apache.commons.collections4.BidiMap;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.holders.IHolder;
import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldBeanData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;


@ApplicationScoped
public abstract class BaseRepository<E extends BaseEntity, ID> {

  Logger LOG = LoggerFactory.getLogger(BaseRepository.class);

  public Optional<E> findById(ID id) {
    if (id == null) return Optional.empty();
    return Optional.ofNullable(DB.getEntityManager().find(getConnfiguredEntityClass(), id));
  }

  public <F extends AbstractFormData> Optional<E> findById(F formData, BidiMap<Class<? extends IHolder<?>>, String> mapping) {
    String pkName = Arrays.stream(getConnfiguredEntityClass().getFields())
      .filter(column -> column.getAnnotation(Id.class) != null)
      .map(column -> column.getAnnotation(Column.class).name())
      .findFirst().orElseThrow();
    ID pk = (ID) formData.getPropertyByClass((Class<AbstractPropertyData<Object>>) mapping.getKey(pkName)).getValue();
    return findById(pk);
  }


  public E save(E entity) {
    DB.getEntityManager().getTransaction().begin();
    entity = DB.getEntityManager().merge(entity);
    DB.getEntityManager().getTransaction().commit();
    return entity;
  }

  public List<E> saveAll(List<E> entity) {
    DB.getEntityManager().getTransaction().begin();
    entity.forEach(DB.getEntityManager()::merge);
    DB.getEntityManager().getTransaction().commit();
    return entity;
  }

  public void saveAllInOwnTransaction(List<E> entity) {
    entity.forEach(DB.getEntityManager()::merge);
  }


  public E formDataToEntity(AbstractFormData formData, BidiMap<Class<? extends IHolder<?>>, String> mapping) {
    try {
      E entity = findById(formData, mapping).orElse((E) getConnfiguredEntityClass().getDeclaredConstructors()[0].newInstance());
      Arrays.stream(getConnfiguredEntityClass().getFields()).forEach(entityField -> {
        String columName = entityField.getAnnotation(Column.class).name();
        Class<? extends IHolder<?>> formDataField = mapping.getKey(columName);
        if (formDataField != null) {
          try {
            if (AbstractValueFieldData.class.isAssignableFrom(formDataField)) {
              AbstractValueFieldData<Object> value = formData.getFieldByClass((Class<AbstractValueFieldData<Object>>) formDataField);
              if (value.getValue() != null) {
                entityField.set(entity, value.getValue());
              }
            } else if (AbstractPropertyData.class.isAssignableFrom(formDataField)) {
              AbstractPropertyData<Object> value = formData.getPropertyByClass((Class<AbstractPropertyData<Object>>) formDataField);
              if (value.getValue() != null) {
                entityField.set(entity, value.getValue());
              }
            }
          } catch (IllegalAccessException e) {
            LOG.error(e.getMessage(), e);
          }
        }
      });
      return entity;
    } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
      LOG.error(e.getMessage(), e);
    }
    return null;
  }

  public <F extends AbstractFormData> F entityToFormData(E entity, F formData, Map<Class<? extends IHolder<?>>, String> mapping) {
    Arrays.stream(formData.getFields()).forEach(formDataField -> {
      String columnName = mapping.get(formDataField.getClass());
      Arrays.stream(entity.getClass().getFields())
        .filter(entityField -> entityField.getAnnotation(Column.class) != null)
        .filter(entityField -> Objects.equals(entityField.getAnnotation(Column.class).name(), columnName))
        .findFirst().ifPresent(entityField -> {
          try {
            ((AbstractValueFieldData<Object>) (formDataField)).setValue(entityField.get(entity));
          } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
          }
        });
    });

    Arrays.stream(formData.getAllProperties()).forEach(field -> {
      String columnName = mapping.get(field.getClass());
      Arrays.stream(entity.getClass().getFields())
        .filter(field1 -> field1.getAnnotation(Column.class) != null)
        .filter(field1 -> Objects.equals(field1.getAnnotation(Column.class).name(), columnName))
        .findFirst().ifPresent(field1 -> {
          try {
            ((AbstractPropertyData<Object>) (field)).setValue(field1.get(entity));
          } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
          }
        });
    });
    return formData;
  }

  public <T extends AbstractTableFieldBeanData> T entityToTableData(List<E> entities, T tableData, Map<String, String> mapping) {
    for (E entity : entities) {
      AbstractTableRowData row = tableData.addRow();
      Arrays.stream(row.getClass().getFields()).forEach(tableField -> {
        String columnName = mapping.get(tableField.getName());
        Arrays.stream(entity.getClass().getFields())
          .filter(entityField -> entityField.getAnnotation(Column.class) != null)
          .filter(field1 -> Objects.equals(field1.getAnnotation(Column.class).name(), columnName))
          .findFirst().ifPresent(entityField -> {
            try {
              row.getClass()
                .getMethod("set" + tableField.getName().substring(0, 1).toUpperCase() + tableField.getName().substring(1), entityField.getType())
                .invoke(row, entityField.get(entity));
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
              throw new RuntimeException(e);
            }
          });
      });
    }
    return tableData;
  }

  public List<E> tableDataToEntity(AbstractTableFieldBeanData tableData, BidiMap<String, String> mapping) {
    List<E> entities = new ArrayList<>();
    String pkFieldName = mapping.getKey(getPrimaryKeyColumn());
    for (AbstractTableRowData row : tableData.getRows()) {
      E entity;
      try {
        entity = findById((ID)row.getClass().getMethod("get" + pkFieldName.substring(0,1).toUpperCase()+ pkFieldName.substring(1)).invoke(row)).orElse(getConnfiguredEntityClass().getDeclaredConstructor().newInstance());
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        throw new RuntimeException(e);
      }
      Arrays.stream(row.getClass().getFields()).forEach(tableField -> {
        String columnName = mapping.get(tableField.getName());
        Arrays.stream(entity.getClass().getFields())
          .filter(entityField -> entityField.getAnnotation(Column.class) != null)
          .filter(field1 -> Objects.equals(field1.getAnnotation(Column.class).name(), columnName))
          .findFirst().ifPresent(entityField -> {
            try {
              entityField.set(entity,row.getClass().getMethod("get" + tableField.getName().substring(0,1).toUpperCase()+ tableField.getName().substring(1)).invoke(row));
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
              throw new RuntimeException(e);
            }
          });
      });
      entities.add(entity);
    }
    return entities;
  }

  public Optional<String> getSequenceName() {
    return Arrays.stream(getConnfiguredEntityClass().getFields())
      .filter(entityField -> entityField.getAnnotation(SequenceGenerator.class) != null)
      .findFirst()
      .map(column -> column.getAnnotation(SequenceGenerator.class).sequenceName());
  }

  public Long nextVal() {
    return getSequenceName().map(seq -> (long) DB.getEntityManager().createNativeQuery("select nextval('" + seq + "')", Long.class).getSingleResult()).orElseThrow();
  }

  public String getPrimaryKeyColumn() {
    return Arrays.stream(getConnfiguredEntityClass().getFields())
      .filter(entityField -> entityField.getAnnotation(Id.class) != null)
      .findFirst()
      .map(entityField ->
        entityField.getAnnotation(Column.class).name()
      ).orElseThrow();
  }

  public abstract Class<E> getConnfiguredEntityClass();
}
