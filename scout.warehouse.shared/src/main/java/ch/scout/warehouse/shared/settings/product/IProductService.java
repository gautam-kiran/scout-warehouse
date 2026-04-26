package ch.scout.warehouse.shared.settings.product;

import ch.scout.warehouse.shared.db.persistance.ICreateService;
import ch.scout.warehouse.shared.db.persistance.IDeleteService;
import ch.scout.warehouse.shared.db.persistance.IUpdateService;
import ch.scout.warehouse.shared.settings.role.RoleFormData;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IProductService extends ICreateService<ProductFormData>,
  IUpdateService<ProductFormData>,
  IDeleteService<Long> {
    ProductTablePageData getProductTableData(SearchFilter filter);
}
