package ch.scout.warehouse.shared.settings.codeTypes;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface ICodeTypeService extends IService {
    CodeTypeTablePageData getCodeTypeTableData(SearchFilter filter);

  CodeTablePageData getCodeTableData(Long codeType);
}
