package ch.scout.warehouse.shared.security;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IPasswordService extends IService {
  boolean verifyPassword(String username, char[] password);
  void changePassword(String username, String newPassword);
}
