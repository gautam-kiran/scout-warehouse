package ch.scout.warehouse.client;

import java.util.List;

import ch.scout.warehouse.client.info.ScoutWarehouseInfoForm;
import ch.scout.warehouse.client.options.OptionForm;
import ch.scout.warehouse.client.settings.user.ChangePasswordForm;
import ch.scout.warehouse.shared.settings.user.ChangePasswordPermission;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.notification.NativeNotificationDefaults;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.form.fields.button.IButton;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.security.IAccessControlService;

import ch.scout.warehouse.client.settings.SettingsOutline;
import ch.scout.warehouse.client.work.WorkOutline;
import ch.scout.warehouse.shared.Icons;

/**
 * @author kiran
 */
public class Desktop extends AbstractDesktop {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ApplicationTitle");
  }

  @Override
  protected String getConfiguredLogoId() {
    return Icons.AppLogo;
  }

  @Override
  protected NativeNotificationDefaults getConfiguredNativeNotificationDefaults() {
    return super.getConfiguredNativeNotificationDefaults().withIconId("application_logo.png");
  }

  @Override
  protected List<Class<? extends IOutline>> getConfiguredOutlines() {
    return CollectionUtility.arrayList(
      WorkOutline.class, SettingsOutline.class);
  }

  @Override
  protected void execDefaultView() {
    selectFirstVisibleOutline();
  }

  protected void selectFirstVisibleOutline() {
    for (IOutline outline : getAvailableOutlines()) {
      if (outline.isEnabled() && outline.isVisible()) {
        setOutline(outline.getClass());
        return;
      }
    }
  }

  @Order(1000)
  public class UserProfileMenu extends AbstractMenu {

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F10;
    }

    @Override
    protected String getConfiguredIconId() {
      return Icons.PersonSolid;
    }

    @Override
    protected String getConfiguredText() {
      String userId = BEANS.get(IAccessControlService.class).getUserIdOfCurrentSubject();
      return StringUtility.uppercaseFirst(userId);
    }

    @Order(1000)
    public class AboutMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("About");
      }

      @Override
      protected void execAction() {
        ScoutWarehouseInfoForm form = new ScoutWarehouseInfoForm();
        form.startModify();
      }
    }

    @Order(1500)
    public class OptionMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Option");
      }

      @Override
      protected void execAction() {
        OptionForm optionForm = new OptionForm();
        optionForm.startModify();
        optionForm.waitFor();
        if(optionForm.getCloseSystemType() == IButton.SYSTEM_TYPE_OK){
          ClientSession.get().stop();
        }
      }
    }

    @Order(2500)
    public class ChangepassowrdMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ChangePassword");
      }
      @Override
      protected boolean getConfiguredVisible() {
        return ACCESS.check(new ChangePasswordPermission());
      }

      @Override
      protected void execAction() {
        ChangePasswordForm changePasswordForm = new ChangePasswordForm();
        changePasswordForm.setUserName(ClientSession.get().getUserId());
        changePasswordForm.startModify();
      }
    }

    @Order(3000)
    public class LogoutMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Logout");
      }

      @Override
      protected void execAction() {
        ClientSessionProvider.currentSession().stop();
      }
    }
  }

  @Order(1000)
  public class WorkOutlineViewButton extends AbstractOutlineViewButton {

    public WorkOutlineViewButton() {
      this(WorkOutline.class);
    }

    protected WorkOutlineViewButton(Class<? extends WorkOutline> outlineClass) {
      super(Desktop.this, outlineClass);
    }

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F2;
    }
  }


  @Order(3000)
  public class SettingsOutlineViewButton extends AbstractOutlineViewButton {

    public SettingsOutlineViewButton() {
      this(SettingsOutline.class);
    }

    protected SettingsOutlineViewButton(Class<? extends SettingsOutline> outlineClass) {
      super(Desktop.this, outlineClass);
    }

    @Override
    protected DisplayStyle getConfiguredDisplayStyle() {
      return DisplayStyle.TAB;
    }

    @Override
    protected String getConfiguredKeyStroke() {
      return IKeyStroke.F10;
    }
  }
}
