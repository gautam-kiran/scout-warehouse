package ch.scout.warehouse.shared.options;

import ch.scout.warehouse.shared.common.LanguageCodeType;
import ch.scout.warehouse.shared.common.ThemeCodeType;

import java.io.Serializable;

public class Options implements Serializable {
  private Long languageId;
  private Long theme;
  private boolean denseMode;

  public Options() {
    languageId = LanguageCodeType.GermandCode.ID;
    theme = ThemeCodeType.DefaultCode.ID;
  }

  public Long getTheme() {
    return theme;
  }

  public void setTheme(Long theme) {
    this.theme = theme;
  }

  public Long getLanguageId() {
    return languageId;
  }

  public void setLanguageId(Long languageId) {
    this.languageId = languageId;
  }

  public boolean isDenseMode() {
    return denseMode;
  }

  public void setDenseMode(boolean denseMode) {
    this.denseMode = denseMode;
  }
}
