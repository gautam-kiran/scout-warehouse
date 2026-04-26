package ch.scout.warehouse.server.db;

import ch.scout.warehouse.shared.db.IDatabaseService;

public class DatabaseService implements IDatabaseService {
  @Override
  public String getDatabaseName() {
    return System.getenv("DB_URL");
  }
}
