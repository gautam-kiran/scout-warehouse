package ch.scout.warehouse.client.common.parser;

import org.eclipse.scout.rt.platform.resource.BinaryResource;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVParser implements IFileParser, IExporter {

  public static final String COMMA_DELIMITER = ";";

  @Override
  public List<String[]> parseFile(BinaryResource file) {
    List<String[]> records = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(file.getContent())))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split(COMMA_DELIMITER);
        records.add(values);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    records.remove(0);
    return records;
  }

  @Override
  public String parseCell(String row) {
    return row + COMMA_DELIMITER;
  }

  @Override
  public String nextLine() {
    return "\n";
  }

  @Override
  public String getConfigururedFileExtension() {
    return "csv";
  }
}
