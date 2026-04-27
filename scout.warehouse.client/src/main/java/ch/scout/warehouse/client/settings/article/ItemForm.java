package ch.scout.warehouse.client.settings.article;

import ch.scout.warehouse.client.settings.article.ItemForm.MainBox.CancelButton;
import ch.scout.warehouse.client.settings.article.ItemForm.MainBox.GroupBox;
import ch.scout.warehouse.client.settings.article.ItemForm.MainBox.OkButton;
import ch.scout.warehouse.shared.settings.item.*;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;

@FormData(value = ItemFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ItemForm extends AbstractForm {
  private Long productNr;
  private Long itemNr;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Item");
  }
  @FormData
  public Long getProductNr() {
    return productNr;
  }
  @FormData
  public void setProductNr(Long productNr) {
    this.productNr = productNr;
  }
  @FormData
  public Long getItemNr() {
    return itemNr;
  }

  @FormData
  public void setItemNr(Long itemNr) {
    this.itemNr = itemNr;
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public GroupBox.DescriptionField getDescriptionField() {
    return getFieldByClass(GroupBox.DescriptionField.class);
  }

  public GroupBox.IntemNoField getIntemNoField() {
    return getFieldByClass(GroupBox.IntemNoField.class);
  }

  public GroupBox.StatusField getStatusField() {
    return getFieldByClass(GroupBox.StatusField.class);
  }

  public GroupBox.VariantField getVariantField() {
    return getFieldByClass(GroupBox.VariantField.class);
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {
    @Order(1000)
    public class GroupBox extends AbstractGroupBox {

      @Order(1000)
      public class IntemNoField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ItemNo");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
        }
      }

      @Order(2000)
      public class DescriptionField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Description");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
        }
      }

      @Order(3000)
      public class VariantField extends AbstractSmartField<Long> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Variant");
        }
      }

      @Order(4000)
      public class StatusField extends AbstractSmartField<Long> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Status");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
          return ItemStatusCodeType.class;
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
      ItemFormData formData = new ItemFormData();
      exportFormData(formData);
      formData = BEANS.get(IItemService.class).prepareCreate(formData);
      importFormData(formData);

      setEnabledPermission(new CreateItemPermission());
    }

    @Override
    protected void execStore() {
      ItemFormData formData = new ItemFormData();
      exportFormData(formData);
      formData = BEANS.get(IItemService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      ItemFormData formData = new ItemFormData();
      exportFormData(formData);
      formData = BEANS.get(IItemService.class).load(formData);
      importFormData(formData);

      setEnabledPermission(new UpdateItemPermission());
    }

    @Override
    protected void execStore() {
      ItemFormData formData = new ItemFormData();
      exportFormData(formData);
      formData = BEANS.get(IItemService.class).store(formData);
      importFormData(formData);
    }
  }
}
