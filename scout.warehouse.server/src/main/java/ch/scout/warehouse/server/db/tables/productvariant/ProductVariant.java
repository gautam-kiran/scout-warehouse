package ch.scout.warehouse.server.db.tables.productvariant;

import ch.scout.warehouse.server.db.tables.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = ProductVariant.NativeNames.PRODUCT_VARIANT)
public class ProductVariant extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = NativeNames.PRODUCT_VARIANT_NR)
  public Long productVariantNr;

  @Column(name = NativeNames.VARIANT_NR)
  public Long variantNr;

  @Column(name = NativeNames.PRODUCT_NR)
  public Long productNr;

  public static class NativeNames {
    public static final String PRODUCT_VARIANT = "PRODUCT_VARIANT";
    public static final String PRODUCT_VARIANT_NR = "PRODUCT_VARIANT_NR";
    public static final String VARIANT_NR = "VARIANT_NR";
    public static final String PRODUCT_NR = "PRODUCT_NR";
  }
}
