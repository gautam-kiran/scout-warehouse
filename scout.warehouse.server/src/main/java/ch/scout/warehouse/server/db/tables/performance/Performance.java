package ch.scout.warehouse.server.db.tables.performance;

import ch.scout.warehouse.server.db.tables.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = Performance.NativeNames.PERFORMANCE)
public class Performance extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = NativeNames.PERFORMANCE_NR)
  public Long performanceNr;

  @Column(name = NativeNames.NAME)
  public String name;

  @Column(name = NativeNames.YEAR)
  public Long year;

  public static class NativeNames {
    public static final String PERFORMANCE = "PERFORMANCE";
    public static final String PERFORMANCE_NR = "PERFORMANCE_NR";
    public static final String NAME = "NAME";
    public static final String YEAR = "YEAR";
  }
}
