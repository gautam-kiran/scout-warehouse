package ch.scout.warehouse.server.db.tables.productvariant;

import ch.scout.warehouse.server.db.tables.BaseRepository;

public class ProductVariantRepository extends BaseRepository<ProductVariant,Long> {
  @Override
  public Class<ProductVariant> getConnfiguredEntityClass() {
    return ProductVariant.class;
  }
}
