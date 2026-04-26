package ch.scout.warehouse.client.settings.role;

import ch.scout.warehouse.shared.Icons;
import ch.scout.warehouse.shared.common.StatusCodeType;
import ch.scout.warehouse.shared.settings.role.IRoleService;
import ch.scout.warehouse.shared.settings.role.RoleTablePageData;
import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.Set;

@Data(RoleTablePageData.class)
public class RoleTablePage extends AbstractPageWithTable<RoleTablePage.Table> {
    @Override
    protected boolean getConfiguredLeaf() {
        return true;
    }

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(IRoleService.class).getRoleTableData(filter));
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Role");
    }

  @Override
  protected String getConfiguredOverviewIconId() {
    return Icons.Key;
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.Key;
  }

  public class Table extends AbstractTable {

      @Override
      protected Class<? extends IMenu> getConfiguredDefaultMenu() {
        return EditRoleMenu.class;
      }

      public NameColumn getNameColumn() {
        return getColumnSet().getColumnByClass(NameColumn.class);
      }

      public RoleNrColumn getRoleNrColumn() {
        return getColumnSet().getColumnByClass(RoleNrColumn.class);
      }

      @Order(0)
      public class RoleNrColumn extends AbstractLongColumn {
        @Override
        protected String getConfiguredHeaderText() {
          return TEXTS.get("RoleNr");
        }

        @Override
        protected int getConfiguredWidth() {
          return 100;
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

      @Order(1000)
      public class NewRoleMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return TEXTS.get("NewRole");
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.hashSet(TableMenuType.EmptySpace);
        }

        @Override
        protected String getConfiguredIconId() {
          return Icons.Add;
        }

        @Override
        protected void execAction() {
          RoleForm roleForm = new RoleForm();
          roleForm.startNew();
          roleForm.waitFor();
          reloadPage();
        }
      }

      @Order(2000)
      public class EditRoleMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return TEXTS.get("EditRole");
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(TableMenuType.SingleSelection);
        }

        @Override
        protected String getConfiguredIconId() {
          return Icons.Edit;
        }

        @Override
        protected void execAction() {
          RoleForm roleForm = new RoleForm();
          roleForm.setRoleNr(getRoleNrColumn().getSelectedValue());
          roleForm.startModify();
          roleForm.waitFor();
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
        protected String getConfiguredIconId() {
          return Icons.Delete;
        }

        @Override
        protected void execAction() {
          BEANS.get(IRoleService.class).updateStatus(getRoleNrColumn().getSelectedValues(), StatusCodeType.DeletedCode.ID);
          reloadPage();
        }
      }
    }
}
