package ch.scout.warehouse.client.settings.item;

import ch.scout.warehouse.shared.settings.item.ItemStatusCodeType;
import ch.scout.warehouse.shared.settings.product.ProductTypeCodeType;
import ch.scout.warehouse.shared.settings.product.VariantCodeType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;

public abstract class AbstractItemTable extends AbstractTable {

  public ItemNoColumn getItemNoColumn() {
    return getColumnSet().getColumnByClass(ItemNoColumn.class);
  }

  public DescriptionColumn getDescriptionColumn() {
    return getColumnSet().getColumnByClass(DescriptionColumn.class);
  }

  public ItemNrColumn getItemNrColumn() {
    return getColumnSet().getColumnByClass(ItemNrColumn.class);
  }

  public ProductNrColumn getProductNrColumn() {
    return getColumnSet().getColumnByClass(ProductNrColumn.class);
  }

  public ProductTypeColumn getProductTypeColumn() {
    return getColumnSet().getColumnByClass(ProductTypeColumn.class);
  }



  public VariantColumn getVariantColumn() {
    return getColumnSet().getColumnByClass(VariantColumn.class);
  }

  @Order(1000)
  public class ItemNrColumn extends AbstractLongColumn {

    @Override
    protected boolean getConfiguredDisplayable() {
      return false;
    }
  }

  @Order(1500)
  public class ProductNrColumn extends AbstractLongColumn {
    @Override
    protected boolean getConfiguredDisplayable() {
      return false;
    }
  }

  @Order(1750)
  public class ProductTypeColumn extends AbstractSmartColumn<Long> {
    @Override
    protected boolean getConfiguredDisplayable() {
      return false;
    }

    @Override
    protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
      return ProductTypeCodeType.class;
    }
  }

  @Order(2000)
  public class ItemNoColumn extends AbstractStringColumn {
    @Override
    protected String getConfiguredHeaderText() {
      return TEXTS.get("ItemNo");
    }

    @Override
    protected int getConfiguredWidth() {
      return 100;
    }
  }

  @Order(3000)
  public class DescriptionColumn extends AbstractStringColumn {
    @Override
    protected String getConfiguredHeaderText() {
      return TEXTS.get("Description");
    }

    @Override
    protected int getConfiguredWidth() {
      return 100;
    }
  }

  @Order(4000)
  public class VariantColumn extends AbstractSmartColumn<Long> {
    @Override
    protected String getConfiguredHeaderText() {
      return TEXTS.get("Variant");
    }

    @Override
    protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
      return VariantCodeType.class;
    }

    @Override
    protected int getConfiguredWidth() {
      return 100;
    }
  }


}
