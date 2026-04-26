package ch.scout.warehouse.server.db.persitance;

import ch.scout.warehouse.server.db.tables.BaseEntity;
import ch.scout.warehouse.server.db.tables.BaseRepository;
import ch.scout.warehouse.shared.db.persistance.ICreateService;
import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import org.apache.commons.collections4.BidiMap;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.IHolder;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;


public interface IEntityCreateService<E extends BaseEntity, F extends AbstractFormData, ID> extends ICreateService<F> {

  @Override
  default F prepareCreate(F formData) {
    if (!ACCESS.check(getConfiguredCreatePermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    formData = prepareCreateImpl(formData, null);
    return formData;
  }

  @Override
  default F create(F formData) {
    if (!ACCESS.check(getConfiguredCreatePermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    E entity = getconfiguredRepository().formDataToEntity(formData, getConfiguredEntityMapping());
    entity = createImpl(formData, entity);
    getconfiguredRepository().entityToFormData(entity, formData, getConfiguredEntityMapping());
    return formData;
  }

  default E createImpl(F formData, E entity) {
    return getconfiguredRepository().save(entity);
  }

  default F prepareCreateImpl(F formData, E entity) {
    return formData;
  }

  AbstractScoutWarehousePermission getConfiguredCreatePermission();

  BaseRepository<E, ID> getconfiguredRepository();

  BidiMap<Class<? extends IHolder<?>>, String> getConfiguredEntityMapping();
}
