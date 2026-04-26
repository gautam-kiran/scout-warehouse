package ch.scout.warehouse.server.db.tables.role;

import ch.scout.warehouse.server.db.tables.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = Role.NativeNames.ROLE)
public class Role extends BaseEntity {

  @Id
  @Column(name = NativeNames.ROLE_NR, insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  public Long roleNr;

  @Column(name = NativeNames.NAME)
  public String name;

  public static class NativeNames {
    public static final String ROLE = "ROLE";
    public static final String ROLE_NR = "ROLE_NR";
    public static final String NAME = "NAME";
  }

}
