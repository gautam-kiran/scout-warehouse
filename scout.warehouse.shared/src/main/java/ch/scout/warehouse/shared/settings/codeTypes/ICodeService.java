package ch.scout.warehouse.shared.settings.codeTypes;

import ch.scout.warehouse.shared.db.persistance.ICreateService;
import ch.scout.warehouse.shared.db.persistance.IDeleteService;
import ch.scout.warehouse.shared.db.persistance.IUpdateService;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ICodeService extends IService,
  ICreateService<CodeFormData>,
  IUpdateService<CodeFormData>,
  IDeleteService<Long> {

}
