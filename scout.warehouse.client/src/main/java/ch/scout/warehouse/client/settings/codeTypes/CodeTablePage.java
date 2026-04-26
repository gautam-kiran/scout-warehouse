package ch.scout.warehouse.client.settings.codeTypes;

import ch.scout.warehouse.shared.common.StatusCodeType;
import ch.scout.warehouse.shared.settings.codeTypes.CodeTablePageData;
import ch.scout.warehouse.shared.settings.codeTypes.ICodeService;
import ch.scout.warehouse.shared.settings.codeTypes.ICodeTypeService;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.Set;

@Data(CodeTablePageData.class)
public class CodeTablePage extends AbstractPageWithTable<CodeTablePage.Table> {
  private Long m_codeType;

  public CodeTablePage(Long codeType) {
    this.m_codeType = codeType;
  }

  @FormData
  public Long getCodeType() {
    return m_codeType;
  }

  @FormData
  public void setCcodeType(Long codeType) {
    this.m_codeType = codeType;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Code");
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(ICodeTypeService.class).getCodeTableData(getCodeType()));
  }

  public class Table extends AbstractTable {

    public IsBuiltInColumn getIsBuiltInColumn() {
      return getColumnSet().getColumnByClass(IsBuiltInColumn.class);
    }

    public UcUidColumn getUcUidColumn() {
      return getColumnSet().getColumnByClass(UcUidColumn.class);
    }

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    @Override
    protected Class<? extends IMenu> getConfiguredDefaultMenu() {
      return EditMenu.class;
    }

    @Order(0)
    public class UcUidColumn extends AbstractLongColumn {
      @Override
      protected boolean getConfiguredVisible() {
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

    @Order(2000)
    public class IsBuiltInColumn extends AbstractBooleanColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("FixedValue");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }

      @Override
      protected boolean getConfiguredVisible() {
        return false;
      }
    }


    @Order(1000)
    public class NewMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("New");
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.EmptySpace);
      }

      @Override
      protected void execAction() {
        CodeForm codeForm = new CodeForm();
        codeForm.setCodeTypeId(getCodeType());
        codeForm.getBuiltInField().setValue(false);
        codeForm.startNew();
        codeForm.waitFor();
        reloadPage();
      }
    }

    @Order(2000)
    public class EditMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("EditCode");
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.SingleSelection);
      }

      @Override
      protected void execAction() {
        CodeForm codeForm = new CodeForm();
        codeForm.setCodeTypeId(getCodeType());
        codeForm.setUcUid(getUcUidColumn().getSelectedValue());
        codeForm.setEnabled(!getIsBuiltInColumn().getValue(getSelectedRow()));
        codeForm.startModify();
        codeForm.waitFor();
        reloadPage();
      }
    }

    @Order(3000)
    public class DeleteMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("DeleteMenu");
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.SingleSelection, TableMenuType.MultiSelection);
      }

      @Override
      protected void execOwnerValueChanged(Object newOwnerValue) {
        setVisible(getIsBuiltInColumn().getValues(getSelectedRows()).stream().allMatch(val -> val.equals(false)));
      }

      @Override
      protected void execAction() {
        BEANS.get(ICodeService.class).updateStatus(getUcUidColumn().getSelectedValues(), StatusCodeType.DeletedCode.ID);
        reloadPage();
      }
    }
  }
}
