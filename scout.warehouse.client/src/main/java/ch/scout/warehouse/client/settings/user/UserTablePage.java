package ch.scout.warehouse.client.settings.user;

import ch.scout.warehouse.shared.Icons;
import ch.scout.warehouse.shared.common.StatusCodeType;
import ch.scout.warehouse.shared.settings.user.ChangePasswordPermission;
import ch.scout.warehouse.shared.settings.user.IUserService;
import ch.scout.warehouse.shared.settings.user.UserTablePageData;
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
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.Set;

@Data(UserTablePageData.class)
public class UserTablePage extends AbstractPageWithTable<UserTablePage.Table> {
  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    importPageData(BEANS.get(IUserService.class).getUserTableData(filter));
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("User");
  }

  @Override
  protected String getConfiguredOverviewIconId() {
    return  Icons.User;
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.User;
  }

  public class Table extends AbstractTable {

    @Override
    protected Class<? extends IMenu> getConfiguredDefaultMenu() {
      return EditPersonMenu.class;
    }

    public UserNameColumn getUserNameColumn() {
      return getColumnSet().getColumnByClass(UserNameColumn.class);
    }

    public UserNrColumn getUserNrColumn() {
      return getColumnSet().getColumnByClass(UserNrColumn.class);
    }

    @Order(1000)
    public class UserNrColumn extends AbstractLongColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("UserNr");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(2000)
    public class UserNameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Username");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }


    @Order(1000)
    public class NewUserMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewUser");
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
        UserForm form = new UserForm();
        form.startNew();
        form.waitFor();
        reloadPage();
      }
    }

    @Order(2000)
    public class EditPersonMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("EditUser");
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
        UserForm form = new UserForm();
        form.setUserNr(getTable().getUserNrColumn().getValue(getTable().getSelectedRow()));
        form.startModify();
        form.waitFor();
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
        BEANS.get(IUserService.class).updateStatus(getUserNrColumn().getSelectedValues(), StatusCodeType.DeletedCode.ID);
        reloadPage();
      }
    }

    @Order(4000)
    public class ChangePasswordMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ChangePassword");
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.SingleSelection);
      }

      @Override
      protected boolean getConfiguredVisible() {
        return ACCESS.check(new ChangePasswordPermission());
      }

      @Override
      protected void execAction() {
          ChangePasswordForm changePasswordForm = new ChangePasswordForm();
          changePasswordForm.setUserName(getUserNameColumn().getSelectedValue());
          changePasswordForm.startModify();
      }
      @Override
      protected String getConfiguredIconId() {
        return Icons.Lock;
      }
    }
  }
}
