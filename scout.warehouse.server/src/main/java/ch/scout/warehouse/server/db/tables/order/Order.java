package ch.scout.warehouse.server.db.tables.order;

import ch.scout.warehouse.server.db.tables.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = Order.NativeNames.ORDERS)
public class Order extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = NativeNames.ORDER_NR)
  public Long orderNr;

  @Column(name = NativeNames.TITLE)
  public String title;

  @Lob
  @Column(name = NativeNames.NOTES)
  public String notes;

  @Column(name = NativeNames.EVT_ORDER)
  public Date evtOrder;

  @Column(name = NativeNames.STATUS)
  public Long status;

  @Column(name = NativeNames.USER_NR)
  public Long userNr;

  public static class NativeNames {
    public static final String ORDERS = "ORDERS";
    public static final String ORDER_NR = "ORDER_NR";
    public static final String TITLE = "TITLE";
    public static final String EVT_ORDER = "EVT_ORDER";
    public static final String STATUS = "STATUS";
    public static final String NOTES = "NOTES";
    public static final String USER_NR = "USER_NR";
  }
}
