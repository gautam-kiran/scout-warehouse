package ch.scout.warehouse.client.work;

import java.util.List;

import ch.scout.warehouse.client.work.order.OrderTablePage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.text.TEXTS;

import ch.scout.warehouse.shared.Icons;

/**
 * @author kiran
 */
@Order(1000)
public class WorkOutline extends AbstractOutline {

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    pageList.add(new OrderTablePage());
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Work");
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.Pencil;
  }
}
