package ch.scout.warehouse.shared.settings.user;

import ch.scout.warehouse.shared.db.persistance.ICreateService;
import ch.scout.warehouse.shared.db.persistance.IDeleteService;
import ch.scout.warehouse.shared.db.persistance.IUpdateService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IUserService extends ICreateService<UserFormData>, IUpdateService<UserFormData>, IDeleteService<Long> {

    UserTablePageData getUserTableData(SearchFilter filter);
}
