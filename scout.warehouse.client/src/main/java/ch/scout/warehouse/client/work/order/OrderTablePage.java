package ch.scout.warehouse.client.work.order;

import ch.scout.warehouse.client.work.order.OrderTablePage.Table;
import ch.scout.warehouse.shared.Icons;
import ch.scout.warehouse.shared.work.order.IOrderService;
import ch.scout.warehouse.shared.work.order.OrderTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@Data(OrderTablePageData.class)
public class OrderTablePage extends AbstractPageWithTable<Table> {

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IOrderService.class).getOrderTableData(filter));
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.Order;
  }

  @Override
  protected String getConfiguredOverviewIconId() {
    return Icons.Order;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Orders");
  }

  public class Table extends AbstractOrderTable {

  }
}
