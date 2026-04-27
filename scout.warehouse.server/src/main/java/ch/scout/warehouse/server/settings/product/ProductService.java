package ch.scout.warehouse.server.settings.product;

import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.persitance.IEntityCreateService;
import ch.scout.warehouse.server.db.persitance.IEntityDeleteService;
import ch.scout.warehouse.server.db.persitance.IEntityUpdateService;
import ch.scout.warehouse.server.db.tables.BaseRepository;
import ch.scout.warehouse.server.db.tables.product.Product;
import ch.scout.warehouse.server.db.tables.product.ProductRepository;
import ch.scout.warehouse.server.db.tables.product.QProduct;
import ch.scout.warehouse.server.db.tables.productunit.ProductUnit;
import ch.scout.warehouse.shared.common.StatusCodeType;
import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import ch.scout.warehouse.shared.settings.product.*;
import ch.scout.warehouse.shared.settings.user.UserTablePageData;
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

public class ProductService implements IProductService,
  IEntityCreateService<Product, ProductFormData, Long>,
  IEntityUpdateService<Product, ProductFormData, Long>,
  IEntityDeleteService<Product, Long> {
  JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, DB.getEntityManager());

  @Override
  public ProductTablePageData getProductTableData(SearchFilter filter) {
    ProductTablePageData pageData = new ProductTablePageData();
    QProduct product = new QProduct("product");
    List<ProductTablePageData.ProductTableRowData> rowData = queryFactory.select(Projections.fields(
        ProductTablePageData.ProductTableRowData.class,
        product.productNr.as("m_" + ProductTablePageData.ProductTableRowData.productNr),
        product.name.as("m_" + ProductTablePageData.ProductTableRowData.name)
      ))
      .from(product)
      .where(product.statusUid.eq(StatusCodeType.ActiveCode.ID))
      .fetch();
    pageData.setRows(rowData.toArray(new ProductTablePageData.ProductTableRowData[rowData.size()]));
    return pageData;
  }

  @Override
  public ProductFormData prepareCreateImpl(ProductFormData formData, Product entity) {
    BEANS.get(ProductUnitService.class).preapareCreate(formData);
    BEANS.get(ProductVariantService.class).preapareCreate(formData);
    return IEntityCreateService.super.prepareCreateImpl(formData, entity);
  }

  @Override
  public Product createImpl(ProductFormData formData, Product entity) {
    Product product = IEntityCreateService.super.createImpl(formData, entity);
    formData.setProductNr(product.getProductNr());
    BEANS.get(ProductUnitService.class).create(formData);
    BEANS.get(ProductVariantService.class).create(formData);
    return product;
  }

  @Override
  public Product loadImpl(ProductFormData formData) {
    BEANS.get(ProductUnitService.class).load(formData);
    BEANS.get(ProductVariantService.class).load(formData);
    return IEntityUpdateService.super.loadImpl(formData);
  }

  @Override
  public Product storeImpl(Product entity, ProductFormData formData) {
    BEANS.get(ProductUnitService.class).store(formData);
    BEANS.get(ProductVariantService.class).store(formData);
    return IEntityUpdateService.super.storeImpl(entity, formData);
  }

  @Override
  public AbstractScoutWarehousePermission getConfiguredCreatePermission() {
    return new CreateProductPermission();
  }

  @Override
  public AbstractScoutWarehousePermission getConfiguredUpdatePermission() {
    return new UpdateProductPermission();
  }

  @Override
  public AbstractScoutWarehousePermission getConfiguredReadPermission() {
    return new ReadProductPermission();
  }

  @Override
  public BaseRepository<Product, Long> getconfiguredRepository() {
    return BEANS.get(ProductRepository.class);
  }

  @Override
  public BidiMap<Class<? extends IHolder<?>>, String> getConfiguredEntityMapping() {
    return new DualHashBidiMap<>(Map.of(
      ProductFormData.ProductNrProperty.class, Product.NativeNames.PRODUCT_NR,
      ProductFormData.Name.class, Product.NativeNames.NAME,
      ProductFormData.Cost.class, Product.NativeNames.COST
    ));
  }
}
