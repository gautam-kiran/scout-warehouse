package ch.scout.warehouse.server.db.tables.order;

import ch.scout.warehouse.server.db.tables.BaseRepository;

public class OrderRepository extends BaseRepository<Order, Long> {
  @Override
  public Class<Order> getConnfiguredEntityClass() {
    return Order.class;
  }
}
