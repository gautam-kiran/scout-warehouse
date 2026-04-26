package ch.scout.warehouse.server.db.tables.migration;

import ch.scout.warehouse.server.db.tables.BaseRepository;

public class DBMigrationRepository extends BaseRepository<DBMigration, Long> {
  @Override
  public Class<DBMigration> getConnfiguredEntityClass() {
    return DBMigration.class;
  }
}
