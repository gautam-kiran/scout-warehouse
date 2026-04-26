package ch.scout.warehouse.ui.html;

import ch.scout.warehouse.shared.security.IPasswordService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.context.RunContext;
import org.eclipse.scout.rt.platform.context.RunContexts;
import org.eclipse.scout.rt.platform.security.ICredentialVerifier;
import org.eclipse.scout.rt.platform.security.SimplePrincipal;

import javax.security.auth.Subject;
import java.io.IOException;

public class ScoutWarehouseCredentialVerifier implements ICredentialVerifier {
  @Override
  public int verify(String username, char[] password) throws IOException {
    if (username == null || username.isEmpty() || password == null || password.length == 0) {
      return AUTH_CREDENTIALS_REQUIRED;
    }

    Subject subject = new Subject();
    subject.getPrincipals().add(new SimplePrincipal("system"));
    subject.setReadOnly();
    RunContext runContext = RunContexts.copyCurrent(true).withSubject(subject);
    return runContext.call(() -> BEANS.get(IPasswordService.class).verifyPassword(username, password) ? AUTH_OK : AUTH_FAILED);
  }
}
