package ch.scout.warehouse.client.work.order;

import ch.scout.warehouse.client.settings.item.AbstractItemTable;
import ch.scout.warehouse.client.work.order.OrderForm.MainBox.CancelButton;
import ch.scout.warehouse.client.work.order.OrderForm.MainBox.GroupBox;
import ch.scout.warehouse.client.work.order.OrderForm.MainBox.OkButton;
import ch.scout.warehouse.shared.Icons;
import ch.scout.warehouse.shared.settings.product.UnitCodeType;
import ch.scout.warehouse.shared.work.order.*;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
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
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.CssClasses;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        public class ItemField extends AbstractSmartField<OrderItemKey> {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Item");
          }

          @Override
          protected Class<? extends ILookupCall<OrderItemKey>> getConfiguredLookupCall() {
            return OrderItemLookupCall.class;
          }

          @Override
          protected OrderItemKey execValidateValue(OrderItemKey orderItemKey) {
            if (orderItemKey == null) {
              return null;
            }
            String text = getDisplayText();
            Long itemKey = orderItemKey.getItemNr() == 0L ? null : orderItemKey.getItemNr();

            ITableRow row = getItemTableField().getTable().addRow();
            getItemTableField().getTable().getItemNrColumn().setValue(row, itemKey);
            getItemTableField().getTable().getProductTypeColumn().setValue(row, orderItemKey.getOrderItemType());
            getItemTableField().getTable().getProductNrColumn().setValue(row, orderItemKey.getProductNr());
            getItemTableField().getTable().getVariantColumn().setValue(row, orderItemKey.getVariant());
            getItemTableField().getTable().getItemNoColumn().setValue(row, orderItemKey.getItemNo());
            getItemTableField().getTable().getDescriptionColumn().setValue(row, text);
            getItemTableField().getTable().getAmountColumn().setValue(row, 1L);
            getItemTableField().getTable().getUnitColumn().setValue(row, orderItemKey.getUnit());
            return null;
          }
          @Override
          protected void execPrepareLookup(ILookupCall<OrderItemKey> call) {
            Map<Long, Long> orderItems = getItemTableField().getTable().getRows()
              .stream()
              .collect(Collectors.toMap(
                getItemTableField().getTable().getItemNrColumn()::getValue,
                getItemTableField().getTable().getAmountColumn()::getValue)
              );
            ((OrderItemLookupCall)call).setOrderItems(orderItems);
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

            public AmountColumn getAmountColumn() {
              return getColumnSet().getColumnByClass(AmountColumn.class);
            }

            public OrderItemColumn getOrderItemColumn() {
              return getColumnSet().getColumnByClass(OrderItemColumn.class);
            }

            public UnitColumn getUnitColumn() {
              return getColumnSet().getColumnByClass(UnitColumn.class);
            }

            @Order(1000)
            public class OrderItemColumn extends AbstractLongColumn {
              @Override
              protected boolean getConfiguredDisplayable() {
                return false;
              }
            }

            @Order(2000)
            public class AmountColumn extends AbstractLongColumn {
              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("Amount");
              }

              @Override
              protected int getConfiguredWidth() {
                return 100;
              }

              @Override
              protected boolean getConfiguredEditable() {
                return true;
              }

              @Override
              protected boolean getConfiguredMandatory() {
                return true;
              }
            }

            @Order(2001)
            public class UnitColumn extends AbstractSmartColumn<Long> {
              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("Unit");
              }

              @Override
              protected int getConfiguredWidth() {
                return 100;
              }

              @Override
              protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
                return UnitCodeType.class;
              }

              @Override
              protected boolean getConfiguredEditable() {
                return true;
              }

              @Override
              protected boolean getConfiguredMandatory() {
                return true;
              }
            }

            @Order(1000)
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
                getTable().deleteRows(getTable().getSelectedRows());
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
