package ch.scout.warehouse.client.work.order;

import ch.scout.warehouse.shared.Icons;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IReloadReason;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.util.Set;

public abstract class AbstractOrderTable extends AbstractTable {

  public NameColumn getNameColumn() {
    return getColumnSet().getColumnByClass(NameColumn.class);
  }

  public OrderNrColumn getOrderNrColumn() {
    return getColumnSet().getColumnByClass(OrderNrColumn.class);
  }

  public OrderUserColumn getOrderUserColumn() {
    return getColumnSet().getColumnByClass(OrderUserColumn.class);
  }

  public StatusColumn getStatusColumn() {
    return getColumnSet().getColumnByClass(StatusColumn.class);
  }

  @Override
  protected Class<? extends IMenu> getConfiguredDefaultMenu() {
    return EditOrderMenu.class;
  }

  @Order(1000)
  public class OrderNrColumn extends AbstractLongColumn {
    @Override
    protected boolean getConfiguredDisplayable() {
      return false;
    }
  }

  @Order(1500)
  public class NameColumn extends AbstractStringColumn {
    @Override
    protected String getConfiguredHeaderText() {
      return TEXTS.get("Name");
    }

    @Override
    protected int getConfiguredWidth() {
      return 100;
    }
  }

  @Order(2000)
  public class OrderDateColumn extends AbstractDateColumn {
    @Override
    protected String getConfiguredHeaderText() {
      return TEXTS.get("OrderDate");
    }

    @Override
    protected int getConfiguredWidth() {
      return 100;
    }
  }

  @Order(3000)
  public class OrderUserColumn extends AbstractSmartColumn<Long> {
    @Override
    protected String getConfiguredHeaderText() {
      return TEXTS.get("OrderBy");
    }

    @Override
    protected int getConfiguredWidth() {
      return 100;
    }

    @Override
    protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
      return null;
    }
  }

  @Order(4000)
  public class StatusColumn extends AbstractSmartColumn<Long> {
    @Override
    protected String getConfiguredHeaderText() {
      return TEXTS.get("Status");
    }

    @Override
    protected int getConfiguredWidth() {
      return 100;
    }
  }

  @Order(0)
  public class NewOrderMenu extends AbstractMenu {
    @Override
    protected String getConfiguredText() {
      return TEXTS.get("NewOrder");
    }

    @Override
    protected String getConfiguredIconId() {
      return Icons.Add;
    }

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.hashSet(TableMenuType.EmptySpace);
    }

    @Override
    protected void execAction() {
      OrderForm form = new OrderForm();
      form.startNew();
      form.waitFor();
      getReloadHandler().reload(IReloadReason.DATA_CHANGED_TRIGGER);
    }
  }

  @Order(1000)
  public class EditOrderMenu extends AbstractMenu {
    @Override
    protected String getConfiguredText() {
      return TEXTS.get("EditOrder");
    }

    @Override
    protected String getConfiguredIconId() {
      return Icons.Edit;
    }

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.hashSet(TableMenuType.SingleSelection);
    }

    @Override
    protected void execAction() {
      OrderForm form = new OrderForm();
      form.setOrderNr(getOrderNrColumn().getSelectedValue());
      form.startModify();
      form.waitFor();
      getReloadHandler().reload(IReloadReason.DATA_CHANGED_TRIGGER);
    }
  }

  @Order(2000)
  public class DeleteOrderMenu extends AbstractMenu {
    @Override
    protected String getConfiguredText() {
      return TEXTS.get("DeleteMenu");
    }

    @Override
    protected String getConfiguredIconId() {
      return Icons.Delete;
    }

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.hashSet(TableMenuType.SingleSelection, TableMenuType.MultiSelection);
    }

    @Override
    protected void execAction() {

    }
  }
}
