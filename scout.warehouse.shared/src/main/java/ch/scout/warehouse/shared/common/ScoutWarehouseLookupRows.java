package ch.scout.warehouse.shared.common;

import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

public class ScoutWarehouseLookupRows extends LookupRow<Long> {
  public ScoutWarehouseLookupRows(Long key, String text) {
    super(key, text);
  }

  public ScoutWarehouseLookupRows(Object[] cells, Class<?> keyClass) {
    super(cells, keyClass);
  }

  public ScoutWarehouseLookupRows(Object[] cells, int maxColumnIndex, Class<?> keyClass) {
    super(cells, maxColumnIndex, keyClass);
  }
}
