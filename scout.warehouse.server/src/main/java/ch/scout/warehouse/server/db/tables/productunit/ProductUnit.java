package ch.scout.warehouse.server.db.tables.productunit;

import ch.scout.warehouse.server.db.tables.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = ProductUnit.NativeNames.PRODUCT_UNIT)
public class ProductUnit extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = NativeNames.PRODUCT_UNIT_NR)
  public Long productUnitNr;

  @Column(name = NativeNames.UNIT_NR)
  public Long unitNr;

  @Column(name = NativeNames.AMOUNT)
  public Long amount;

  @Column(name = NativeNames.PRODUCT_NR)
  public Long productNr;

  public static class NativeNames {
    public static final String PRODUCT_UNIT = "PRODUCT_UNIT";
    public static final String PRODUCT_UNIT_NR = "PRODUCT_UNIT_NR";
    public static final String PRODUCT_NR = "PRODUCT_NR";
    public static final String UNIT_NR = "UNIT_NR";
    public static final String AMOUNT = "AMOUNT";
  }
}
