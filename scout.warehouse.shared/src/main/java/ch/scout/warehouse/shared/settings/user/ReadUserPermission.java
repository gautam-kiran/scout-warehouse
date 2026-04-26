package ch.scout.warehouse.shared.settings.user;

import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import org.eclipse.scout.rt.api.data.security.PermissionId;


public class ReadUserPermission extends AbstractScoutWarehousePermission {
    private static final long serialVersionUID = 1L;

    public ReadUserPermission() {
        super(PermissionId.of("ReadUserPermission"));
    }
}
