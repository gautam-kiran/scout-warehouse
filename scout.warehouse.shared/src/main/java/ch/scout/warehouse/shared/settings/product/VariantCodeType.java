package ch.scout.warehouse.shared.settings.product;

import ch.scout.warehouse.shared.common.codetype.AbstractSqlCodeType;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class VariantCodeType extends AbstractSqlCodeType {
    private static final long serialVersionUID = 1L;
    public static final long ID = 0L;

    @Override
    public Long getId() {
        return ID;
    }

  @Override
  public boolean getconfiguredEditable() {
    return true;
  }

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("Variant");
  }
}
