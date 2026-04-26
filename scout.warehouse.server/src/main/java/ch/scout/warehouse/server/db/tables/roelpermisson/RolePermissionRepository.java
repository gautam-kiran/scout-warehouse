package ch.scout.warehouse.server.db.tables.roelpermisson;

import ch.scout.warehouse.server.db.tables.BaseRepository;

public class RolePermissionRepository extends BaseRepository<RolePermission, RolePermissionKey> {
  @Override
  public Class<RolePermission> getConnfiguredEntityClass() {
    return RolePermission.class;
  }
}
