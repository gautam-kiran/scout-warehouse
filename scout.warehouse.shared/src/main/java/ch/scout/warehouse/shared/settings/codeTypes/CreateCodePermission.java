package ch.scout.warehouse.shared.settings.codeTypes;

import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import org.eclipse.scout.rt.api.data.security.PermissionId;

public class CreateCodePermission extends AbstractScoutWarehousePermission {
    private static final long serialVersionUID = 1L;

    public CreateCodePermission() {
        super(PermissionId.of("CreateCodePermission"));
    }
}
