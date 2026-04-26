package ch.scout.warehouse.server.settings.codeTypes;

import ch.scout.warehouse.server.db.persitance.IEntityDeleteService;
import ch.scout.warehouse.server.db.tables.BaseRepository;
import ch.scout.warehouse.server.db.tables.uctext.UcText;
import ch.scout.warehouse.server.db.tables.uctext.UcTextRepository;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.service.IService;

public class CodeTextService implements IEntityDeleteService<UcText, Long>, IService {
  @Override
  public BaseRepository<UcText, Long> getconfiguredRepository() {
    return BEANS.get(UcTextRepository.class);
  }
}
