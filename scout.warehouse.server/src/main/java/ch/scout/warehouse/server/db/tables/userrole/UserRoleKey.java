package ch.scout.warehouse.server.db.tables.userrole;

import java.io.Serializable;

public class UserRoleKey implements Serializable {

  Long userNr;
  Long roleNr;
  public UserRoleKey(Long userNr, Long roleNr) {
    this.userNr = userNr;
    this.roleNr = roleNr;
  }

  UserRoleKey(){}

}
