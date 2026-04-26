package ch.scout.warehouse.server.db.persitance;

import ch.scout.warehouse.server.db.tables.BaseEntity;
import ch.scout.warehouse.server.db.tables.BaseRepository;
import org.apache.commons.collections4.BidiMap;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldBeanData;

import java.util.List;

public interface IEntityTableCreateService <E extends BaseEntity,F extends AbstractFormData, T extends AbstractTableFieldBeanData, ID> {

  default T preapareCreate(F formData) {
    List<E> entities = prepareCreateImpl(formData);
    return getconfiguredRepository().entityToTableData(entities, getConfiguredTable(formData), getConfiguredEntityMapping());
  }

  default F create(F formData) {
    List<E>  entity = getconfiguredRepository().tableDataToEntity(getConfiguredTable(formData), getConfiguredEntityMapping());
    entity = createImpl(entity, formData);
    getconfiguredRepository().entityToTableData(entity, getConfiguredTable(formData), getConfiguredEntityMapping());
    return formData;
  }

  default List<E> createImpl(List<E> entities, F formData) {
    return  getconfiguredRepository().saveAll(entities);
  }

  default List<E> prepareCreateImpl(F formData) {
    return List.of();
  }

  T getConfiguredTable(F formData);

  BaseRepository<E, ID> getconfiguredRepository();

  BidiMap<String, String> getConfiguredEntityMapping();
}
