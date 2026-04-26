package ch.scout.warehouse.server.db.tables.uctext;

import ch.scout.warehouse.server.db.tables.BaseRepository;

public class UcTextRepository extends BaseRepository<UcText, Long> {
  @Override
  public Class<UcText> getConnfiguredEntityClass() {
    return UcText.class;
  }
}
