package ch.scout.warehouse.server.db.tables.product;

import ch.scout.warehouse.server.db.tables.BaseRepository;

public class ProductRepository extends BaseRepository<Product, Long> {
  @Override
  public Class<Product> getConnfiguredEntityClass() {
    return Product.class;
  }
}
