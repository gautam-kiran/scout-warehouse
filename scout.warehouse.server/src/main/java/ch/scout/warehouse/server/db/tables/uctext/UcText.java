package ch.scout.warehouse.server.db.tables.uctext;

import ch.scout.warehouse.server.db.tables.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = UcText.NativeNames.UC_TEXT)
@Entity
@Getter
@Setter
public class UcText extends BaseEntity {
  @Id
  @Column(name = NativeNames.UC_UID)
  public Long ucUid;

  @Column(name = NativeNames.LANGUAGE_CODE)
  public Long lannguageCode;

  @Column(name = NativeNames.TEXT)
  public String text;

  public static class NativeNames {
    public static final String UC_UID = "UC_UID";
    public static final String LANGUAGE_CODE = "LANGUAGE_CODE";
    public static final String TEXT = "TEXT";
    public static final String UC_TEXT = "UC_TEXT";
  }
}
