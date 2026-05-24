package ch.scout.warehouse.shared.work.order;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

@TunnelToServer
public interface IOrderItemLookupService extends ILookupService<OrderItemKey> {

}
