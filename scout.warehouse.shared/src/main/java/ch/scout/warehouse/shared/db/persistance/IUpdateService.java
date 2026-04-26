package ch.scout.warehouse.shared.db.persistance;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;

public interface IUpdateService <F extends AbstractFormData> extends IService {

  F load(F formData);

  F store(F formData);
}
