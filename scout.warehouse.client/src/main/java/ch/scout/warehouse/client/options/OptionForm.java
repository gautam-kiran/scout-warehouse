package ch.scout.warehouse.client.options;

import ch.scout.warehouse.shared.common.LanguageCodeType;
import ch.scout.warehouse.shared.common.ThemeCodeType;
import ch.scout.warehouse.shared.options.IOptionService;
import ch.scout.warehouse.shared.options.OptionFormData;
import ch.scout.warehouse.shared.options.UpdateOptionPermission;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;

@FormData(value = OptionFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class OptionForm extends AbstractForm {
  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Option");
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

  public MainBox.GroupBox.DenseModeField getDenseModeField() {
    return getFieldByClass(MainBox.GroupBox.DenseModeField.class);
  }

  public MainBox.GroupBox.LanaguageField getLanaguageField() {
    return getFieldByClass(MainBox.GroupBox.LanaguageField.class);
  }

  public MainBox.GroupBox.ThemeField getThemeField() {
    return getFieldByClass(MainBox.GroupBox.ThemeField.class);
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {
    @Order(1000)
    public class GroupBox extends AbstractGroupBox {
      @Override
      protected int getConfiguredWidthInPixel() {
        return 500;
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(1000)
      public class LanaguageField extends AbstractSmartField<Long> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Language");
        }

        @Override
        protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
          return LanguageCodeType.class;
        }
      }

      @Order(2000)
      public class ThemeField extends AbstractSmartField<Long> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Theme");
        }

        @Override
        protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
          return ThemeCodeType.class;
        }
      }

      @Order(3000)
      public class DenseModeField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DenseMode");
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
    protected void execLoad() {
      OptionFormData formData = new OptionFormData();
      exportFormData(formData);
      formData = BEANS.get(IOptionService.class).load(formData);
      importFormData(formData);

      setEnabledPermission(new UpdateOptionPermission());
    }

    @Override
    protected void execStore() {
      OptionFormData formData = new OptionFormData();
      exportFormData(formData);
      formData = BEANS.get(IOptionService.class).store(formData);
      importFormData(formData);
    }
  }
}
