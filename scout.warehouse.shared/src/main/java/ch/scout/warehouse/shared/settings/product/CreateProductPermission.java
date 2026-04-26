package ch.scout.warehouse.shared.settings.product;

import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import org.eclipse.scout.rt.api.data.security.PermissionId;
import org.eclipse.scout.rt.security.AbstractPermission;

public class CreateProductPermission extends AbstractScoutWarehousePermission {
    private static final long serialVersionUID = 1L;
    public static final PermissionId ID = PermissionId.of("CreateProductPermission");

    public CreateProductPermission() {
        super(ID);
    }
}
