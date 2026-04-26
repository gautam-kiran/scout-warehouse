package ch.scout.warehouse.server.settings.codeTypes;

import ch.scout.warehouse.server.db.persitance.IEntityCreateService;
import ch.scout.warehouse.server.db.persitance.IEntityDeleteService;
import ch.scout.warehouse.server.db.persitance.IEntityUpdateService;
import ch.scout.warehouse.server.db.tables.BaseRepository;
import ch.scout.warehouse.server.db.tables.uc.Uc;
import ch.scout.warehouse.server.db.tables.uc.UcRepository;
import ch.scout.warehouse.server.db.tables.uctext.UcText;
import ch.scout.warehouse.server.db.tables.uctext.UcTextRepository;
import ch.scout.warehouse.shared.common.LanguageCodeType;
import ch.scout.warehouse.shared.security.AbstractScoutWarehousePermission;
import ch.scout.warehouse.shared.settings.codeTypes.*;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.IHolder;

import java.util.List;
import java.util.Map;

public class CodeService implements ICodeService, IEntityCreateService<Uc, CodeFormData, Long>, IEntityUpdateService<Uc, CodeFormData,Long>, IEntityDeleteService<Uc, Long>{

  @Override
  public Uc createImpl(CodeFormData formData, Uc entity) {
    entity.setUcUid(getconfiguredRepository().nextVal());
    Uc uc = IEntityCreateService.super.createImpl(formData, entity);
    UcText ucText = new UcText();
    ucText.setUcUid(uc.getUcUid());
    ucText.setText(formData.getName().getValue());
    ucText.setLannguageCode(LanguageCodeType.GermandCode.ID);
    BEANS.get(UcTextRepository.class).save(ucText);
    reloadCodeType(entity.getCodeType());
    return uc;
  }

  @Override
  public Uc loadImpl(CodeFormData formData) {
    Uc uc = IEntityUpdateService.super.loadImpl(formData);
    BEANS.get(UcTextRepository.class).findById(uc.getUcUid()).ifPresent(ucText -> {
      formData.getName().setValue(ucText.getText());
    });
    return uc;
  }

  @Override
  public Uc storeImpl(Uc entity, CodeFormData formData) {
    BEANS.get(UcTextRepository.class).findById(entity.getUcUid()).ifPresent(ucText -> {
      ucText.setText(formData.getName().getValue());
      ucText.setLannguageCode(LanguageCodeType.GermandCode.ID);
      BEANS.get(UcTextRepository.class).save(ucText);
    });
    reloadCodeType(entity.getCodeType());
    return IEntityUpdateService.super.storeImpl(entity, formData);
  }

  @Override
  public void updateStatus(List<Long> ids, Long status) {
    IEntityDeleteService.super.updateStatus(ids, status);
    BEANS.get(CodeTextService.class).updateStatus(ids, status);
    reloadCodeType(getconfiguredRepository().findById(ids.get(0)).get().getCodeType());
  }

  private void reloadCodeType(Long codeType){
    BEANS.get(org.eclipse.scout.rt.shared.services.common.code.CodeService.class).reloadCodeType(BEANS.get(org.eclipse.scout.rt.shared.services.common.code.CodeService.class).findCodeTypeById(codeType).getClass());
  }

  @Override
  public BidiMap<Class<? extends IHolder<?>>, String> getConfiguredEntityMapping() {
    return new DualHashBidiMap<>(Map.of(
      CodeFormData.UcUidProperty.class, Uc.NativeNames.UC_UID,
      CodeFormData.CodeTypeIdProperty.class, Uc.NativeNames.CODE_TYPE,
      CodeFormData.Value.class, Uc.NativeNames.VALUE,
      CodeFormData.ExtKey.class, Uc.NativeNames.EXT_KEY,
      CodeFormData.BuiltIn.class, Uc.NativeNames.IS_BUILT_IN
    ));
  }

  @Override
  public BaseRepository<Uc, Long> getconfiguredRepository() {
    return BEANS.get(UcRepository.class);
  }

  @Override
  public AbstractScoutWarehousePermission getConfiguredUpdatePermission() {
    return new UpdateCodePermission();
  }

  @Override
  public AbstractScoutWarehousePermission getConfiguredReadPermission() {
    return new ReadCodePermission();
  }

  @Override
  public AbstractScoutWarehousePermission getConfiguredCreatePermission() {
    return new CreateCodePermission();
  }


}
