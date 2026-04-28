package ch.scout.warehouse.server.db.tables.orderitem;

import ch.scout.warehouse.server.db.tables.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = OrderItem.NativeNames.ORDER_ITEM)
public class OrderItem extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = NativeNames.ORDER_ITEM_NR)
  public Long orderItemNr;

  @Column(name = NativeNames.ORDER_NR)
  public Long orderNr;

  @Column(name = NativeNames.ITEM_NR)
  public Long itemNr;

  public static class NativeNames {
    public static final String ORDER_ITEM = "ORDER_ITEM";
    public static final String ORDER_ITEM_NR = "ORDER_ITEM_NR";
    public static final String ORDER_NR = "ORDER_NR";
    public static final String ITEM_NR = "ITEM_NR";
  }
}
