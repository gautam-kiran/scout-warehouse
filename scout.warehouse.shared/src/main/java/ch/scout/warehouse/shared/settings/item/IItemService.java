package ch.scout.warehouse.shared.settings.item;

import ch.scout.warehouse.shared.db.persistance.ICreateService;
import ch.scout.warehouse.shared.db.persistance.IDeleteService;
import ch.scout.warehouse.shared.db.persistance.IUpdateService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IItemService extends ICreateService<ItemFormData>,
  IUpdateService<ItemFormData>,
  IDeleteService<Long> {
}
