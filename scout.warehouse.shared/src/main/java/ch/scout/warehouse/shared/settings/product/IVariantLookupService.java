package ch.scout.warehouse.shared.settings.product;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

@TunnelToServer
public interface IVariantLookupService extends ILookupService<Long> {

}
