package ch.scout.warehouse.shared.settings.role;

import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import org.eclipse.scout.rt.api.data.security.PermissionId;

public class ReadRolePermission extends AbstractScoutWarehousePermission {
    private static final long serialVersionUID = 1L;

    public ReadRolePermission() {
        super(PermissionId.of("ReadRolePermission"));
    }
}
