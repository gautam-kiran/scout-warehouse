package ch.scout.warehouse.server.db.tables.uc;

import ch.scout.warehouse.server.db.tables.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = Uc.NativeNames.UC)
@Entity
@Getter
@Setter
public class Uc extends BaseEntity{

  @Id
  @SequenceGenerator(name = "UC_SEQ", sequenceName = "UC_SEQ", allocationSize = 1, initialValue = 10000)
  @Column(name = NativeNames.UC_UID)
  public Long ucUid;

  @Column(name = NativeNames.CODE_TYPE)
  public Long codeType;

  @Column(name = NativeNames.VALUE)
  public Double value;

  @Column(name = NativeNames.EXT_KEY)
  public String extKey;

  @Column(name = NativeNames.PARENT_KEY)
  public Long parentKey;

  @Column(name = NativeNames.IS_BUILT_IN)
  public Boolean isBuiltIn;

  public static class NativeNames {
    public static final String UC = "UC";
    public static final String UC_UID = "UC_UID";
    public static final String CODE_TYPE = "CODE_TYPE";
    public static final String VALUE = "VALUE";
    public static final String EXT_KEY = "EXT_KEY";
    public static final String PARENT_KEY = "PARENT_KEY";
    public static final String IS_BUILT_IN = "IS_BUILT_IN";
  }
}
