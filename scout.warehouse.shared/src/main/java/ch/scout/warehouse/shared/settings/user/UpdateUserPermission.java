package ch.scout.warehouse.shared.settings.user;

import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import org.eclipse.scout.rt.api.data.security.PermissionId;


public class UpdateUserPermission extends AbstractScoutWarehousePermission {
    private static final long serialVersionUID = 1L;

    public UpdateUserPermission() {
        super(PermissionId.of("UpdateUserPermission"));
    }
}
