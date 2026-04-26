package ch.scout.warehouse.server.db.mig;

import ch.scout.warehouse.server.settings.codeTypes.CodeTypeService;
import ch.scout.warehouse.server.settings.user.AdminUserService;
import org.eclipse.scout.rt.platform.BEANS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseMigration {
  static Logger LOG = LoggerFactory.getLogger(DatabaseMigration.class);

  public static void migrate() {
    LOG.info("Starting DB Migration");

    LOG.info("Creating Admin User");
    BEANS.get(AdminUserService.class).ensuereAdminUser();

    LOG.info("Update Admin Permissions");
    BEANS.get(AdminUserService.class).updateAdminRole();

    LOG.info("Syncronize CodeTypes");
    BEANS.get(CodeTypeService.class).synchronzieCodes();

    LOG.info("Run Migratables");
    BEANS.get(DatabaseMigratableService.class).runMigratables();
  }
}
