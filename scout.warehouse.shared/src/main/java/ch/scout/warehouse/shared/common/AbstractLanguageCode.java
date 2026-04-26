package ch.scout.warehouse.shared.common;

import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;

import java.util.Locale;

public abstract class AbstractLanguageCode extends AbstractCode<Long> {

  public abstract Locale getConfiguredLocale();
}
