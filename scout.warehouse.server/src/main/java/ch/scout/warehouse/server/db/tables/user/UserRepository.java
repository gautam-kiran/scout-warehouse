package ch.scout.warehouse.server.db.tables.user;

import ch.scout.warehouse.server.ServerSession;
import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.tables.BaseRepository;
import ch.scout.warehouse.shared.common.StatusCodeType;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.eclipse.scout.rt.shared.user.UserId;

import java.util.Optional;

public class UserRepository extends BaseRepository<User, Long> {
  JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, DB.getEntityManager());

  public Optional<User> findByUsername(String username) {
    ch.scout.warehouse.server.db.tables.user.QUser user = ch.scout.warehouse.server.db.tables.user.QUser.user;
    return Optional.ofNullable(queryFactory.select(user)
      .from(user)
      .where(user.username.toLowerCase().eq(username.toLowerCase())
        .and(user.statusUid.eq(StatusCodeType.ActiveCode.ID)))
      .fetchFirst());
  }

  public User getCurrentUser(){
    return findByUsername(UserId.CURRENT.get()).orElse(null);
  }

  @Override
  public Class<User> getConnfiguredEntityClass() {
    return User.class;
  }
}
