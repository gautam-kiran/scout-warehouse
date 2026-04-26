package ch.scout.warehouse.server.settings.role;

import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.persitance.IEntityCreateService;
import ch.scout.warehouse.server.db.persitance.IEntityDeleteService;
import ch.scout.warehouse.server.db.persitance.IEntityUpdateService;
import ch.scout.warehouse.server.db.tables.BaseRepository;
import ch.scout.warehouse.server.db.tables.roelpermisson.QRolePermission;
import ch.scout.warehouse.server.db.tables.roelpermisson.RolePermission;
import ch.scout.warehouse.server.db.tables.roelpermisson.RolePermissionRepository;
import ch.scout.warehouse.server.db.tables.role.QRole;
import ch.scout.warehouse.server.db.tables.role.Role;
import ch.scout.warehouse.server.db.tables.role.RoleRepository;
import ch.scout.warehouse.shared.common.StatusCodeType;
import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import ch.scout.warehouse.shared.settings.role.*;
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


public class RoleService implements IRoleService,
  IEntityCreateService<Role, RoleFormData, Long>,
  IEntityUpdateService<Role, RoleFormData, Long>,
  IEntityDeleteService<Role, Long> {

  JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, DB.getEntityManager());
  QRolePermission rolePermission = QRolePermission.rolePermission;

  @Override
  public Role createImpl(RoleFormData formData, Role entity) {
    Role role = IEntityCreateService.super.createImpl(formData, entity);
    saveRolePermission(formData, role.getRoleNr());
    return role;
  }

  @Override
  public Role loadImpl(RoleFormData formData) {
    Role role = IEntityUpdateService.super.loadImpl(formData);
    Set<String> permission = queryFactory
      .selectFrom(rolePermission)
      .where(rolePermission.roleNr.eq(formData.getRoleNr()))
      .fetch().stream()
      .map(RolePermission::getPermissionClass)
      .collect(Collectors.toSet());
    formData.getPermissionBox().setValue(permission);
    return role;
  }

  @Override
  public Role storeImpl(Role entity, RoleFormData formData) {
    Role role = IEntityUpdateService.super.storeImpl(entity, formData);

    DB.getEntityManager().getTransaction().begin();
    queryFactory.delete(rolePermission)
      .where(rolePermission.roleNr.eq(formData.getRoleNr()))
      .execute();
    DB.getEntityManager().getTransaction().commit();
    DB.getEntityManager().clear();

    saveRolePermission(formData, formData.getRoleNr());
    return role;
  }

  private void saveRolePermission(RoleFormData formData, Long roleNr) {
    List<RolePermission> rolePermissions = formData.getPermissionBox().getValue().stream()
      .map(permisson -> {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleNr(roleNr);
        rolePermission.setPermissionClass(permisson);
        return rolePermission;
      }).toList();
    BEANS.get(RolePermissionRepository.class).saveAll(rolePermissions);
  }

  @Override
  public RoleTablePageData getRoleTableData(SearchFilter filter) {
    RoleTablePageData pageData = new RoleTablePageData();

    QRole role = new QRole("role");
    List<RoleTablePageData.RoleTableRowData> rowData = queryFactory.select(Projections.fields(
        RoleTablePageData.RoleTableRowData.class,
        role.roleNr.as("m_" + RoleTablePageData.RoleTableRowData.roleNr),
        role.name.as("m_" + RoleTablePageData.RoleTableRowData.name)
      ))
      .from(role)
      .where(role.statusUid.eq(StatusCodeType.ActiveCode.ID))
      .fetch();
    pageData.setRows(rowData.toArray(new RoleTablePageData.RoleTableRowData[rowData.size()]));
    return pageData;
  }

  @Override
  public BidiMap<Class<? extends IHolder<?>>, String> getConfiguredEntityMapping() {
    return new DualHashBidiMap<>(Map.of(
      RoleFormData.RoleNrProperty.class, Role.NativeNames.ROLE_NR,
      RoleFormData.Name.class, Role.NativeNames.NAME
    ));
  }

  @Override
  public BaseRepository<Role, Long> getconfiguredRepository() {
    return BEANS.get(RoleRepository.class);
  }

  @Override
  public AbstractScoutWarehousePermission getConfiguredUpdatePermission() {
    return new UpdateRolePermission();
  }

  @Override
  public AbstractScoutWarehousePermission getConfiguredCreatePermission() {
    return new CreateRolePermission();
  }

  @Override
  public AbstractScoutWarehousePermission getConfiguredReadPermission() {
    return new ReadRolePermission();
  }
}
