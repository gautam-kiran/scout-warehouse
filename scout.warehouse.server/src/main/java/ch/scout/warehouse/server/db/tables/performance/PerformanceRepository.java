package ch.scout.warehouse.server.db.tables.performance;

import ch.scout.warehouse.server.db.tables.BaseRepository;

public class PerformanceRepository extends BaseRepository<Performance, Long> {
  @Override
  public Class<Performance> getConnfiguredEntityClass() {
    return Performance.class;
  }
}
