package ch.scout.warehouse.client.work.order;

import ch.scout.warehouse.client.settings.item.AbstractItemTable;
import ch.scout.warehouse.client.work.order.OrderForm.MainBox.CancelButton;
import ch.scout.warehouse.client.work.order.OrderForm.MainBox.GroupBox;
import ch.scout.warehouse.client.work.order.OrderForm.MainBox.OkButton;
import ch.scout.warehouse.shared.work.order.CreateOrderPermission;
import ch.scout.warehouse.shared.work.order.IOrderService;
import ch.scout.warehouse.shared.work.order.OrderFormData;
import ch.scout.warehouse.shared.work.order.UpdateOrderPermission;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.splitbox.AbstractSplitBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.CssClasses;

@FormData(value = OrderFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class OrderForm extends AbstractForm {
  Long orderNr;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Order");
  }

  @FormData
  public Long getOrderNr() {
    return orderNr;
  }

  @FormData
  public void setOrderNr(Long orderNr) {
    this.orderNr = orderNr;
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_VIEW;
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

  public GroupBox.DetailsBox getDetailsBox() {
    return getFieldByClass(GroupBox.DetailsBox.class);
  }

  public GroupBox.OrderBox.ItemField getItemField() {
    return getFieldByClass(GroupBox.OrderBox.ItemField.class);
  }

  public GroupBox.OrderBox.ItemTableField getItemTableField() {
    return getFieldByClass(GroupBox.OrderBox.ItemTableField.class);
  }

  public GroupBox.DetailsBox.NotesField getNotesField() {
    return getFieldByClass(GroupBox.DetailsBox.NotesField.class);
  }

  public GroupBox.OrderBox getOrderBox() {
    return getFieldByClass(GroupBox.OrderBox.class);
  }

  public GroupBox.DetailsBox.OrderDateField getOrderDateField() {
    return getFieldByClass(GroupBox.DetailsBox.OrderDateField.class);
  }

  public GroupBox.DetailsBox.TitleField getTitleField() {
    return getFieldByClass(GroupBox.DetailsBox.TitleField.class);
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {
    @Order(1000)
    public class GroupBox extends AbstractSplitBox {

      @Override
      protected boolean getConfiguredSplitHorizontal() {
        return false;
      }

      @Override
      protected String getConfiguredCssClass() {
        return CssClasses.INVISIBLE_SPLITTER;
      }

      @Order(0)
      public class DetailsBox extends AbstractGroupBox {
        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 2;
        }

        @Order(1000)
        public class TitleField extends AbstractStringField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Title");
          }

          @Override
          protected int getConfiguredMaxLength() {
            return 128;
          }

          @Override
          protected boolean getConfiguredMandatory() {
            return true;
          }
        }

        @Order(2000)
        public class OrderDateField extends AbstractDateField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("OrderDate");
          }

          @Override
          protected boolean getConfiguredMandatory() {
            return true;
          }
        }

        @Order(3000)
        public class NotesField extends AbstractStringField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Notes");
          }

          @Override
          protected boolean getConfiguredMultilineText() {
            return true;
          }

          @Override
          protected boolean getConfiguredWrapText() {
            return true;
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_TOP;
          }

          @Override
          protected int getConfiguredGridH() {
            return 2;
          }

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }
        }
      }

      @Order(1000)
      public class OrderBox extends AbstractGroupBox {
        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Order(1000)
        public class ItemField extends AbstractSmartField<Long> {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Item");
          }
        }

        @Order(2000)
        public class ItemTableField extends AbstractTableField<ItemTableField.Table> {

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected int getConfiguredGridH() {
            return 6;
          }

          @ClassId("2686fd6b-b83c-4f86-9858-5d7003443382")
          public class Table extends AbstractItemTable {

            public OrderItemColumn getOrderItemColumn() {
              return getColumnSet().getColumnByClass(OrderItemColumn.class);
            }

            @Order(1000)
            public class OrderItemColumn extends AbstractLongColumn {
              @Override
              protected boolean getConfiguredDisplayable() {
                return false;
              }
            }

          }
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
      OrderFormData formData = new OrderFormData();
      exportFormData(formData);
      formData = BEANS.get(IOrderService.class).prepareCreate(formData);
      importFormData(formData);

      setEnabledPermission(new CreateOrderPermission());
    }

    @Override
    protected void execStore() {
      OrderFormData formData = new OrderFormData();
      exportFormData(formData);
      formData = BEANS.get(IOrderService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      OrderFormData formData = new OrderFormData();
      exportFormData(formData);
      formData = BEANS.get(IOrderService.class).load(formData);
      importFormData(formData);

      setEnabledPermission(new UpdateOrderPermission());
    }

    @Override
    protected void execStore() {
      OrderFormData formData = new OrderFormData();
      exportFormData(formData);
      formData = BEANS.get(IOrderService.class).store(formData);
      importFormData(formData);
    }
  }
}
