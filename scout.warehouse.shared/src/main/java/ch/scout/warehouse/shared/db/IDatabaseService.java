package ch.scout.warehouse.shared.db;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IDatabaseService  extends IService {

  String getDatabaseName();

}
