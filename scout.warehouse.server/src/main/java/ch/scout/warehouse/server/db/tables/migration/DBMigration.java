package ch.scout.warehouse.server.db.tables.migration;

import ch.scout.warehouse.server.db.tables.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.scout.rt.platform.ApplicationScoped;

@Setter
@Getter
@ApplicationScoped
@Table(name = DBMigration.NativeNames.DB_MIGRATIONS)
@Entity
public class DBMigration extends BaseEntity{

   @Id
   @Column(name = NativeNames.DB_MIGRATION_NR)
   public Long DBMigrationNr;

  public static class NativeNames {
    public static final String DB_MIGRATIONS = "DB_MIGRATIONS";
    public static final String DB_MIGRATION_NR = "DB_MIGRATION_NR";
  }
}
