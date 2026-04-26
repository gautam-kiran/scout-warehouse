package ch.scout.warehouse.shared.options;

import ch.scout.warehouse.shared.db.persistance.IUpdateService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IOptionService extends IUpdateService<OptionFormData> {
 Options getOptions();
}
