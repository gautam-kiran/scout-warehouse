package ch.scout.warehouse.server.settings.codeTypes;

import ch.scout.warehouse.server.db.DB;
import ch.scout.warehouse.server.db.tables.uc.QUc;
import ch.scout.warehouse.server.db.tables.uc.Uc;
import ch.scout.warehouse.server.db.tables.uc.UcRepository;
import ch.scout.warehouse.server.db.tables.uctext.QUcText;
import ch.scout.warehouse.server.db.tables.uctext.UcText;
import ch.scout.warehouse.server.db.tables.uctext.UcTextRepository;
import ch.scout.warehouse.shared.common.LanguageCodeType;
import ch.scout.warehouse.shared.common.StatusCodeType;
import ch.scout.warehouse.shared.common.codetype.AbstractSqlCodeWithGenericType;
import ch.scout.warehouse.shared.settings.codeTypes.CodeTablePageData;
import ch.scout.warehouse.shared.settings.codeTypes.CodeTypeTablePageData;
import ch.scout.warehouse.shared.settings.codeTypes.ICodeTypeService;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.services.common.code.ICode;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CodeTypeService implements ICodeTypeService {
  JPAQueryFactory queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, DB.getEntityManager());
  QUc uc = QUc.uc;
  QUcText ucText = QUcText.ucText;

  @Override
  public CodeTypeTablePageData getCodeTypeTableData(SearchFilter filter) {
    CodeTypeTablePageData pageData = new CodeTypeTablePageData();
    BEANS.all(AbstractSqlCodeWithGenericType.class).stream()
      .filter(AbstractSqlCodeWithGenericType::getconfiguredEditable)
      .forEach(codeType -> {
        CodeTypeTablePageData.CodeTypeTableRowData row = pageData.addRow();
        row.setName(codeType.getText());
        row.setCodeTypeUid((Long) codeType.getId());
      });
    return pageData;
  }

  @Override
  public CodeTablePageData getCodeTableData(Long codeType) {
    CodeTablePageData pageData = new CodeTablePageData();

    List<CodeTablePageData.CodeTableRowData> rows = queryFactory.select(Projections.fields(CodeTablePageData.CodeTableRowData.class,
        uc.ucUid.as("m_" + CodeTablePageData.CodeTableRowData.ucUid),
        ucText.text.as("m_" + CodeTablePageData.CodeTableRowData.name),
        uc.isBuiltIn.as("m_" + CodeTablePageData.CodeTableRowData.isBuiltIn)
      )).from(uc)
      .join(ucText).on(uc.ucUid.eq(ucText.ucUid))
      .where(
        uc.statusUid.eq(StatusCodeType.ActiveCode.ID)
        .and(uc.codeType.eq(codeType)))
      .fetch();
    pageData.setRows(rows.toArray(new CodeTablePageData.CodeTableRowData[0]));
    return pageData;
  }

  public void synchronzieCodes() {
    List<ICode<Long>> codes = BEANS.all(AbstractSqlCodeWithGenericType.class).stream()
      .map(AbstractSqlCodeWithGenericType::getCodes)
      .flatMap(List::stream)
      .filter(code -> ((ICode<Long>)code).getPartitionId() == 0)
      .toList();

    List<Long> allreadInsertedCodes = queryFactory.select(uc.ucUid)
      .from(uc).where(uc.statusUid.eq(StatusCodeType.ActiveCode.ID))
      .fetch();
    List<Uc> ucs = new ArrayList<>();
    List<UcText> ucTexts = new ArrayList<>();
    for (ICode<Long> code : codes) {
      if (allreadInsertedCodes.contains(code.getId())) {
        ucs.add(updateCode(code));
        ucTexts.add(updateText(code));
      } else {
        ucs.add(createCode(code));
        ucTexts.add(createText(code));
      }
    }

    BEANS.get(UcRepository.class).saveAll(ucs);
    BEANS.get(UcTextRepository.class).saveAll(ucTexts);
  }

  private UcText createText(ICode<Long> code) {
    UcText ucText = new UcText();
    return fillUcText(ucText, code);
  }

  private UcText updateText(ICode<Long> code) {
    UcText ucText = BEANS.get(UcTextRepository.class).findById(code.getId()).get();
    return fillUcText(ucText, code);
  }

  private UcText fillUcText(UcText ucText, ICode<Long> code) {
    ucText.setUcUid(code.getId());
    ucText.setText(code.getText());
    ucText.setLannguageCode(LanguageCodeType.GermandCode.ID);
    return ucText;
  }

  private Uc createCode(ICode<Long> code) {
    Uc uc = new Uc();
    return fillUc(uc, code);
  }

  private Uc updateCode(ICode<Long> code) {
    Uc uc = BEANS.get(UcRepository.class).findById(code.getId()).get();
    return fillUc(uc, code);
  }

  private Uc fillUc(Uc uc, ICode<Long> code) {
    uc.setUcUid(code.getId());
    uc.setCodeType((Long) code.getCodeType().getId());
    uc.setExtKey(code.getExtKey());
    uc.setValue((Double) code.getValue());
    uc.setIsBuiltIn(true);
    uc.setParentKey(Optional.ofNullable(code.getParentCode()).map(ICode::getId).orElse(null));
    return uc;
  }
}
