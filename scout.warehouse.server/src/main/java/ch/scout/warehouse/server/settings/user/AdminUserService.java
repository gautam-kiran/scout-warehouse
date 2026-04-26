package ch.scout.warehouse.server.settings.user;

import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.tables.roelpermisson.QRolePermission;
import ch.scout.warehouse.server.db.tables.roelpermisson.RolePermission;
import ch.scout.warehouse.server.db.tables.roelpermisson.RolePermissionRepository;
import ch.scout.warehouse.server.db.tables.role.Role;
import ch.scout.warehouse.server.db.tables.role.RoleRepository;
import ch.scout.warehouse.server.db.tables.user.User;
import ch.scout.warehouse.server.db.tables.user.UserRepository;
import ch.scout.warehouse.server.db.tables.userrole.UserRole;
import ch.scout.warehouse.server.db.tables.userrole.UserRoleRepository;
import ch.scout.warehouse.server.security.PasswordService;
import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;

import java.util.List;

@ApplicationScoped
public class AdminUserService {
  JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, DB.getEntityManager());


  private User createAdminUser() {
    User user = new User();
    user.setUsername("admin");
    user = BEANS.get(PasswordService.class).hashPassword(user, System.getenv("ADMIN_PASSWORD"));
    return BEANS.get(UserRepository.class).save(user);
  }

  private Role createAdminRole() {
    Role role = new Role();
    role.setName("Admin");
    Role persitedRole = BEANS.get(RoleRepository.class).save(role);
    addAllPermissionsToRole(persitedRole.getRoleNr());
    return persitedRole;
  }

  private void addAllPermissionsToRole(Long roleNr) {
    List<RolePermission> rolePermissions = BEANS.all(AbstractScoutWarehousePermission.class).stream().map(permission -> {
      RolePermission rolePermission = new RolePermission();
      rolePermission.setPermissionClass(permission.getClass().getSimpleName());
      rolePermission.setRoleNr(roleNr);
      return rolePermission;
    }).toList();
    BEANS.get(RolePermissionRepository.class).saveAll(rolePermissions);
  }

  private void addAdminRoleToAdminUser(User user, Role role) {
    UserRole userRole = new UserRole();
    userRole.setRoleNr(role.getRoleNr());
    userRole.setUserNr(user.getUserNr());
    BEANS.get(UserRoleRepository.class).save(userRole);
  }


  public void ensuereAdminUser() {
    if (BEANS.get(UserRepository.class).findByUsername("admin").isEmpty()) {
      addAdminRoleToAdminUser(createAdminUser(), createAdminRole());
    }
  }

  public void updateAdminRole() {
    Role role = BEANS.get(RoleRepository.class).findByName("Admin").orElseThrow();
    QRolePermission rolePermission = new QRolePermission("rp");
    DB.getEntityManager().getTransaction().begin();
    queryFactory.delete(rolePermission)
      .where(rolePermission.roleNr.eq(role.getRoleNr()))
      .execute();
    DB.getEntityManager().getTransaction().commit();
    DB.getEntityManager().clear();
    addAllPermissionsToRole(role.getRoleNr());
  }
}
