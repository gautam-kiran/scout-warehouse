package ch.scout.warehouse.client.common;

import ch.scout.warehouse.client.ClientSession;
import ch.scout.warehouse.client.common.parser.CSVParser;
import ch.scout.warehouse.client.common.parser.ExportUtility;
import ch.scout.warehouse.shared.Icons;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.Set;

public class AbstractExportTable extends AbstractTable {

  @Order(1000)
  public class ExportMenu extends AbstractMenu {

    @Override
    protected String getConfiguredIconId() {
      return Icons.Export;
    }

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.hashSet(TableMenuType.EmptySpace);
    }

    @Override
    protected byte getConfiguredHorizontalAlignment() {
      return HORIZONTAL_ALIGNMENT_RIGHT;
    }

    @Override
    protected void execAction() {
      ClientSession.get().getDesktop().openUri(BEANS.get(ExportUtility.class).exportTable(AbstractExportTable.this, new CSVParser()));
    }
  }
}
