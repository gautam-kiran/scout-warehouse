package ch.scout.warehouse.server.settings.product;

import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.persitance.IEntityTableCreateService;
import ch.scout.warehouse.server.db.persitance.IEntityTableUpdateService;
import ch.scout.warehouse.server.db.tables.BaseEntity;
import ch.scout.warehouse.server.db.tables.BaseRepository;
import ch.scout.warehouse.server.db.tables.productvariant.ProductVariant;
import ch.scout.warehouse.server.db.tables.productvariant.ProductVariantRepository;
import ch.scout.warehouse.server.db.tables.productvariant.QProductVariant;
import ch.scout.warehouse.shared.common.StatusCodeType;
import ch.scout.warehouse.shared.settings.product.ProductFormData;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.eclipse.scout.rt.platform.BEANS;

import java.util.List;
import java.util.Map;

public class ProductVariantService implements
  IEntityTableCreateService<ProductVariant, ProductFormData, ProductFormData.Variant, Long>,
  IEntityTableUpdateService<ProductVariant, ProductFormData, ProductFormData.Variant, Long> {
  JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, DB.getEntityManager());

  @Override
  public List<ProductVariant> loadImpl(ProductFormData formData) {
    QProductVariant variant = QProductVariant.productVariant;
    return queryFactory
      .select(variant)
      .from(variant)
      .where(
        variant.productNr.eq(formData.getProductNr()).and(
        variant.statusUid.eq(StatusCodeType.ActiveCode.ID))
      )
      .fetch();
  }

  @Override
  public List<ProductVariant> createImpl(List<ProductVariant> entities, ProductFormData formData) {
    entities.forEach(productVariant -> productVariant.setProductNr(formData.getProductNr()));
    return IEntityTableCreateService.super.createImpl(entities, formData);
  }

  @Override
  public List<ProductVariant> storeImpl(List<ProductVariant> entities, ProductFormData formData) {
    entities.forEach(productVariant ->  productVariant.setProductNr(formData.getProductNr()));
    return IEntityTableUpdateService.super.storeImpl(entities, formData);
  }

  @Override
  public ProductFormData.Variant getConfiguredTable(ProductFormData formData) {
    return formData.getVariant();
  }

  @Override
  public BaseRepository<ProductVariant, Long> getconfiguredRepository() {
    return BEANS.get(ProductVariantRepository.class);
  }

  @Override
  public BidiMap<String, String> getConfiguredEntityMapping() {
    return new DualHashBidiMap<>(Map.of(
      ProductFormData.Variant.VariantRowData.productVariantNr, ProductVariant.NativeNames.PRODUCT_VARIANT_NR,
      ProductFormData.Variant.VariantRowData.name, ProductVariant.NativeNames.VARIANT_NR,
      ProductFormData.Variant.VariantRowData.status, BaseEntity.NativeNames.STATUS_UID
    ));
  }
}
