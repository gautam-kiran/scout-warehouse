package ch.scout.warehouse.shared.settings.role;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

public class PermissionLookupCall extends LookupCall<String> {
    private static final long serialVersionUID = 1L;

    @Override
    protected Class<? extends ILookupService<String>> getConfiguredService() {
        return IPermissionLookupService.class;
    }
}
