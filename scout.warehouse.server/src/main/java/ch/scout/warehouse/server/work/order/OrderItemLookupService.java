package ch.scout.warehouse.server.work.order;

import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.tables.article.QItem;
import ch.scout.warehouse.server.db.tables.product.QProduct;
import ch.scout.warehouse.server.db.tables.productunit.QProductUnit;
import ch.scout.warehouse.server.db.tables.productvariant.QProductVariant;
import ch.scout.warehouse.server.db.tables.uctext.QUcText;
import ch.scout.warehouse.shared.common.LanguageCodeType;
import ch.scout.warehouse.shared.common.StatusCodeType;
import ch.scout.warehouse.shared.settings.product.ProductTypeCodeType;
import ch.scout.warehouse.shared.work.order.IOrderItemLookupService;
import ch.scout.warehouse.shared.work.order.OrderItemKey;
import ch.scout.warehouse.shared.work.order.OrderItemLookupCall;
import ch.scout.warehouse.shared.work.order.OrderItemRow;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
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
  QUcText variantText = new QUcText("variantText");
  QProductUnit productUnit = new QProductUnit("productUnit");
  QUcText productUnitText = new QUcText("productUnitText");

  @Override
  public List<? extends ILookupRow<OrderItemKey>> getDataByKey(ILookupCall<OrderItemKey> call) {
    return call.getKey().getOrderItemType() == ProductTypeCodeType.SpecificCode.ID ? getData(item.itemNr.eq(call.getKey().getItemNr()), (OrderItemLookupCall) call) : getData(product.productNr.eq(call.getKey().getProductNr()), (OrderItemLookupCall) call);
  }

  @Override
  public List<? extends ILookupRow<OrderItemKey>> getDataByText(ILookupCall<OrderItemKey> call) {
    String text = call.getText().replace(call.getWildcard(), "%").toLowerCase();
    return getData(item.description.toLowerCase().like(text).or(product.name.toLowerCase().like(text)), (OrderItemLookupCall) call);
  }

  @Override
  public List<? extends ILookupRow<OrderItemKey>> getDataByAll(ILookupCall<OrderItemKey> call) {
    return getData(null, (OrderItemLookupCall) call);
  }

  @Override
  public List<? extends ILookupRow<OrderItemKey>> getDataByRec(ILookupCall<OrderItemKey> call) {
    return List.of();
  }

  private List<OrderItemRow> getData(Predicate condition, OrderItemLookupCall lookupCall) {
    List<Long> itemNrs = lookupCall.getOrderItems().keySet().stream().toList();
    Long languageId = BEANS.get(LanguageCodeType.class).getCodeByExtKey(NlsLocale.get().getLanguage()).getId();
    List<OrderItemRow> rows = queryFactory.selectDistinct(Projections.constructor(OrderItemRow.class,
          product.productNr,
          product.productType,
          variant.variantNr,
          productUnit.unitNr,
          product.name
            .append(" - ").append(productUnitText.text).append(" ")
            .append(" (").append(variantText.text.coalesce("")).append(")")
        )
      )
      .from(item)
      .leftJoin(product).on(product.productNr.eq(item.productNr))
      .leftJoin(productUnit).on(productUnit.productNr.eq(product.productNr).and(product.statusUid.eq(StatusCodeType.ActiveCode.ID)))
      .leftJoin(productUnitText).on(productUnit.unitNr.eq(productUnitText.ucUid).and(productUnitText.lannguageCode.eq(languageId)))
      .leftJoin(variant).on(item.variantNr.eq(variant.variantNr).and(variant.statusUid.eq(StatusCodeType.ActiveCode.ID)))
      .leftJoin(variantText).on(variant.variantNr.eq(variantText.ucUid).and(variantText.lannguageCode.eq(languageId)))
      .where(
        item.statusUid.eq(StatusCodeType.ActiveCode.ID)
          .and(product.statusUid.eq(StatusCodeType.ActiveCode.ID))
          .and(
            JPAExpressions.select(
                item.itemNr.count().subtract((long) itemNrs.size()))
              .from(item)
              .where(
                item.productNr.eq(product.productNr)
                  .and(product.statusUid.eq(StatusCodeType.ActiveCode.ID))
              ).gt(0L)
          )
          .and(product.productType.eq(ProductTypeCodeType.FelxibleCode.ID))
          .and(condition)
      )
      .fetch();


    rows.addAll(queryFactory.selectDistinct(Projections.constructor(OrderItemRow.class,
          item.itemNr,
          product.productNr,
          product.productType,
          item.variantNr,
          item.itemNo,
          productUnit.unitNr,
          item.description.coalesce(product.name)
            .append(" - ").append(productUnitText.text).append(" ")
            .append(" (").append(variantText.text.coalesce("")).append(")")
        )
      )
      .from(item)
      .join(product).on(product.productNr.eq(item.productNr))
      .leftJoin(productUnit).on(productUnit.productNr.eq(product.productNr).and(product.statusUid.eq(StatusCodeType.ActiveCode.ID)))
      .leftJoin(productUnitText).on(productUnit.unitNr.eq(productUnitText.ucUid).and(productUnitText.lannguageCode.eq(languageId)))
      .leftJoin(variant).on(item.variantNr.eq(variant.variantNr).and(variant.statusUid.eq(StatusCodeType.ActiveCode.ID)))
      .leftJoin(variantText).on(variant.variantNr.eq(variantText.ucUid).and(variantText.lannguageCode.eq(languageId)))
      .where(
        item.statusUid.eq(StatusCodeType.ActiveCode.ID)
          .and(product.statusUid.eq(StatusCodeType.ActiveCode.ID))
          .and(product.productType.eq(ProductTypeCodeType.SpecificCode.ID))
          .and(item.itemNr.notIn(itemNrs))
          .and(item.itemNr.isNotNull())
          .and(condition)
      )
      .fetch());
    return rows;
  }
}
