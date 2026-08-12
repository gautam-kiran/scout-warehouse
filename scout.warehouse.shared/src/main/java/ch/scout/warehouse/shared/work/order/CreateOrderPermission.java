package ch.scout.warehouse.shared.work.order;

import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import org.eclipse.scout.rt.api.data.security.PermissionId;
import org.eclipse.scout.rt.security.AbstractPermission;

public class CreateOrderPermission extends AbstractScoutWarehousePermission {
    private static final long serialVersionUID = 1L;
    public static final PermissionId ID = PermissionId.of("CreateOrderPermission");

    public CreateOrderPermission() {
        super(ID);
    }
}
