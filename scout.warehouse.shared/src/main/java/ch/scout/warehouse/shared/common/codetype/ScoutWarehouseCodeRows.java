package ch.scout.warehouse.shared.common.codetype;

import ch.scout.warehouse.shared.common.StatusCodeType;
import org.eclipse.scout.rt.shared.services.common.code.CodeRow;

public class ScoutWarehouseCodeRows extends CodeRow<Long> {
  public ScoutWarehouseCodeRows(Long key, String text, Long parentKey, Long satusUid, String extKey, Number value, boolean isbuiltIn) {
    super(key, text, "", "", "", "", null, "", true, parentKey, satusUid.equals(StatusCodeType.ActiveCode.ID), extKey, value, isbuiltIn ? 0 : 100);
  }
}
