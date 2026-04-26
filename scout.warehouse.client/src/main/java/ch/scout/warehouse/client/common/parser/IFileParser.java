package ch.scout.warehouse.client.common.parser;

import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.resource.BinaryResource;

import java.util.List;
@ApplicationScoped
public interface IFileParser {

  List<String[]> parseFile(BinaryResource file);
}
