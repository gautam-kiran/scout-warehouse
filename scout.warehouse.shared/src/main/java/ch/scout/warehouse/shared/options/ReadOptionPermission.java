package ch.scout.warehouse.shared.options;

import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import org.eclipse.scout.rt.api.data.security.PermissionId;

public class ReadOptionPermission extends AbstractScoutWarehousePermission {
    private static final long serialVersionUID = 1L;

    public ReadOptionPermission() {
        super(PermissionId.of("ReadOptionPermission"));
    }
}
