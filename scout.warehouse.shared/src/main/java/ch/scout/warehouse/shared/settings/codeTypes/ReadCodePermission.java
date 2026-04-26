package ch.scout.warehouse.shared.settings.codeTypes;

import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import org.eclipse.scout.rt.api.data.security.PermissionId;

public class ReadCodePermission extends AbstractScoutWarehousePermission {
    private static final long serialVersionUID = 1L;

    public ReadCodePermission() {
        super(PermissionId.of("ReadCodePermission"));
    }
}
