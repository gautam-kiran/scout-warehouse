package ch.scout.warehouse.shared.work.order;

import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import org.eclipse.scout.rt.api.data.security.PermissionId;
import org.eclipse.scout.rt.security.AbstractPermission;

public class UpdateOrderPermission extends AbstractScoutWarehousePermission {
    private static final long serialVersionUID = 1L;
    public static final PermissionId ID = PermissionId.of("UpdateOrderPermission");

    public UpdateOrderPermission() {
        super(ID);
    }
}
