package ch.scout.warehouse.shared.settings.role;

import ch.scout.warehouse.shared.db.persistance.ICreateService;
import ch.scout.warehouse.shared.db.persistance.IDeleteService;
import ch.scout.warehouse.shared.db.persistance.IUpdateService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IRoleService extends
  ICreateService<RoleFormData>,
  IUpdateService<RoleFormData>,
  IDeleteService<Long> {


    RoleTablePageData getRoleTableData(SearchFilter filter);
}
