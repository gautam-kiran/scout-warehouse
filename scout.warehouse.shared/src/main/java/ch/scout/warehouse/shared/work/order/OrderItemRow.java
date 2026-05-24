package ch.scout.warehouse.shared.work.order;

import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

public class OrderItemRow extends LookupRow<OrderItemKey> {
  public OrderItemRow(Long itemNr, Long productNr, Long orderItemType, String text) {
    super(new OrderItemKey(itemNr,productNr,orderItemType),text);
  }

  public OrderItemRow(OrderItemKey key, String text) {
    super(key, text);
  }

  public OrderItemRow(Object[] cells, Class<?> keyClass) {
    super(cells, keyClass);
  }

  public OrderItemRow(Object[] cells, int maxColumnIndex, Class<?> keyClass) {
    super(cells, maxColumnIndex, keyClass);
  }

}
