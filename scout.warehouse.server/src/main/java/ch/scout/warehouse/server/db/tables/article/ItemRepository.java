package ch.scout.warehouse.server.db.tables.article;

import ch.scout.warehouse.server.db.tables.BaseRepository;

public class ItemRepository extends BaseRepository<Item, Long>{
  @Override
  public Class<Item> getConnfiguredEntityClass() {
    return Item.class;
  }
}
