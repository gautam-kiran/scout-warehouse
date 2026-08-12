package ch.scout.warehouse.server.work.order;

import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.persitance.IEntityCreateService;
import ch.scout.warehouse.server.db.persitance.IEntityDeleteService;
import ch.scout.warehouse.server.db.persitance.IEntityUpdateService;
import ch.scout.warehouse.server.db.tables.BaseRepository;
import ch.scout.warehouse.server.db.tables.order.Order;
import ch.scout.warehouse.server.db.tables.order.OrderRepository;
import ch.scout.warehouse.server.db.tables.order.QOrder;
import ch.scout.warehouse.server.settings.user.UserService;
import ch.scout.warehouse.shared.common.StatusCodeType;
import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import ch.scout.warehouse.shared.work.order.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.IHolder;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.user.UserId;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderService implements
  IEntityCreateService<Order, OrderFormData, Long>,
  IEntityUpdateService<Order, OrderFormData, Long>,
  IEntityDeleteService<Order, Long>,
  IOrderService {
  JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, DB.getEntityManager());


  @Override
  public OrderTablePageData getOrderTableData(SearchFilter filter) {
    OrderTablePageData pageData = new OrderTablePageData();
    QOrder order = QOrder.order;
    List<OrderTablePageData.OrderTableRowData> rowData = queryFactory.select(Projections.fields(
        OrderTablePageData.OrderTableRowData.class,
        order.orderNr.as("m_" + OrderTablePageData.OrderTableRowData.orderNr),
        order.evtOrder.as("m_" + OrderTablePageData.OrderTableRowData.orderDate),
        order.title.as("m_" + OrderTablePageData.OrderTableRowData.name),
        order.userNr.as("m_" + OrderTablePageData.OrderTableRowData.orderUser),
        order.status.as("m_" + OrderTablePageData.OrderTableRowData.status)
      ))
      .from(order)
      .where(
        order.statusUid.eq(StatusCodeType.ActiveCode.ID)
      )
      .fetch();
    pageData.setRows(rowData.toArray(new OrderTablePageData.OrderTableRowData[rowData.size()]));
    return pageData;
  }

  @Override
  public OrderFormData prepareCreateImpl(OrderFormData formData, Order entity) {
    LocalDate nextSaturday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
    Date dateNextSaturday = Date.from(nextSaturday.atStartOfDay(ZoneId.systemDefault()).toInstant());
    formData.getOrderDate().setValue(dateNextSaturday);
    BEANS.get(OrderItemService.class).preapareCreate(formData);
    return IEntityCreateService.super.prepareCreateImpl(formData, entity);
  }

  @Override
  public Order createImpl(OrderFormData formData, Order entity) {
    entity.setUserNr(BEANS.get(UserService.class).getUserNrByUsername(UserId.CURRENT.get()));
    BEANS.get(OrderItemService.class).create(formData);
    return IEntityCreateService.super.createImpl(formData, entity);
  }

  @Override
  public Order loadImpl(OrderFormData formData) {
    BEANS.get(OrderItemService.class).load(formData);
    return IEntityUpdateService.super.loadImpl(formData);
  }

  @Override
  public Order storeImpl(Order entity, OrderFormData formData) {
    BEANS.get(OrderItemService.class).store(formData);
    return IEntityUpdateService.super.storeImpl(entity, formData);
  }

  @Override
  public BidiMap<Class<? extends IHolder<?>>, String> getConfiguredEntityMapping() {
    return new DualHashBidiMap<>(Map.of(
      OrderFormData.OrderNrProperty.class, Order.NativeNames.ORDER_NR,
      OrderFormData.Title.class, Order.NativeNames.TITLE,
      OrderFormData.OrderDate.class, Order.NativeNames.EVT_ORDER,
      OrderFormData.Notes.class, Order.NativeNames.NOTES
    ));
  }

  @Override
  public AbstractScoutWarehousePermission getConfiguredCreatePermission() {
    return new CreateOrderPermission();
  }

  @Override
  public AbstractScoutWarehousePermission getConfiguredUpdatePermission() {
    return new UpdateOrderPermission();
  }

  @Override
  public AbstractScoutWarehousePermission getConfiguredReadPermission() {
    return new CreateOrderPermission();
  }

  @Override
  public BaseRepository<Order, Long> getconfiguredRepository() {
    return BEANS.get(OrderRepository.class);
  }

}
