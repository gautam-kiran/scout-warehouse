package ch.scout.warehouse.client.settings.product;

import ch.scout.warehouse.client.settings.product.ProductForm.MainBox.CancelButton;
import ch.scout.warehouse.client.settings.product.ProductForm.MainBox.GroupBox;
import ch.scout.warehouse.client.settings.product.ProductForm.MainBox.OkButton;
import ch.scout.warehouse.shared.Icons;
import ch.scout.warehouse.shared.common.StatusCodeType;
import ch.scout.warehouse.shared.settings.product.*;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;

import java.math.BigDecimal;
import java.util.Set;

@FormData(value = ProductFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ProductForm extends AbstractForm {
  private Long productNr;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Product");
  }

  @FormData
  public Long getProductNr() {
    return productNr;
  }

  @FormData
  public void setProductNr(Long productNr) {
    this.productNr = productNr;
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

  public GroupBox.ProductDetailBox.CostField getCostField() {
    return getFieldByClass(GroupBox.ProductDetailBox.CostField.class);
  }

  public GroupBox.ProductDetailBox.NameField getNameField() {
    return getFieldByClass(GroupBox.ProductDetailBox.NameField.class);
  }

  public GroupBox.ProductDetailBox getProductDetailBox() {
    return getFieldByClass(GroupBox.ProductDetailBox.class);
  }

  public GroupBox.ProductDetailBox.ProductTypeField getProductTypeField() {
    return getFieldByClass(GroupBox.ProductDetailBox.ProductTypeField.class);
  }

  public GroupBox.ProductDetailBox.UnitsField getUnitsField() {
    return getFieldByClass(GroupBox.ProductDetailBox.UnitsField.class);
  }

  public GroupBox.VariantBox getVariantBox() {
    return getFieldByClass(GroupBox.VariantBox.class);
  }

  public GroupBox.VariantBox.VariantField getVariantField() {
    return getFieldByClass(GroupBox.VariantBox.VariantField.class);
  }

  @Order(1000)
  public class MainBox extends AbstractGroupBox {
    @Order(1000)
    public class GroupBox extends AbstractTabBox {

      @Order(0)
      public class ProductDetailBox extends AbstractGroupBox {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Details");
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

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

          @Override
          protected boolean getConfiguredMandatory() {
            return true;
          }
        }

        @Order(1500)
        public class ProductTypeField extends AbstractSmartField<Long> {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ProductType");
          }

          @Override
          protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
            return ProductTypeCodeType.class;
          }
        }

        @Order(1500)
        public class CostField extends AbstractBigDecimalField {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Price");
          }

          @Override
          protected BigDecimal getConfiguredMinValue() {
            return new BigDecimal("0");
          }

          @Override
          protected BigDecimal getConfiguredMaxValue() {
            return new BigDecimal("999999999999999999");
          }
        }

        @Order(2000)
        public class UnitsField extends AbstractTableField<UnitsField.Table> {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Units");
          }

          @Override
          protected int getConfiguredGridH() {
            return 6;
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_TOP;
          }

          @ClassId("3e7bd7d1-d2e5-4ba7-b0cf-c47d3c214837")
          public class Table extends AbstractTable {

            public AmountColumn getAmountColumn() {
              return getColumnSet().getColumnByClass(AmountColumn.class);
            }

            public ProductUnitNrColumn getProductUnitNrColumn() {
              return getColumnSet().getColumnByClass(ProductUnitNrColumn.class);
            }

            public StatusColumn getStatusColumn() {
              return getColumnSet().getColumnByClass(StatusColumn.class);
            }

            public UnitColumn getUnitColumn() {
              return getColumnSet().getColumnByClass(UnitColumn.class);
            }

            @Order(1000)
            public class ProductUnitNrColumn extends AbstractLongColumn {
              @Override
              protected boolean getConfiguredDisplayable() {
                return false;
              }
            }

            @Order(2000)
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
              protected boolean getConfiguredEditable() {
                return true;
              }

              @Override
              protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
                return UnitCodeType.class;
              }
            }

            @Order(3000)
            public class AmountColumn extends AbstractLongColumn {
              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("Amount");
              }

              @Override
              protected boolean getConfiguredEditable() {
                return true;
              }

              @Override
              protected int getConfiguredWidth() {
                return 100;
              }
            }

            @Order(4000)
            public class StatusColumn extends AbstractSmartColumn<Long> {
              @Override
              protected boolean getConfiguredDisplayable() {
                return false;
              }

              @Override
              protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
                return StatusCodeType.class;
              }
            }


            @Order(1000)
            public class NewUnitMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("NewUnit");
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
                getStatusColumn().setValue(getTable().addRow(), StatusCodeType.ActiveCode.ID);
              }
            }

            @Order(2000)
            public class DeleteMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("DeleteMenu");
              }

              @Override
              protected String getConfiguredIconId() {
                return Icons.Delete;
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.SingleSelection, TableMenuType.MultiSelection);
              }

              @Override
              protected void execAction() {
                getTable().getSelectedRows().forEach(row -> getTable().getStatusColumn().setValue(row, StatusCodeType.DeletedCode.ID));
                getTable().deleteRows(getTable().getSelectedRows());
              }
            }
          }
        }
      }

      @Order(1000)
      public class VariantBox extends AbstractGroupBox {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Variants");
        }

        @Order(1000)
        public class VariantField extends AbstractTableField<VariantField.Table> {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Variants");
          }

          @Override
          protected int getConfiguredGridH() {
            return 6;
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_TOP;
          }

          @ClassId("761c3a81-e41f-4aeb-8b00-3eabd17fa143")
          public class Table extends AbstractTable {


            public NameColumn getNameColumn() {
              return getColumnSet().getColumnByClass(NameColumn.class);
            }

            public ProductVariantNrColumn getProductVariantNrColumn() {
              return getColumnSet().getColumnByClass(ProductVariantNrColumn.class);
            }

            public StatusColumn getStatusColumn() {
              return getColumnSet().getColumnByClass(StatusColumn.class);
            }

            @Order(1000)
            public class ProductVariantNrColumn extends AbstractLongColumn {

              @Override
              protected boolean getConfiguredDisplayable() {
                return false;
              }
            }

            @Order(2000)
            public class NameColumn extends AbstractSmartColumn<Long> {
              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("Variant");
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
              protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
                return VariantCodeType.class;
              }
            }

            @Order(3000)
            public class StatusColumn extends AbstractSmartColumn<Long> {
              @Override
              protected boolean getConfiguredDisplayable() {
                return false;
              }

              @Override
              protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
                return StatusCodeType.class;
              }
            }

            @Order(1000)
            public class NewVariantMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("NewVariant");
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
                getStatusColumn().setValue(getTable().addRow(), StatusCodeType.ActiveCode.ID);
              }
            }

            @Order(2000)
            public class DeleteVariantMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("DeleteMenu");
              }

              @Override
              protected String getConfiguredIconId() {
                return Icons.Delete;
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.SingleSelection, TableMenuType.MultiSelection);
              }

              @Override
              protected void execAction() {
                getTable().getSelectedRows().forEach(row -> getTable().getStatusColumn().setValue(row, StatusCodeType.DeletedCode.ID));
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

  @Override
  public void validateForm() {
    super.validateForm();
  }

  @Override
  protected boolean execValidate() {
    if (getUnitsField().getTable().getRowCount() > 0) {
      getUnitsField().getTable().getRows().forEach(row -> {
        validateCell(getUnitsField().getTable().getUnitColumn(), row);
        validateCell(getUnitsField().getTable().getAmountColumn(), row);
      });
    }
    if (getVariantField().getTable().getRowCount() > 0) {
      getVariantField().getTable().getRows().forEach(row -> {
        validateCell(getVariantField().getTable().getNameColumn(), row);
      });
    }
    return true;
  }

  private void validateCell(IColumn<?> column, ITableRow row) {
    if (column.getValue(row) == null) {
      throw new VetoException()
        .withTitle(TEXTS.get("FormValidationFailedTitle"))
        .withHtmlMessage(HTML.fragment(TEXTS.get("InvalidFields"), column.getHeaderCell().getText()));
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
      ProductFormData formData = new ProductFormData();
      exportFormData(formData);
      formData = BEANS.get(IProductService.class).prepareCreate(formData);
      importFormData(formData);

      setEnabledPermission(new CreateProductPermission());
    }

    @Override
    protected void execStore() {
      ProductFormData formData = new ProductFormData();
      exportFormData(formData);
      formData = BEANS.get(IProductService.class).create(formData);
      importFormData(formData);
    }
  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      ProductFormData formData = new ProductFormData();
      exportFormData(formData);
      formData = BEANS.get(IProductService.class).load(formData);
      importFormData(formData);

      setEnabledPermission(new UpdateProductPermission());
    }

    @Override
    protected void execStore() {
      ProductFormData formData = new ProductFormData();
      exportFormData(formData);
      formData = BEANS.get(IProductService.class).store(formData);
      importFormData(formData);
    }
  }
}
