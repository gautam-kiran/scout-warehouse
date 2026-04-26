package ch.scout.warehouse.server.security;

import ch.scout.warehouse.server.ServerSession;
import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.tables.roelpermisson.QRolePermission;
import ch.scout.warehouse.server.db.tables.roelpermisson.RolePermission;
import ch.scout.warehouse.server.db.tables.user.QUser;
import ch.scout.warehouse.server.db.tables.userrole.QUserRole;
import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import ch.scout.warehouse.shared.security.AccessControlService;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.security.DefaultPermissionCollection;
import org.eclipse.scout.rt.security.IPermissionCollection;
import org.eclipse.scout.rt.security.PermissionLevel;
import org.eclipse.scout.rt.server.session.ServerSessionCache;
import org.eclipse.scout.rt.shared.security.RemoteServiceAccessPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author Kiran
 */
@Replace
public class ServerAccessControlService extends AccessControlService {
  JPAQueryFactory queryFactory;
  Logger LOG = LoggerFactory.getLogger(ServerAccessControlService.class);

  @Override
  protected IPermissionCollection execLoadPermissions(String userId) {
    IPermissionCollection permissions = BEANS.get(DefaultPermissionCollection.class);
    permissions.add(new RemoteServiceAccessPermission("*.shared.*", "*"), PermissionLevel.ALL);
    queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, DB.getEntityManager());

    QRolePermission rolePermission = QRolePermission.rolePermission;
    QUserRole userRole = QUserRole.userRole;
    QUser user = QUser.user;
    List<String> permissionClasses =
      queryFactory.select(rolePermission)
        .from(rolePermission)
        .leftJoin(userRole).on(rolePermission.roleNr.eq(userRole.roleNr))
        .leftJoin(user).on(user.userNr.eq(userRole.userNr))
        .where(user.username.toLowerCase().like(userId.toLowerCase()))
        .fetch().stream().map(RolePermission::getPermissionClass)
        .toList();

    BEANS.all(AbstractScoutWarehousePermission.class).stream()
      .filter(permissionClass -> permissionClasses.contains(permissionClass.getClass().getSimpleName()))
      .forEach(permission -> {
        try {
          permissions.add(permission.getClass().getDeclaredConstructor().newInstance(), PermissionLevel.ALL);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
          LOG.error("Failed to instantiate permission class", e);
        }
      });
    permissions.setReadOnly();
    return permissions;
  }
}
