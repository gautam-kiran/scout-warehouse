package ch.scout.warehouse.server.db.tables.uc;

import ch.scout.warehouse.server.db.tables.BaseRepository;

public class UcRepository extends BaseRepository<Uc, Long> {
  @Override
  public Class<Uc> getConnfiguredEntityClass() {
    return Uc.class;
  }
}
