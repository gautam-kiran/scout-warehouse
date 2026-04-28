package ch.scout.warehouse.client.settings.product;

import ch.scout.warehouse.client.settings.item.AbstractItemTable;
import ch.scout.warehouse.client.settings.item.ItemForm;
import ch.scout.warehouse.shared.Icons;
import ch.scout.warehouse.shared.settings.product.IProductService;
import ch.scout.warehouse.shared.settings.product.ProductItemSummaryFormData;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.Set;

@FormData(value = ProductItemSummaryFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ProductItemSummaryForm extends AbstractForm {

  private Long productNr;

  public MainBox.GroupBox.DetailBox.AmoutField getAmoutField() {
    return getFieldByClass(MainBox.GroupBox.DetailBox.AmoutField.class);
  }

  public MainBox.GroupBox.DetailBox.CostField getCostField() {
    return getFieldByClass(MainBox.GroupBox.DetailBox.CostField.class);
  }

  public MainBox.GroupBox.DetailBox getDetailBox() {
    return getFieldByClass(MainBox.GroupBox.DetailBox.class);
  }

  public MainBox.GroupBox.ItemBox getItemBox() {
    return getFieldByClass(MainBox.GroupBox.ItemBox.class);
  }

  public MainBox.GroupBox.ItemBox.ItemTableField getItemTableField() {
    return getFieldByClass(MainBox.GroupBox.ItemBox.ItemTableField.class);
  }

  public MainBox.GroupBox.DetailBox.NameField getNameField() {
    return getFieldByClass(MainBox.GroupBox.DetailBox.NameField.class);
  }

  public MainBox.GroupBox.DetailBox.OrdersField getOrdersField() {
    return getFieldByClass(MainBox.GroupBox.DetailBox.OrdersField.class);
  }

  public MainBox.GroupBox.DetailBox.TotalCostField getTotalCostField() {
    return getFieldByClass(MainBox.GroupBox.DetailBox.TotalCostField.class);
  }

  @FormData
  public Long getProductNr() {
    return productNr;
  }

  @FormData
  public void setProductNr(Long productNr) {
    this.productNr = productNr;
  }

  public class MainBox extends AbstractGroupBox {

    public class GroupBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Order(1000)
      public class DetailBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Order(1000)
        public class NameField extends AbstractLabelField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Name");
          }

        }

        @Order(2000)
        public class AmoutField extends AbstractLabelField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Amount");
          }

        }

        @Order(2500)
        public class CostField extends AbstractLabelField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Value");
          }
        }

        @Order(2750)
        public class TotalCostField extends AbstractLabelField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("TotalValue");
          }
        }

        @Order(3000)
        public class OrdersField extends AbstractTableField<OrdersField.Table> {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Orders");
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_TOP;
          }

          @Override
          protected int getConfiguredGridH() {
            return 6;
          }

          @ClassId("f3dbae89-ab7d-4037-9308-183679c1ef54")
          public class Table extends AbstractTable {

            public OrderByColumn getOrderByColumn() {
              return getColumnSet().getColumnByClass(OrderByColumn.class);
            }

            public OrderDateColumn getOrderDateColumn() {
              return getColumnSet().getColumnByClass(OrderDateColumn.class);
            }

            public OrderNrColumn getOrderNrColumn() {
              return getColumnSet().getColumnByClass(OrderNrColumn.class);
            }

            @Order(1000)
            public class OrderNrColumn extends AbstractLongColumn {
              @Override
              protected boolean getConfiguredDisplayable() {
                return false;
              }
            }

            @Order(2000)
            public class OrderDateColumn extends AbstractDateColumn {
              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("OrderDate");
              }

              @Override
              protected int getConfiguredWidth() {
                return 100;
              }
            }

            @Order(3000)
            public class OrderByColumn extends AbstractSmartColumn<Long> {
              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("OrderBy");
              }

              @Override
              protected int getConfiguredWidth() {
                return 100;
              }
            }

            @Order(1000)
            public class OpenOrderMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("OpenOrder");
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.SingleSelection);
              }

              @Override
              protected void execAction() {

              }
            }
          }
        }
      }


      @Order(1000)
      public class ItemBox extends AbstractGroupBox {
        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Order(1000)
        public class ItemTableField extends AbstractTableField<ItemTableField.Table> {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Items");
          }

          @Override
          protected int getConfiguredGridH() {
            return 6;
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_TOP;
          }

          @ClassId("898f1583-74df-4c74-b2f8-b5b93eb45a64")
          public class Table extends AbstractItemTable {

            @Order(1000)
            public class NewItemMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("NewItem");
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
                ItemForm form = new ItemForm();
                form.setProductNr(getProductNr());
                form.startNew();
                form.waitFor();
                getForm().doReset();
              }
            }

            @Order(2000)
            public class EditItemMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("EditItem");
              }

              @Override
              protected String getConfiguredIconId() {
                return Icons.Edit;
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.SingleSelection);
              }

              @Override
              protected void execAction() {
                ItemForm form = new ItemForm();
                form.setProductNr(getProductNr());
                form.setItemNr(getTable().getItemNrColumn().getSelectedValue());
                form.startModify();
                form.waitFor();
                getForm().doReset();
              }
            }
          }
        }
      }
    }
  }

  public static class FormHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      ProductItemSummaryFormData formData = new ProductItemSummaryFormData();
      getForm().exportFormData(formData);
      formData = BEANS.get(IProductService.class).loadItemSummary(formData);
      getForm().importFormData(formData);
    }
  }
}
