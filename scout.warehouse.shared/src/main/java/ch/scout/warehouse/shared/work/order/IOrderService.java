package ch.scout.warehouse.shared.work.order;

import ch.scout.warehouse.shared.db.persistance.ICreateService;
import ch.scout.warehouse.shared.db.persistance.IDeleteService;
import ch.scout.warehouse.shared.db.persistance.IUpdateService;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IOrderService extends ICreateService<OrderFormData>, IUpdateService<OrderFormData>,IDeleteService<Long> {
    OrderTablePageData getOrderTableData(SearchFilter filter);
}
