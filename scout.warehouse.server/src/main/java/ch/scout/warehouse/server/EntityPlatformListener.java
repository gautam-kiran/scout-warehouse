package ch.scout.warehouse.server;

import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.mig.DatabaseMigration;
import org.eclipse.scout.rt.platform.IPlatform;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.PlatformEvent;

public class EntityPlatformListener implements IPlatformListener {
  @Override
  public void stateChanged(PlatformEvent event) {
    if (event.getState() == IPlatform.State.PlatformStarted) {
      DB.init();
      if( Boolean.parseBoolean(System.getenv("DB_MIGRATION"))){
        DatabaseMigration.migrate();
      }
    }
  }
}
