package ch.scout.warehouse.shared.work.order;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

public class OrderItemLookupCall extends LookupCall<OrderItemKey> {
    private static final long serialVersionUID = 1L;

    @Override
    protected Class<? extends ILookupService<OrderItemKey>> getConfiguredService() {
        return IOrderItemLookupService.class;
    }
}
