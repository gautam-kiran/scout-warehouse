package ch.scout.warehouse.server.db.tables.product;

import ch.scout.warehouse.server.db.tables.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = Product.NativeNames.PRODUCT)
public class Product extends BaseEntity {

  @Id
  @Column(name = NativeNames.PRODUCT_NR, insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  public Long productNr;

  @Column(name = NativeNames.NAME)
  public String name;

  public static class NativeNames {
    public static final String PRODUCT = "PRODUCT";
    public static final String PRODUCT_NR = "PRODUCT_NR";
    public static final String NAME = "NAME";
  }
}
