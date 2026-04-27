package ch.scout.warehouse.server.settings.product;

import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.persitance.IEntityTableCreateService;
import ch.scout.warehouse.server.db.persitance.IEntityTableUpdateService;
import ch.scout.warehouse.server.db.tables.BaseEntity;
import ch.scout.warehouse.server.db.tables.BaseRepository;
import ch.scout.warehouse.server.db.tables.productunit.ProductUnit;
import ch.scout.warehouse.server.db.tables.productunit.ProductUnitRepository;
import ch.scout.warehouse.server.db.tables.productunit.QProductUnit;
import ch.scout.warehouse.shared.common.StatusCodeType;
import ch.scout.warehouse.shared.settings.product.ProductFormData;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.eclipse.scout.rt.platform.BEANS;

import java.util.List;
import java.util.Map;

public class ProductUnitService implements
  IEntityTableCreateService<ProductUnit, ProductFormData, ProductFormData.Units, Long>,
  IEntityTableUpdateService<ProductUnit, ProductFormData, ProductFormData.Units, Long> {

  JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, DB.getEntityManager());

  @Override
  public List<ProductUnit> loadImpl(ProductFormData formData) {
    QProductUnit product = QProductUnit.productUnit;
    return queryFactory
      .select(product)
      .from(product)
      .where(
        product.productNr.eq(formData.getProductNr())
          .and(product.statusUid.eq(StatusCodeType.ActiveCode.ID))
      )
      .fetch();
  }

  @Override
  public List<ProductUnit> createImpl(List<ProductUnit> entities, ProductFormData formData) {
    entities.forEach(productUnit -> productUnit.setProductNr(formData.getProductNr()));
    return IEntityTableCreateService.super.createImpl(entities, formData);
  }

  @Override
  public List<ProductUnit> storeImpl(List<ProductUnit> entities, ProductFormData formData) {
    entities.forEach(productUnit -> productUnit.setProductNr(formData.getProductNr()));
    return IEntityTableUpdateService.super.storeImpl(entities, formData);
  }

  @Override
  public ProductFormData.Units getConfiguredTable(ProductFormData formData) {
    return formData.getUnits();
  }

  @Override
  public BaseRepository<ProductUnit, Long> getconfiguredRepository() {
    return BEANS.get(ProductUnitRepository.class);
  }

  @Override
  public BidiMap<String, String> getConfiguredEntityMapping() {
    return new DualHashBidiMap<>(Map.of(
      ProductFormData.Units.UnitsRowData.productUnitNr, ProductUnit.NativeNames.PRODUCT_UNIT_NR,
      ProductFormData.Units.UnitsRowData.unit, ProductUnit.NativeNames.UNIT_NR,
      ProductFormData.Units.UnitsRowData.amount, ProductUnit.NativeNames.AMOUNT,
      ProductFormData.Units.UnitsRowData.status, BaseEntity.NativeNames.STATUS_UID
    ));
  }
}
