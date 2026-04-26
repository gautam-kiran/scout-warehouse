package ch.scout.warehouse.client.settings.codeTypes;

import ch.scout.warehouse.shared.settings.codeTypes.CodeFormData;
import ch.scout.warehouse.shared.settings.codeTypes.CreateCodePermission;
import ch.scout.warehouse.shared.settings.codeTypes.ICodeService;
import ch.scout.warehouse.shared.settings.codeTypes.UpdateCodePermission;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.math.BigDecimal;

@FormData(value = CodeFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class CodeForm extends AbstractForm {
  Long m_codeTypeId;
  Long m_ucUid;

  @FormData
  public Long getCodeTypeId() {
    return m_codeTypeId;
  }

  @FormData
  public void setCodeTypeId(Long codeTypeId) {
    this.m_codeTypeId = codeTypeId;
  }
  @FormData
  public Long getUcUid() {
    return m_ucUid;
  }
  @FormData
  public void setUcUid(Long m_ucUid) {
    this.m_ucUid = m_ucUid;
  }

  @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Code");
    }

  public MainBox.GroupBox.BuiltInField getBuiltInField() {
    return getFieldByClass(MainBox.GroupBox.BuiltInField.class);
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

  public MainBox.GroupBox.ExtKeyField getExtKeyField() {
    return getFieldByClass(MainBox.GroupBox.ExtKeyField.class);
  }

  public MainBox.GroupBox.NameField getNameField() {
    return getFieldByClass(MainBox.GroupBox.NameField.class);
  }

  public MainBox.GroupBox.ValueField getValueField() {
    return getFieldByClass(MainBox.GroupBox.ValueField.class);
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
          public class ExtKeyField extends AbstractStringField {
            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("ExtKey");
            }

            @Override
            protected int getConfiguredMaxLength() {
              return 128;
            }
          }

          @Order(3000)
          public class ValueField extends AbstractBigDecimalField {
            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Value");
            }

            @Override
            protected BigDecimal getConfiguredMinValue() {
              return new BigDecimal("-999999999999999999");
            }

            @Override
            protected BigDecimal getConfiguredMaxValue() {
              return new BigDecimal("999999999999999999");
            }
          }

          @Order(4000)
          public class BuiltInField extends AbstractBooleanField {
            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("FixedValue");
            }

            @Override
            protected boolean getConfiguredVisible() {
              return false;
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
            CodeFormData formData = new CodeFormData();
            exportFormData(formData);
            formData = BEANS.get(ICodeService.class).prepareCreate(formData);
            importFormData(formData);

            setEnabledPermission(new CreateCodePermission());
        }

        @Override
        protected void execStore() {
            CodeFormData formData = new CodeFormData();
            exportFormData(formData);
            formData = BEANS.get(ICodeService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            CodeFormData formData = new CodeFormData();
            exportFormData(formData);
            formData = BEANS.get(ICodeService.class).load(formData);
            importFormData(formData);

            setEnabledPermission(new UpdateCodePermission());
        }

        @Override
        protected void execStore() {
            CodeFormData formData = new CodeFormData();
            exportFormData(formData);
            formData = BEANS.get(ICodeService.class).store(formData);
            importFormData(formData);
        }
    }
}
