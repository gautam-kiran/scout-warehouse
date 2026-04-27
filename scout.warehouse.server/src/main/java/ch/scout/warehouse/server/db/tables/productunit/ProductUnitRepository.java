package ch.scout.warehouse.server.db.tables.productunit;

import ch.scout.warehouse.server.db.tables.BaseRepository;
import ch.scout.warehouse.shared.settings.product.ProductFormData;

public class ProductUnitRepository extends BaseRepository<ProductUnit, Long> {
  @Override
  public Class<ProductUnit> getConnfiguredEntityClass() {
    return ProductUnit.class;
  }
}
