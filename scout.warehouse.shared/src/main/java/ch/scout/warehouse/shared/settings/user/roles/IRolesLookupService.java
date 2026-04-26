package ch.scout.warehouse.shared.settings.user.roles;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

@TunnelToServer
public interface IRolesLookupService extends ILookupService<Long> {

}
