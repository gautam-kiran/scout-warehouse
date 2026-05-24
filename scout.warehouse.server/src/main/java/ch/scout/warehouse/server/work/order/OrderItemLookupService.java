package ch.scout.warehouse.server.work.order;

import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.tables.article.QItem;
import ch.scout.warehouse.server.db.tables.product.QProduct;
import ch.scout.warehouse.server.db.tables.productvariant.QProductVariant;
import ch.scout.warehouse.server.db.tables.uctext.QUcText;
import ch.scout.warehouse.shared.common.LanguageCodeType;
import ch.scout.warehouse.shared.common.StatusCodeType;
import ch.scout.warehouse.shared.settings.product.ProductTypeCodeType;
import ch.scout.warehouse.shared.work.order.IOrderItemLookupService;
import ch.scout.warehouse.shared.work.order.OrderItemKey;
import ch.scout.warehouse.shared.work.order.OrderItemRow;
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

public class OrderItemLookupService extends AbstractLookupService<OrderItemKey> implements IOrderItemLookupService {
  JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, DB.getEntityManager());
  QProduct product = new QProduct("product");
  QProductVariant variant = new QProductVariant("qv");
  QItem item = new QItem("item");
  QUcText ucText = new QUcText("uct");

  @Override
  public List<? extends ILookupRow<OrderItemKey>> getDataByKey(ILookupCall<OrderItemKey> call) {
    return call.getKey().getOrderItemType() == ProductTypeCodeType.SpecificCode.ID ? getData(item.itemNr.eq(call.getKey().getItemNr())) : getData(product.productNr.eq(call.getKey().getProductNr()));
  }

  @Override
  public List<? extends ILookupRow<OrderItemKey>> getDataByText(ILookupCall<OrderItemKey> call) {
    String text = call.getText().replace(call.getWildcard(), "%").toLowerCase();
    return getData(item.description.toLowerCase().like(text).or(product.name.toLowerCase().like(text)));
  }

  @Override
  public List<? extends ILookupRow<OrderItemKey>> getDataByAll(ILookupCall<OrderItemKey> call) {
    return getData(null);
  }

  @Override
  public List<? extends ILookupRow<OrderItemKey>> getDataByRec(ILookupCall<OrderItemKey> call) {
    return List.of();
  }

  private List<OrderItemRow> getData(Predicate condition) {
    Long languageId = BEANS.get(LanguageCodeType.class).getCodeByExtKey(NlsLocale.get().getLanguage()).getId();
    return queryFactory.selectDistinct(Projections.constructor(OrderItemRow.class,
        product.productType.when(ProductTypeCodeType.SpecificCode.ID)
          .then(item.itemNr)
          .otherwise(0L),
        product.productNr,
        product.productType,
        item.variantNr,
        item.itemNo,
        item.description.coalesce(product.name).append(" (").append(ucText.text).append(")")
      ))
      .from(item)
      .leftJoin(product).on(item.productNr.eq(item.productNr))
      .leftJoin(variant).on(item.variantNr.eq(variant.variantNr).and(variant.statusUid.eq(StatusCodeType.ActiveCode.ID)))
      .leftJoin(ucText).on(variant.variantNr.eq(ucText.ucUid).and(ucText.lannguageCode.eq(languageId)))
      .where(
        item.statusUid.eq(StatusCodeType.ActiveCode.ID)
          .and(product.statusUid.eq(StatusCodeType.ActiveCode.ID))
          .and(variant.statusUid.eq(StatusCodeType.ActiveCode.ID))
          .and(condition)
      )
      .fetch();
  }
}
