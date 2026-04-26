package ch.scout.warehouse.client.settings.user;

import ch.scout.warehouse.shared.settings.user.*;
import ch.scout.warehouse.shared.settings.user.roles.RolesLookupCall;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

@FormData(value = UserFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class UserForm extends AbstractForm {
  private Long userNr;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("User");
  }

  @FormData
  public Long getUserNr() {
    return userNr;
  }

  @FormData
  public void setUserNr(Long personNr) {
    this.userNr = personNr;
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

  public MainBox.GroupBox.UserNameField getUserNameField() {
    return getFieldByClass(MainBox.GroupBox.UserNameField.class);
  }

  public MainBox.GroupBox.PasswordField getPassowrdField() {
    return getFieldByClass(MainBox.GroupBox.PasswordField.class);
  }

  public MainBox.GroupBox.RepeatPasswordField getRepeatPasswordField() {
    return getFieldByClass(MainBox.GroupBox.RepeatPasswordField.class);
  }

  public MainBox.GroupBox.RolesBox getRolesBox() {
    return getFieldByClass(MainBox.GroupBox.RolesBox.class);
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {
    @Order(1000)
    public class GroupBox extends AbstractGroupBox {

      @Order(20)
      public class UserNameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Username");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
        }
      }

      @Order(30)
      @ClassId("9fb80aa1-3c3c-4881-8395-7155d69d4425")
      public class PasswordField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Password");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected boolean getConfiguredInputMasked() {
          return true;
        }

        @Override
        protected String execValidateValue(String rawValue) {
          if(rawValue != null && rawValue.trim().length() < 12) {
            throw new VetoException(TEXTS.get("PasswordTooShort"));
          }
          return rawValue;
        }
      }

      @Order(40)
      @ClassId("14d4143c-ceb3-4a3a-8c83-cfeb09b0727f")
      public class RepeatPasswordField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("RepeatPassword");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected boolean getConfiguredInputMasked() {
          return true;
        }

        @Override
        protected String execValidateValue(String rawValue) {
          if (getPassowrdField().getValue() != null && !getPassowrdField().getValue().equals(rawValue)) {
            throw new VetoException(TEXTS.get("PasswordsDoNotMatch"));
          }
          return rawValue;
        }
      }

      @Order(60)
      public class RolesBox extends AbstractListBox<Long> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Roles");
        }

        @Override
        protected byte getConfiguredLabelPosition() {
          return LABEL_POSITION_TOP;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected int getConfiguredGridH() {
          return 6;
        }

        @Override
        protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
          return RolesLookupCall.class;
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
      UserFormData formData = new UserFormData();
      exportFormData(formData);
      formData = BEANS.get(IUserService.class).prepareCreate(formData);
      importFormData(formData);

      setEnabledPermission(new CreateUserPermission());
    }

    @Override
    protected void execStore() {
      UserFormData formData = new UserFormData();
      exportFormData(formData);
      formData = BEANS.get(IUserService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      UserFormData formData = new UserFormData();
      exportFormData(formData);
      formData = BEANS.get(IUserService.class).load(formData);
      importFormData(formData);

      setEnabledPermission(new UpdateUserPermission());

      getPassowrdField().setVisible(false);
      getPassowrdField().setMandatory(false);
      getRepeatPasswordField().setVisible(false);
      getRepeatPasswordField().setMandatory(false);
    }

    @Override
    protected void execStore() {
      UserFormData formData = new UserFormData();
      exportFormData(formData);
      formData = BEANS.get(IUserService.class).store(formData);
      importFormData(formData);
    }
  }
}
