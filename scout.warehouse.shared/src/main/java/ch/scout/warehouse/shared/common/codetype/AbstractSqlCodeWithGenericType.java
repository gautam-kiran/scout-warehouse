package ch.scout.warehouse.shared.common.codetype;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeTypeWithGeneric;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.rt.shared.services.common.code.ICodeRow;

import java.util.List;

public abstract class AbstractSqlCodeWithGenericType<CODE extends ICode<Long>> extends AbstractCodeTypeWithGeneric<Long, Long, CODE> {

  @Override
  protected List<? extends ICodeRow<Long>> execLoadCodes(Class<? extends ICodeRow<Long>> codeRowType) {
    return BEANS.get(IUcService.class).loadCodes(getId());
  }

  @Override
  protected CODE execCreateCode(ICodeRow<Long> newRow) {
    CODE code = super.execCreateCode(newRow);
    return code != null ? code : execCreateCodeFromDB(newRow);
  }

  protected abstract CODE execCreateCodeFromDB(ICodeRow<Long> newRow);
  public abstract boolean getconfiguredEditable();
}
