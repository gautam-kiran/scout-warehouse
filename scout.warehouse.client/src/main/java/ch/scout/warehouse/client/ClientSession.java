package ch.scout.warehouse.client;

import ch.scout.warehouse.shared.common.LanguageCodeType;
import ch.scout.warehouse.shared.common.ThemeCodeType;
import ch.scout.warehouse.shared.options.IOptionService;
import ch.scout.warehouse.shared.options.Options;
import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.services.common.code.CODES;

/**
 * @author kiran
 */
public class ClientSession extends AbstractClientSession {

  public ClientSession() {
    super(true);
  }

  /**
   * @return The {@link IClientSession} which is associated with the current thread, or {@code null} if not found.
   */
  public static ClientSession get() {
    return ClientSessionProvider.currentSession(ClientSession.class);
  }

  @Override
  protected void execLoadSession() {
    Options options = BEANS.get(IOptionService.class).getOptions();
    setLocale(BEANS.get(LanguageCodeType.class).getCode(options.getLanguageId()).getConfiguredLocale());
    setDesktop(new Desktop());
    getDesktop().setTheme(BEANS.get(ThemeCodeType.class).getCode(options.getTheme()).getExtKey());
    getDesktop().setDense(options.isDenseMode());
    CODES.getAllCodeTypes("ch.scout.warehouse.scout.warehouse.shared");
    loadInitialSharedVariables();
  }
}
