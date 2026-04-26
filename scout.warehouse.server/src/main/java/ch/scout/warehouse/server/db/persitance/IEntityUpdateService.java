package ch.scout.warehouse.server.db.persitance;

import ch.scout.warehouse.server.db.tables.BaseEntity;
import ch.scout.warehouse.server.db.tables.BaseRepository;
import ch.scout.warehouse.shared.db.persistance.IUpdateService;
import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import org.apache.commons.collections4.BidiMap;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.IHolder;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;

public interface IEntityUpdateService<E extends BaseEntity, F extends AbstractFormData, ID> extends IUpdateService<F> {
  @Override
  default F load(F formData) {
    if (!ACCESS.check(getConfiguredReadPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    E entity = loadImpl(formData);
    return getconfiguredRepository().entityToFormData(entity, formData, getConfiguredEntityMapping());
  }

  @Override
  default F store(F formData) {
    if (!ACCESS.check(getConfiguredUpdatePermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    E entity = getconfiguredRepository().formDataToEntity(formData, getConfiguredEntityMapping());
    entity = storeImpl(entity, formData);
    formData = getconfiguredRepository().entityToFormData(entity, formData, getConfiguredEntityMapping());
    return formData;
  }

  default E storeImpl(E entity, F formData) {
    return getconfiguredRepository().save(entity);
  }

  default E loadImpl(F formData) {
    return getconfiguredRepository().findById(formData,getConfiguredEntityMapping()).orElseThrow();
  }


  AbstractScoutWarehousePermission getConfiguredUpdatePermission();

  AbstractScoutWarehousePermission getConfiguredReadPermission();

  BaseRepository<E, ID> getconfiguredRepository();

  BidiMap<Class<? extends IHolder<?>>, String> getConfiguredEntityMapping();
}
