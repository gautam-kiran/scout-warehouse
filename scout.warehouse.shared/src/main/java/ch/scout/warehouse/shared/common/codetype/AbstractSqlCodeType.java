package ch.scout.warehouse.shared.common.codetype;

import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.rt.shared.services.common.code.ICodeRow;

public abstract class AbstractSqlCodeType extends AbstractSqlCodeWithGenericType<ICode<Long>> {

  @Override
  protected ICode<Long> execCreateCodeFromDB(ICodeRow<Long> newRow) {
    return null;
  }
}
