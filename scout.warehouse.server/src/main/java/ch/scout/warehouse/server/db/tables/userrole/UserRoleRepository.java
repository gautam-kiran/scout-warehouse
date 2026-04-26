package ch.scout.warehouse.server.db.tables.userrole;

import ch.scout.warehouse.server.db.tables.BaseRepository;

public class UserRoleRepository extends BaseRepository<UserRole, Long> {
  @Override
  public Class<UserRole> getConnfiguredEntityClass() {
    return UserRole.class;
  }
}
