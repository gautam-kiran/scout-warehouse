package ch.scout.warehouse.server.settings.user;

import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.persitance.IEntityCreateService;
import ch.scout.warehouse.server.db.persitance.IEntityDeleteService;
import ch.scout.warehouse.server.db.persitance.IEntityUpdateService;
import ch.scout.warehouse.server.db.tables.BaseRepository;
import ch.scout.warehouse.server.db.tables.user.QUser;
import ch.scout.warehouse.server.db.tables.user.User;
import ch.scout.warehouse.server.db.tables.user.UserRepository;
import ch.scout.warehouse.server.db.tables.userrole.QUserRole;
import ch.scout.warehouse.server.db.tables.userrole.UserRole;
import ch.scout.warehouse.server.db.tables.userrole.UserRoleRepository;
import ch.scout.warehouse.server.security.PasswordService;
import ch.scout.warehouse.shared.common.StatusCodeType;
import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import ch.scout.warehouse.shared.settings.user.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.IHolder;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UserService implements IUserService,
  IEntityCreateService<User, UserFormData, Long>,
  IEntityUpdateService<User, UserFormData, Long>,
  IEntityDeleteService<User, Long> {

  JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, DB.getEntityManager());
  QUserRole userRole = new QUserRole("userRole");
  QUser user = new QUser("user");

  @Override
  public User createImpl(UserFormData formData, User entity) {
    entity = IEntityCreateService.super.createImpl(formData, entity);
    saveRolePermission(formData, entity.getUserNr());
    entity = BEANS.get(PasswordService.class).hashPassword(entity, formData.getPassword().getValue());
    return getconfiguredRepository().save(entity);
  }

  @Override
  public User loadImpl(UserFormData formData) {
    User entity = IEntityUpdateService.super.loadImpl(formData);
    Set<Long> roles = queryFactory
      .selectFrom(userRole)
      .where(userRole.userNr.eq(formData.getUserNr()))
      .fetch().stream()
      .map(UserRole::getRoleNr)
      .collect(Collectors.toSet());
    formData.getRolesBox().setValue(roles);
    return entity;
  }

  @Override
  public User storeImpl(User entity, UserFormData formData) {
    entity = IEntityUpdateService.super.storeImpl(entity, formData);

    DB.getEntityManager().getTransaction().begin();
    queryFactory.delete(userRole)
      .where(userRole.userNr.eq(formData.getUserNr()))
      .execute();
    DB.getEntityManager().getTransaction().commit();
    DB.getEntityManager().clear();

    saveRolePermission(formData, entity.getUserNr());
    return entity;
  }

  private void saveRolePermission(UserFormData formData, Long userNr) {
    List<UserRole> userRoles = formData.getRolesBox().getValue().stream()
      .map(roleNr -> {
        UserRole userRole = new UserRole();
        userRole.setUserNr(userNr);
        userRole.setRoleNr(roleNr);
        return userRole;
      }).toList();
    BEANS.get(UserRoleRepository.class).saveAll(userRoles);
  }

  @Override
  public UserTablePageData getUserTableData(SearchFilter filter) {
    UserTablePageData pageData = new UserTablePageData();

    List<UserTablePageData.UserTableRowData> rowData = queryFactory.select(Projections.fields(
        UserTablePageData.UserTableRowData.class,
        user.userNr.as("m_" + UserTablePageData.UserTableRowData.userNr),
        user.username.as("m_" + UserTablePageData.UserTableRowData.userName)
      ))
      .from(user)
      .where(user.statusUid.eq(StatusCodeType.ActiveCode.ID))
      .fetch();
    pageData.setRows(rowData.toArray(new UserTablePageData.UserTableRowData[rowData.size()]));
    return pageData;
  }

  @Override
  public AbstractScoutWarehousePermission getConfiguredCreatePermission() {
    return new CreateUserPermission();
  }

  @Override
  public AbstractScoutWarehousePermission getConfiguredUpdatePermission() {
    return new UpdateUserPermission();
  }

  @Override
  public AbstractScoutWarehousePermission getConfiguredReadPermission() {
    return new ReadUserPermission();
  }

  @Override
  public BaseRepository<User, Long> getconfiguredRepository() {
    return BEANS.get(UserRepository.class);
  }

  @Override
  public BidiMap<Class<? extends IHolder<?>>, String> getConfiguredEntityMapping() {
    return new DualHashBidiMap<>(Map.of(
      UserFormData.UserNrProperty.class, User.NativeNames.USER_NR,
      UserFormData.UserName.class, User.NativeNames.USERNAME
    ));
  }

  public Long getUserNrByUsername(String userName) {
    return queryFactory
      .select(user.userNr)
      .from(user)
      .where(user.username.equalsIgnoreCase(userName))
      .fetchOne();
  }
}
