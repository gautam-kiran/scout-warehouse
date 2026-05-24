package ch.scout.warehouse.shared.settings.product;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class ProductTypeCodeType extends AbstractCodeType<Long, Long> {
    private static final long serialVersionUID = 1L;
    public static final long ID = 400L;

    @Override
    public Long getId() {
        return ID;
    }

  @Order(1000)
  public static class SpecificCode extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final long ID = 401;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Specific");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(2000)
  public static class FelxibleCode extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final long ID = 402;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Flexible");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }
}
