package ch.scout.warehouse.server.work.order;

import ch.scout.warehouse.server.db.persitance.IEntityTableCreateService;
import ch.scout.warehouse.server.db.persitance.IEntityTableUpdateService;
import ch.scout.warehouse.server.db.tables.BaseEntity;
import ch.scout.warehouse.server.db.tables.BaseRepository;
import ch.scout.warehouse.server.db.tables.orderitem.OrderItem;
import ch.scout.warehouse.server.db.tables.orderitem.OrderItemRepository;
import ch.scout.warehouse.server.db.tables.productvariant.ProductVariant;
import ch.scout.warehouse.shared.settings.product.ProductFormData;
import ch.scout.warehouse.shared.work.order.OrderFormData;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.eclipse.scout.rt.platform.BEANS;

import java.util.List;
import java.util.Map;

public class OrderItemService implements
  IEntityTableCreateService<OrderItem, OrderFormData, OrderFormData.ItemTable, Long>,
  IEntityTableUpdateService<OrderItem, OrderFormData, OrderFormData.ItemTable, Long> {

  @Override
  public List<OrderItem> createImpl(List<OrderItem> entities, OrderFormData formData) {
    entities.forEach(entity -> entity.setOrderNr(formData.getOrderNr()));
    return IEntityTableCreateService.super.createImpl(entities, formData);
  }

  @Override
  public List<OrderItem> storeImpl(List<OrderItem> entities, OrderFormData formData) {
    entities.forEach(entity -> entity.setOrderNr(formData.getOrderNr()));
    return IEntityTableUpdateService.super.storeImpl(entities, formData);
  }

  @Override
  public List<OrderItem> loadImpl(OrderFormData formData) {
    return List.of();
  }

  @Override
  public OrderFormData.ItemTable getConfiguredTable(OrderFormData formData) {
    return formData.getItemTable();
  }

  @Override
  public BaseRepository<OrderItem, Long> getconfiguredRepository() {
    return BEANS.get(OrderItemRepository.class);
  }

  @Override
  public BidiMap<String, String> getConfiguredEntityMapping() {
    return new DualHashBidiMap<>(Map.of(
      OrderFormData.ItemTable.ItemTableRowData.orderItem, OrderItem.NativeNames.ORDER_ITEM,
      OrderFormData.ItemTable.ItemTableRowData.itemNr, OrderItem.NativeNames.ORDER_ITEM
    ));
  }
}
