package ch.scout.warehouse.shared.db.persistance;

import ch.scout.warehouse.shared.settings.role.RoleFormData;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;

public interface ICreateService <F extends AbstractFormData> extends IService {

  F prepareCreate(F formData);
  F create(F formData);
}
