package ch.scout.warehouse.server.settings.role;

import ch.scout.warehouse.shared.settings.role.IPermissionLookupService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.security.AbstractPermission;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

import java.util.List;

public class PermissionLookupService extends AbstractLookupService<String> implements IPermissionLookupService {
  @Override
  public List<? extends ILookupRow<String>> getDataByKey(ILookupCall<String> call) {
    return null;
  }

  @Override
  public List<? extends ILookupRow<String>> getDataByText(ILookupCall<String> call) {
    return null;
  }

  @Override
  public List<? extends ILookupRow<String>> getDataByAll(ILookupCall<String> call) {
    return BEANS.all(AbstractPermission.class).stream().map(permission -> new LookupRow<>(permission.getClass().getSimpleName(), permission.getName())).toList();
  }

  @Override
  public List<? extends ILookupRow<String>> getDataByRec(ILookupCall<String> call) {
    return null;
  }
}
