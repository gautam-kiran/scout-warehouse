package ch.scout.warehouse.shared.options;

import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import org.eclipse.scout.rt.api.data.security.PermissionId;

public class UpdateOptionPermission extends AbstractScoutWarehousePermission {
    private static final long serialVersionUID = 1L;

    public UpdateOptionPermission() {
        super(PermissionId.of("UpdateOptionPermission"));
    }
}
