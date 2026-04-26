package ch.scout.warehouse.shared.settings.role;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

@TunnelToServer
public interface IPermissionLookupService extends ILookupService<String> {

}
