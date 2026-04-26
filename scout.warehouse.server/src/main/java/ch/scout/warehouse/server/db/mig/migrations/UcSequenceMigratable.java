package ch.scout.warehouse.server.db.mig.migrations;
import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.mig.IMigratable;

public class UcSequenceMigratable implements IMigratable
{
  @Override
  public Long getId() {
    return 1L;
  }

  @Override
  public void check() {

  }

  @Override
  public void migrate() {
    DB.getEntityManager().createNativeQuery("CREATE SEQUENCE UC_SEQ START WITH 1000 INCREMENT BY 1").executeUpdate();
  }

  @Override
  public void verify() {

  }
}
