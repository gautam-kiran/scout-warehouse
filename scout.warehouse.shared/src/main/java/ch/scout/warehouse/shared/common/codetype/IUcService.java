package ch.scout.warehouse.shared.common.codetype;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.code.ICodeRow;

import java.util.List;

@TunnelToServer
public interface IUcService extends IService {

  List<? extends ICodeRow<Long>> loadCodes(Long id);
}
