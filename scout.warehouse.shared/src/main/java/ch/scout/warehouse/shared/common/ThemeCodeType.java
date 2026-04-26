package ch.scout.warehouse.shared.common;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class ThemeCodeType extends AbstractCodeType<Long, Long> {
    private static final long serialVersionUID = 1L;
    public static final long ID = 200L;

    @Override
    public Long getId() {
        return ID;
    }

  @Order(1000)
  public static class DefaultCode extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final long ID = 201L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Default");
    }

    @Override
    public Long getId() {
      return ID;
    }

    @Override
    protected String getConfiguredExtKey() {
      return "Default";
    }
  }

  @Order(2000)
  public static class DarkCode extends AbstractCode<Long> {
    private static final long serialVersionUID = 1L;
    public static final long ID = 202L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Dark");
    }

    @Override
    public Long getId() {
      return ID;
    }

    @Override
    protected String getConfiguredExtKey() {
      return "dark";
    }
  }
}
