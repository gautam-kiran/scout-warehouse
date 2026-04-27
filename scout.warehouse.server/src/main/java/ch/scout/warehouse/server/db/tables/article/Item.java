package ch.scout.warehouse.server.db.tables.article;

import ch.scout.warehouse.server.db.tables.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = Item.NativeNames.ITEM)
public class Item extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = NativeNames.ITEM_NR)
  public Long itemNr;

  @Column(name = NativeNames.PRODUCT_NR)
  public Long productNr;

  @Column(name = NativeNames.ITEM_NO)
  public String itemNo;

  @Column(name = NativeNames.DESCRIPTION)
  public String description;

  @Column(name = NativeNames.STATUS)
  public Long status;

  @Column(name = NativeNames.VARIANT_NR)
  public Long variantNr;

  public static class NativeNames {
    public static final String ITEM = "ITEM";
    public static final String ITEM_NR = "ITEM_NR";
    public static final String PRODUCT_NR = "PRODUCT_NR";
    public static final String ITEM_NO = "ITEM_NO";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String STATUS = "STATUS";
    public static final String VARIANT_NR = "VARIANT_NR";
  }
}
