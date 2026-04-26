package ch.scout.warehouse.server.db.tables.roelpermisson;


import java.io.Serializable;

public class RolePermissionKey implements Serializable {
  Long roleNr;
  String permissionClass;

  public RolePermissionKey(Long roleNr, String permissionClass) {
    this.roleNr = roleNr;
    this.permissionClass = permissionClass;
  }

  public RolePermissionKey() {
  }

}
