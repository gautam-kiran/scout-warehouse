package ch.scout.warehouse.shared.work.order;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

import java.util.Map;

public class OrderItemLookupCall extends LookupCall<OrderItemKey> {
  private static final long serialVersionUID = 1L;
  Map<Long, Long> orderItems;

  @Override
  protected Class<? extends ILookupService<OrderItemKey>> getConfiguredService() {
    return IOrderItemLookupService.class;
  }

  public Map<Long, Long> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(Map<Long, Long> orderItems) {
    this.orderItems = orderItems;
  }
}
