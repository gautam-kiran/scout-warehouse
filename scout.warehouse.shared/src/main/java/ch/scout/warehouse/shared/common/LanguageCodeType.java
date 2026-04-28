package ch.scout.warehouse.shared.common;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeTypeWithGeneric;

import java.util.Locale;

public class LanguageCodeType extends AbstractCodeTypeWithGeneric<Long, Long, AbstractLanguageCode> {
  private static final long serialVersionUID = 1L;
  public static final long ID = 1000L;

  @Override
  public Long getId() {
    return ID;
  }

  @Order(1000)
  public static class GermandCode extends AbstractLanguageCode {
    private static final long serialVersionUID = 1L;
    public static final long ID = 1001L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("German");
    }

    @Override
    public Long getId() {
      return ID;
    }


    @Override
    protected String getConfiguredExtKey() {
      return Locale.GERMAN.getLanguage();
    }

    @Override
    public Locale getConfiguredLocale() {
      return Locale.GERMAN;
    }
  }

  @Order(2000)
  public static class EnglishCode extends AbstractLanguageCode {
    private static final long serialVersionUID = 1L;
    public static final long ID = 1002L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("English");
    }

    @Override
    public Long getId() {
      return ID;
    }

    @Override
    protected String getConfiguredExtKey() {
      return Locale.ENGLISH.getLanguage();
    }

    @Override
    public Locale getConfiguredLocale() {
      return Locale.ENGLISH;
    }
  }
}
