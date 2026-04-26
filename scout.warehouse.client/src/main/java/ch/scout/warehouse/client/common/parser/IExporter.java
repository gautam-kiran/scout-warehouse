package ch.scout.warehouse.client.common.parser;

public interface IExporter {

  String parseCell(String rows);
  String nextLine();

  String getConfigururedFileExtension();
}
