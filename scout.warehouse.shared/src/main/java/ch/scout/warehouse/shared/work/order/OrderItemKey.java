package ch.scout.warehouse.shared.work.order;

import java.io.Serializable;

public class OrderItemKey implements Serializable {
  private Long itemNr;
  private Long productNr;
  private Long orderItemType;

  public OrderItemKey(){

  }

  public OrderItemKey(Long itemNr, Long productNr, Long orderItemType) {
    this.itemNr = itemNr;
    this.productNr = productNr;
    this.orderItemType = orderItemType;
  }

  public Long getProductNr() {
    return productNr;
  }

  public void setProductNr(Long productNr) {
    this.productNr = productNr;
  }

  public Long getOrderItemType() {
    return orderItemType;
  }

  public void setOrderItemType(Long orderItemType) {
    this.orderItemType = orderItemType;
  }

  public Long getItemNr() {
    return itemNr;
  }

  public void setItemNr(Long itemNr) {
    this.itemNr = itemNr;
  }
}
