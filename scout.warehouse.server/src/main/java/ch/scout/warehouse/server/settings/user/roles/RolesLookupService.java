package ch.scout.warehouse.server.settings.user.roles;

import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.tables.role.QRole;
import ch.scout.warehouse.shared.common.StatusCodeType;
import ch.scout.warehouse.shared.settings.user.roles.IRolesLookupService;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

import java.util.List;

public class RolesLookupService extends AbstractLookupService<Long> implements IRolesLookupService {

  QRole role = new QRole("role");

  @Override
  public List<? extends ILookupRow<Long>> getDataByKey(ILookupCall<Long> call) {
    return executeQuery(role.roleNr.eq(call.getKey()));
  }

  @Override
  public List<? extends ILookupRow<Long>> getDataByText(ILookupCall<Long> call) {
    return executeQuery(role.name.like(call.getText().replace(call.getWildcard(), "")));
  }

  @Override
  public List<? extends ILookupRow<Long>> getDataByAll(ILookupCall<Long> call) {
    return executeQuery();
  }

  @Override
  public List<? extends ILookupRow<Long>> getDataByRec(ILookupCall<Long> call) {
    return null;
  }

  private List<? extends ILookupRow<Long>> executeQuery(Predicate... whereClauses) {
    JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, DB.getEntityManager());
    return queryFactory.select(role)
      .from(role)
      .where(role.statusUid.eq(StatusCodeType.ActiveCode.ID))
      .where(whereClauses)
      .fetch().stream()
      .map(role1 -> new LookupRow<>(role1.getRoleNr(), role1.getName()))
      .toList();
  }
}
