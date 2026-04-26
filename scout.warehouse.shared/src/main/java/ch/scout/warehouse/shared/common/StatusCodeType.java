package ch.scout.warehouse.shared.common;

import ch.scout.warehouse.shared.common.codetype.AbstractSqlCodeType;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class StatusCodeType extends AbstractCodeType<Long, Long> {
    private static final long serialVersionUID = 1L;
    public static final long ID = 100L;

    @Override
    public Long getId() {
        return ID;
    }

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("StatusCode");
  }

  @Order(1000)
  public static class ActiveCode extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final long ID = 101L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Active");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(2000)
  public static class DeletedCode extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final long ID = 102L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Deleted");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }
}
