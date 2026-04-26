package ch.scout.warehouse.server.db.mig;

import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.tables.migration.DBMigration;
import ch.scout.warehouse.server.db.tables.migration.DBMigrationRepository;
import ch.scout.warehouse.server.db.tables.migration.QDBMigration;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DatabaseMigratableService {
  JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, DB.getEntityManager());

  public void runMigratables() {
    QDBMigration dbMigration = QDBMigration.dBMigration;
    List<Long> allreadyMigratedMigratbles = queryFactory
      .select(dbMigration.DBMigrationNr)
      .from(dbMigration)
      .fetch();

    List<IMigratable> toMigrate = BEANS.all(IMigratable.class).stream()
      .filter(iMigratable -> !allreadyMigratedMigratbles.contains(iMigratable.getId()))
      .toList();

    List<DBMigration> migrations = new ArrayList<>();
    for (IMigratable migratable : toMigrate) {
      DB.getEntityManager().getTransaction().begin();
      try {
        migratable.check();
        migratable.migrate();
        migratable.verify();
      } catch (Exception e) {
        DB.getEntityManager().getTransaction().rollback();
        continue;
      }
      DB.getEntityManager().getTransaction().commit();
      DBMigration migration = new DBMigration();
      migration.setDBMigrationNr(migratable.getId());
      migrations.add(migration);
    }
    BEANS.get(DBMigrationRepository.class).saveAll(migrations);
  }
}
