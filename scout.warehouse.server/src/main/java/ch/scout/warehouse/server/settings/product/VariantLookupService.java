package ch.scout.warehouse.server.settings.product;

import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.tables.productvariant.QProductVariant;
import ch.scout.warehouse.server.db.tables.uctext.QUcText;
import ch.scout.warehouse.shared.common.LanguageCodeType;
import ch.scout.warehouse.shared.common.ScoutWarehouseLookupRows;
import ch.scout.warehouse.shared.common.StatusCodeType;
import ch.scout.warehouse.shared.settings.product.IVariantLookupService;
import ch.scout.warehouse.shared.settings.product.VariantLookupCall;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.nls.NlsLocale;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

import java.util.List;

public class VariantLookupService extends AbstractLookupService<Long> implements IVariantLookupService {
  JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, DB.getEntityManager());
  QProductVariant variant = new QProductVariant("qv");
  QUcText ucText = new QUcText("uct");

  @Override
  public List<? extends ILookupRow<Long>> getDataByKey(ILookupCall<Long> call) {
    return getData(variant.variantNr.eq(call.getKey()), ((VariantLookupCall) call).getProductNr());

  }
  @Override
  public List<? extends ILookupRow<Long>> getDataByText(ILookupCall<Long> call) {
    return getData(ucText.text.eq(call.getText()), ((VariantLookupCall) call).getProductNr());
  }

  @Override
  public List<? extends ILookupRow<Long>> getDataByAll(ILookupCall<Long> call) {
    return getData(null, ((VariantLookupCall) call).getProductNr());
  }

  @Override
  public List<? extends ILookupRow<Long>> getDataByRec(ILookupCall<Long> call) {
    return null;
  }

  private List<ScoutWarehouseLookupRows> getData(Predicate condition, Long productNr) {
    Long languageId = BEANS.get(LanguageCodeType.class).getCodeByExtKey(NlsLocale.get().getLanguage()).getId();
    return queryFactory.select(Projections.constructor(ScoutWarehouseLookupRows.class,
        variant.variantNr,
        ucText.text
      ))
      .from(variant)
      .join(ucText).on(variant.variantNr.eq(ucText.ucUid).and(ucText.lannguageCode.eq(languageId)))
      .where(variant.statusUid.eq(StatusCodeType.ActiveCode.ID).and(variant.productNr.eq(productNr)).and(condition))
      .fetch();
  }
}
