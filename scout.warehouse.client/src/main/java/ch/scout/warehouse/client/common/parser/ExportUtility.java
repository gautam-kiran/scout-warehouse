package ch.scout.warehouse.client.common.parser;

import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.resource.BinaryResource;

@ApplicationScoped
public class ExportUtility {

  public BinaryResource exportTable(AbstractTable table, IExporter exporter) {
    StringBuilder buffer = new StringBuilder();
    for (int i = 0; i < table.getVisibleColumnCount(); i++) {
      buffer.append(exporter.parseCell(table.getVisibleHeaderCell(i).getText()));
    }
    buffer.append(exporter.nextLine());

    for (int i = 0; i < table.getRowCount(); i++) {
      for (int j = 0; j < table.getVisibleColumnCount(); j++) {
        buffer.append(exporter.parseCell(table.getVisibleCell(i, j).getText()));
      }
      buffer.append(exporter.nextLine());
    }

    return new BinaryResource("Export." + exporter.getConfigururedFileExtension(), buffer.toString().getBytes());
  }
}
