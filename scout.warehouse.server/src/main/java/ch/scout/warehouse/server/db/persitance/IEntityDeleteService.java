package ch.scout.warehouse.server.db.persitance;

import ch.scout.warehouse.server.db.tables.BaseEntity;
import ch.scout.warehouse.server.db.tables.BaseRepository;
import ch.scout.warehouse.shared.common.StatusCodeType;
import ch.scout.warehouse.shared.db.persistance.IDeleteService;

import java.util.Date;
import java.util.List;

public interface IEntityDeleteService<E extends BaseEntity, ID> extends IDeleteService<ID> {

  @Override
  default void updateStatus(List<ID> ids, Long status) {
    for (ID id : ids) {
      getconfiguredRepository().findById(id).ifPresent(entity -> {
        if(status.equals(StatusCodeType.DeletedCode.ID)){
          entity.setEvtDeleted(new Date());
        }
        entity.setStatusUid(status);
        getconfiguredRepository().save(entity);
      });
    }
  }


  BaseRepository<E, ID> getconfiguredRepository();
}
