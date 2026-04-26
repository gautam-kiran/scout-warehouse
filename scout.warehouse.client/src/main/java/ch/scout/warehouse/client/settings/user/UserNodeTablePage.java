package ch.scout.warehouse.client.settings.user;

import ch.scout.warehouse.client.settings.role.RoleTablePage;
import ch.scout.warehouse.shared.Icons;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.text.TEXTS;

import java.util.List;


public class UserNodeTablePage extends AbstractPageWithNodes {
  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("User");
  }
  @Override
  protected String getConfiguredOverviewIconId() {
    return Icons.User;
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.User;
  }

  @Override
  protected boolean getConfiguredShowTileOverview() {
    return true;
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    pageList.add(new UserTablePage());
    pageList.add(new RoleTablePage());
  }


}
