package ch.scout.warehouse.server.settings.item;

import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.persitance.IEntityCreateService;
import ch.scout.warehouse.server.db.persitance.IEntityDeleteService;
import ch.scout.warehouse.server.db.persitance.IEntityUpdateService;
import ch.scout.warehouse.server.db.tables.BaseRepository;
import ch.scout.warehouse.server.db.tables.article.Item;
import ch.scout.warehouse.server.db.tables.article.ItemRepository;
import ch.scout.warehouse.server.db.tables.article.QItem;
import ch.scout.warehouse.shared.common.StatusCodeType;
import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import ch.scout.warehouse.shared.settings.item.*;
import ch.scout.warehouse.shared.settings.product.ProductItemSummaryFormData;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.IHolder;

import java.util.List;
import java.util.Map;

public class ItemService implements
  IEntityCreateService<Item, ItemFormData, Long>,
  IEntityUpdateService<Item, ItemFormData, Long>,
  IEntityDeleteService<Item, Long>,
  IItemService {
  JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, DB.getEntityManager());

  @Override
  public AbstractScoutWarehousePermission getConfiguredCreatePermission() {
    return new CreateItemPermission();
  }

  @Override
  public AbstractScoutWarehousePermission getConfiguredUpdatePermission() {
    return new UpdateItemPermission();
  }

  @Override
  public AbstractScoutWarehousePermission getConfiguredReadPermission() {
    return new ReadItemPermission();
  }

  @Override
  public BaseRepository<Item, Long> getconfiguredRepository() {
    return BEANS.get(ItemRepository.class);
  }

  @Override
  public BidiMap<Class<? extends IHolder<?>>, String> getConfiguredEntityMapping() {
    return new DualHashBidiMap<>(Map.of(
      ItemFormData.ItemNrProperty.class, Item.NativeNames.ITEM_NR,
      ItemFormData.ProductNrProperty.class, Item.NativeNames.PRODUCT_NR,
      ItemFormData.IntemNo.class, Item.NativeNames.ITEM_NO,
      ItemFormData.Description.class, Item.NativeNames.DESCRIPTION,
      ItemFormData.Status.class, Item.NativeNames.STATUS,
      ItemFormData.Variant.class, Item.NativeNames.VARIANT_NR
    ));
  }

  public void loadTablePage(ProductItemSummaryFormData.ItemTable itemTable, Long productNr) {
    QItem item = new QItem("item");
    List<ProductItemSummaryFormData.ItemTable.ItemTableRowData> rowData = queryFactory.select(Projections.fields(
        ProductItemSummaryFormData.ItemTable.ItemTableRowData.class,
        item.itemNr.as("m_" + ProductItemSummaryFormData.ItemTable.ItemTableRowData.itemNr),
        item.itemNo.as("m_" + ProductItemSummaryFormData.ItemTable.ItemTableRowData.itemNo),
        item.description.as("m_" + ProductItemSummaryFormData.ItemTable.ItemTableRowData.name),
        item.variantNr.as("m_" + ProductItemSummaryFormData.ItemTable.ItemTableRowData.variant),
        item.status.as("m_" + ProductItemSummaryFormData.ItemTable.ItemTableRowData.status)
      ))
      .from(item)
      .where(
        item.productNr.eq(productNr).and(
          item.statusUid.eq(StatusCodeType.ActiveCode.ID))
      )
      .fetch();
    itemTable.setRows(rowData.toArray(new ProductItemSummaryFormData.ItemTable.ItemTableRowData[rowData.size()]));
  }
}
