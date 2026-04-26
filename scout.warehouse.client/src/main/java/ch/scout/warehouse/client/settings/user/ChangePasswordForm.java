package ch.scout.warehouse.client.settings.user;

import ch.scout.warehouse.shared.security.IPasswordService;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class ChangePasswordForm extends AbstractForm {

  private String userName;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ChangePassword");
  }

  @FormData
  public String getUserName() {
    return userName;
  }

  @FormData
  public void setUserName(String userName) {
    this.userName = userName;
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

  public MainBox.GroupBox.CurrentPasswordField getCurrentPasswordField() {
    return getFieldByClass(MainBox.GroupBox.CurrentPasswordField.class);
  }

  public MainBox.GroupBox.NewPasswordField getNewPasswordField() {
    return getFieldByClass(MainBox.GroupBox.NewPasswordField.class);
  }

  public MainBox.GroupBox.RepeatPasswordField getRepeatPasswordField() {
    return getFieldByClass(MainBox.GroupBox.RepeatPasswordField.class);
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {
    @Order(1000)
    public class GroupBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(1000)
      public class CurrentPasswordField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("CurrentPassword");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
        @Override
        protected boolean getConfiguredInputMasked() {
          return true;
        }
      }

      @Order(2000)
      public class NewPasswordField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("NewPassword");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
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
          if (rawValue != null && rawValue.trim().length() < 12) {
            throw new VetoException(TEXTS.get("PasswordTooShort"));
          }
          return rawValue;
        }
      }

      @Order(3000)
      public class RepeatPasswordField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("RepeatPassword");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
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
          if (getNewPasswordField().getValue() != null && !getNewPasswordField().getValue().equals(rawValue)) {
            throw new VetoException(TEXTS.get("PasswordsDoNotMatch"));
          }
          return rawValue;
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


  public class ModifyHandler extends AbstractFormHandler {

    @Override
    protected void execStore() {
      if(!BEANS.get(IPasswordService.class).verifyPassword(getUserName(), getCurrentPasswordField().getValue().toCharArray())) {
        throw new VetoException(TEXTS.get("TheCurrentPasswordDoesNotMatch"));
      }

      BEANS.get(IPasswordService.class).changePassword(getUserName(), getNewPasswordField().getValue());
    }
  }
}
