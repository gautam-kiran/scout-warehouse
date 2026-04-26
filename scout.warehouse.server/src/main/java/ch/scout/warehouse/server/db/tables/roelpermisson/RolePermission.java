package ch.scout.warehouse.server.db.tables.roelpermisson;

import ch.scout.warehouse.server.db.tables.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = RolePermission.NativeNames.ROLE_PERMISSION)
@IdClass(RolePermissionKey.class)
public class RolePermission extends BaseEntity {

  @Id
  @Column(name = NativeNames.ROLE_NR)
  public Long roleNr;

  @Id
  @Column(name = NativeNames.PERMISSION_CLASS)
  public String permissionClass;

  public static class NativeNames {
    public static final String ROLE_PERMISSION = "ROLE_PERMISSION";
    public static final String PERMISSION_CLASS = "PERMISSION_CLASS";
    public static final String ROLE_NR = "ROLE_NR";
  }
}
