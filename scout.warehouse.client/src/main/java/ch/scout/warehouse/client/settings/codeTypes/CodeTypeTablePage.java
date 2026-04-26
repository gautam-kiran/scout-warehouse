package ch.scout.warehouse.client.settings.codeTypes;

import ch.scout.warehouse.shared.Icons;
import ch.scout.warehouse.shared.settings.codeTypes.CodeTypeTablePageData;
import ch.scout.warehouse.shared.settings.codeTypes.ICodeTypeService;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@Data(CodeTypeTablePageData.class)
public class CodeTypeTablePage extends AbstractPageWithTable<CodeTypeTablePage.Table> {

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(ICodeTypeService.class).getCodeTypeTableData(filter));
    }

  @Override
  protected IPage<?> execCreateChildPage(ITableRow row) {
    return new CodeTablePage(getTable().getCodeTypeUidColumn().getValue(row));
  }

  @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("CodeTypeTablePage");
    }

  @Override
  protected String getConfiguredOverviewIconId() {
    return Icons.List;
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.List;
  }

  public class Table extends AbstractTable {
      public CodeTypeUidColumn getCodeTypeUidColumn() {
        return getColumnSet().getColumnByClass(CodeTypeUidColumn.class);
      }

      public NameColumn getNameColumn() {
        return getColumnSet().getColumnByClass(NameColumn.class);
      }

      @Order(2000)
      public class CodeTypeUidColumn extends AbstractLongColumn {
        @Override
        protected boolean getConfiguredDisplayable() {
          return false;
        }
      }
      @Order(1000)
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
    }
}
