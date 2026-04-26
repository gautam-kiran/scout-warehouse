package ch.scout.warehouse.client.settings.role;

import ch.scout.warehouse.shared.settings.role.*;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = RoleFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class RoleForm extends AbstractForm {

  private Long roleNr;

  @FormData
  public Long getRoleNr() {
    return roleNr;
  }

  @FormData
  public void setRoleNr(Long roleNr) {
    this.roleNr = roleNr;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Role");
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public MainBox.GroupBox getGroupBox() {
    return getFieldByClass(MainBox.GroupBox.class);
  }

  public MainBox.OkButton getOkButton() {
    return getFieldByClass(MainBox.OkButton.class);
  }

  public MainBox.CancelButton getCancelButton() {
    return getFieldByClass(MainBox.CancelButton.class);
  }

  public MainBox.GroupBox.NameField getNameField() {
    return getFieldByClass(MainBox.GroupBox.NameField.class);
  }

  public MainBox.GroupBox.PermissionBox getPermissionBox() {
    return getFieldByClass(MainBox.GroupBox.PermissionBox.class);
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {
    @Order(1000)
    public class GroupBox extends AbstractGroupBox {

      @Order(1000)
      public class NameField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Name");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
        }
      }

      @Order(2000)
      public class PermissionBox extends AbstractListBox<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Permissions");
        }

        @Override
        protected int getConfiguredGridH() {
          return 6;
        }

        @Override
        protected byte getConfiguredLabelPosition() {
          return LABEL_POSITION_TOP;
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return PermissionLookupCall.class;
        }
      }

    }

    @Order(2000)
    public class OkButton extends AbstractOkButton {

    }

    @Order(3000)
    public class CancelButton extends AbstractCancelButton {

    }
  }

  public void startModify() {
    startInternalExclusive(new ModifyHandler());
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public class NewHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      RoleFormData formData = new RoleFormData();
      exportFormData(formData);
      formData = BEANS.get(IRoleService.class).prepareCreate(formData);
      importFormData(formData);

      setEnabledPermission(new CreateRolePermission());
    }

    @Override
    protected void execStore() {
      RoleFormData formData = new RoleFormData();
      exportFormData(formData);
      formData = BEANS.get(IRoleService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      RoleFormData formData = new RoleFormData();
      exportFormData(formData);
      formData = BEANS.get(IRoleService.class).load(formData);
      importFormData(formData);

      setEnabledPermission(new UpdateRolePermission());
    }

    @Override
    protected void execStore() {
      RoleFormData formData = new RoleFormData();
      exportFormData(formData);
      formData = BEANS.get(IRoleService.class).store(formData);
      importFormData(formData);
    }
  }
}
