package ch.scout.warehouse.shared.db.persistance;

import org.eclipse.scout.rt.platform.service.IService;

import java.util.List;

public interface IDeleteService<ID> extends IService {

  void updateStatus(List<ID> ids, Long status);
}
