package ch.scout.warehouse.shared.settings.item;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class ItemStatusCodeType extends AbstractCodeType<Long, Long> {
    private static final long serialVersionUID = 1L;
    public static final long ID = 350L;

    @Override
    public Long getId() {
        return ID;
    }

  @Order(1000)
  public static class InStockCode extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final long ID = 351L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("InStock");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(2000)
  public static class OrderedCode extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final long ID = 352L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Orderd");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(3000)
  public static class AwardedCode extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final long ID = 353L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Awarded");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(4000)
  public static class DisposedCode extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final long ID = 354L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Disposed");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }
}
