package ch.scout.warehouse.server.db.tables.orderitem;

import ch.scout.warehouse.server.db.tables.BaseRepository;

public class OrderItemRepository extends BaseRepository<OrderItem, Long> {
  @Override
  public Class<OrderItem> getConnfiguredEntityClass() {
    return OrderItem.class;
  }
}
