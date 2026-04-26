package ch.scout.warehouse.client.info;

import ch.scout.warehouse.shared.db.IDatabaseService;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.config.PlatformConfigProperties;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.Map;

public class ScoutWarehouseInfoForm extends ScoutInfoForm {

  @Override
  protected Map<String, Object> getProperties() {
    Map<String, Object> properties = super.getProperties();
    properties.put(TEXTS.get("Database"), BEANS.get(IDatabaseService.class).getDatabaseName());
    properties.put(TEXTS.get("Version"), CONFIG.getPropertyValue(PlatformConfigProperties.ApplicationVersionProperty.class));
    return properties;
  }
}
