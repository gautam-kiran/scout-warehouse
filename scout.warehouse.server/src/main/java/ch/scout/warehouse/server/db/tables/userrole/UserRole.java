package ch.scout.warehouse.server.db.tables.userrole;

import ch.scout.warehouse.server.db.tables.BaseEntity;
import ch.scout.warehouse.server.db.tables.IBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = UserRole.NativeNames.PERSON_ROLE)
@IdClass(UserRoleKey.class)
public class UserRole implements IBaseEntity {

  @Id
  @Column(name = NativeNames.USER_NR, insertable = false, updatable = false)
  public Long userNr;

  @Id
  @Column(name = NativeNames.ROLE_NR, insertable = false, updatable = false)
  public Long roleNr;

  public static class NativeNames {
    public static final String PERSON_ROLE = "PERSON_ROLE";
    public static final String ROLE_NR = "ROLE_NR";
    public static final String USER_NR = "USER_NR";
  }
}
