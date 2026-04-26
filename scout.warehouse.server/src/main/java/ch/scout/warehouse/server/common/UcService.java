package ch.scout.warehouse.server.common;

import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.tables.uc.QUc;
import ch.scout.warehouse.server.db.tables.uctext.QUcText;
import ch.scout.warehouse.shared.common.LanguageCodeType;
import ch.scout.warehouse.shared.common.codetype.IUcService;
import ch.scout.warehouse.shared.common.codetype.ScoutWarehouseCodeRows;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.eclipse.scout.rt.shared.services.common.code.ICodeRow;

import java.util.List;


public class UcService implements IUcService {
  JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, DB.getEntityManager());

  @Override
  public List<? extends ICodeRow<Long>> loadCodes(Long id) {
    QUc uc = new QUc("uc");
    QUcText ucText = new QUcText("ucT");

    return queryFactory.select(Projections.constructor(ScoutWarehouseCodeRows.class,
        uc.ucUid,
        ucText.text,
        uc.parentKey,
        uc.statusUid,
        uc.extKey,
        uc.value,
        uc.isBuiltIn
      ))
      .from(uc)
      .leftJoin(ucText).on(ucText.ucUid.eq(uc.ucUid).and(ucText.lannguageCode.eq(LanguageCodeType.GermandCode.ID)))
      .where(uc.codeType.eq(id)).fetch();

  }
}
