package ch.scout.warehouse.client.settings;

import ch.scout.warehouse.client.settings.codeTypes.CodeTypeTablePage;
import ch.scout.warehouse.client.settings.product.ProductTablePage;
import ch.scout.warehouse.client.settings.user.UserNodeTablePage;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.platform.text.TEXTS;

import ch.scout.warehouse.shared.Icons;

import java.util.List;

/**
 * @author kiran
 */
@Order(3000)
public class SettingsOutline extends AbstractOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Settings");
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.Gear;
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    pageList.add(new CodeTypeTablePage());
    pageList.add(new UserNodeTablePage());
    pageList.add(new ProductTablePage());
  }
}
