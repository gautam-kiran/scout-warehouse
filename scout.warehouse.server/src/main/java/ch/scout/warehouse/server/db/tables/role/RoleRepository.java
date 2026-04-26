package ch.scout.warehouse.server.db.tables.role;

import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.tables.BaseRepository;
import ch.scout.warehouse.shared.common.StatusCodeType;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.Optional;

public class RoleRepository extends BaseRepository<Role, Long> {
  JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, DB.getEntityManager());

  @Override
  public Class<Role> getConnfiguredEntityClass() {
    return Role.class;
  }

  public Optional<Role> findByName(String name) {
    QRole role = QRole.role;
    return Optional.ofNullable(queryFactory.select(role)
      .from(role)
      .where(role.name.toLowerCase().eq(name.toLowerCase())
        .and(role.statusUid.eq(StatusCodeType.ActiveCode.ID)))
      .fetchFirst());
  }
}
