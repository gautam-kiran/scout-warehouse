package ch.scout.warehouse.shared.settings.item;

import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import org.eclipse.scout.rt.api.data.security.PermissionId;

public class CreateItemPermission extends AbstractScoutWarehousePermission {
    private static final long serialVersionUID = 1L;
    public static final PermissionId ID = PermissionId.of("CreateItemPermission");

    public CreateItemPermission() {
        super(ID);
    }
}
