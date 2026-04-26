package ch.scout.warehouse.server.db.persitance;

import ch.scout.warehouse.server.db.tables.BaseEntity;
import ch.scout.warehouse.server.db.tables.BaseRepository;
import org.apache.commons.collections4.BidiMap;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldBeanData;

import java.util.List;

public interface IEntityTableUpdateService<E extends BaseEntity,F extends AbstractFormData, T extends AbstractTableFieldBeanData, ID> {

  default T load(F formData) {
    List<E> entities = loadImpl(formData);
    return getconfiguredRepository().entityToTableData(entities, getConfiguredTable(formData), getConfiguredEntityMapping());
  }

  default F store(F formData) {
    List<E>  entity = getconfiguredRepository().tableDataToEntity(getConfiguredTable(formData), getConfiguredEntityMapping());
    entity = storeImpl(entity, formData);
    getconfiguredRepository().entityToTableData(entity, getConfiguredTable(formData), getConfiguredEntityMapping());
    return formData;
  }

  default List<E> storeImpl(List<E> entities, F formData) {
    return  getconfiguredRepository().saveAll(entities);
  }

  List<E> loadImpl(F formData);

  T getConfiguredTable(F formData);

  BaseRepository<E, ID> getconfiguredRepository();

  BidiMap<String, String> getConfiguredEntityMapping();
}
