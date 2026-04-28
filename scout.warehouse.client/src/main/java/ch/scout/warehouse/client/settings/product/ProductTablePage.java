package ch.scout.warehouse.client.settings.product;

import ch.scout.warehouse.client.common.AbstractExportTable;
import ch.scout.warehouse.client.settings.product.ProductTablePage.Table;
import ch.scout.warehouse.shared.Icons;
import ch.scout.warehouse.shared.common.StatusCodeType;
import ch.scout.warehouse.shared.settings.product.IProductService;
import ch.scout.warehouse.shared.settings.product.ProductTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.Set;

@Data(ProductTablePageData.class)
public class ProductTablePage extends AbstractPageWithTable<Table> {

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IProductService.class).getProductTableData(filter));
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Products");
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.Box;
  }

  @Override
  protected String getConfiguredOverviewIconId() {
    return Icons.Box;
  }

  @Override
  protected IPage<?> execCreateChildPage(ITableRow row) {
    ProductItemSummaryPage page = new ProductItemSummaryPage();
    page.setProductNr(getTable().getProductNrColumn().getValue(row));
    return page;
  }

  public class Table extends AbstractExportTable {

    public AmountColumn getAmountColumn() {
      return getColumnSet().getColumnByClass(AmountColumn.class);
    }

    public CostColumn getCostColumn() {
      return getColumnSet().getColumnByClass(CostColumn.class);
    }

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    public ProductNrColumn getProductNrColumn() {
      return getColumnSet().getColumnByClass(ProductNrColumn.class);
    }

    @Order(1000)
    public class ProductNrColumn extends AbstractLongColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }
    }

    @Order(2000)
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

    @Order(3000)
    public class AmountColumn extends AbstractLongColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Amount");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(4000)
    public class CostColumn extends AbstractLongColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Value");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(1000)
    public class NewProductMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewProduct");
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
        ProductForm form = new ProductForm();
        form.startNew();
        form.waitFor();
        reloadPage();
      }
    }

    @Order(2000)
    public class EditProductMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("EditProduct");
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
        ProductForm form = new ProductForm();
        form.setProductNr(getProductNrColumn().getSelectedValue());
        form.startModify();
        form.waitFor();
        reloadPage();
      }
    }

    @Order(3000)
    public class DeleteProductMenu extends AbstractMenu {
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
        int result = MessageBoxes.createDeleteConfirmationMessage(getSelectedRows()).show();
        if (result == IMessageBox.YES_OPTION) {
          BEANS.get(IProductService.class).updateStatus(getTable().getProductNrColumn().getSelectedValues(), StatusCodeType.DeletedCode.ID);
          reloadPage();
        }
      }
    }
  }
}
