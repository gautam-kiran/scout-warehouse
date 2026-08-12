package ch.scout.warehouse.shared.work.order;

import java.io.Serializable;

public class OrderItemKey implements Serializable {
  private Long itemNr;
  private Long productNr;
  private Long orderItemType;
  private Long variant;
  private String itemNo;
  private Long unit;

  public OrderItemKey(){

  }

  public OrderItemKey(Long itemNr, Long productNr, Long orderItemType, Long vairant,String itemNo, Long unit) {
    this.itemNr = itemNr;
    this.productNr = productNr;
    this.orderItemType = orderItemType;
    this.variant = vairant;
    this.itemNo = itemNo;
    this.unit = unit;
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

  public Long getVariant() {
    return variant;
  }

  public void setVariant(Long variant) {
    this.variant = variant;
  }

  public String getItemNo() {
    return itemNo;
  }

  public void setItemNo(String itemNo) {
    this.itemNo = itemNo;
  }

  public Long getUnit() {
    return unit;
  }

  public void setUnit(Long unit) {
    this.unit = unit;
  }
}
