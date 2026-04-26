package ch.scout.warehouse.shared.security;

import org.eclipse.scout.rt.api.data.security.PermissionId;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.security.AbstractPermission;

@ApplicationScoped
public abstract class AbstractScoutWarehousePermission extends AbstractPermission {
  public AbstractScoutWarehousePermission(PermissionId name) {
    super(name);
  }
}
