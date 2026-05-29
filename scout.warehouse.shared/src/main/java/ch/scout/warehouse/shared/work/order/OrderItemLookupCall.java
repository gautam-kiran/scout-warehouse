package ch.scout.warehouse.shared.work.order;

import org.eclipse.scout.rt.platform.util.Pair;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

import java.util.List;
import java.util.Map;

public class OrderItemLookupCall extends LookupCall<OrderItemKey> {
  private static final long serialVersionUID = 1L;
  Map<Pair<Long,Long>, Long> productAmount;
  List<Long> itemNrs;

  @Override
  protected Class<? extends ILookupService<OrderItemKey>> getConfiguredService() {
    return IOrderItemLookupService.class;
  }

  public Map<Pair<Long,Long>, Long> getProductAmount() {
    return productAmount;
  }

  public void setProductAmount(Map<Pair<Long,Long>, Long> productAmount) {
    this.productAmount = productAmount;
  }

  public List<Long> getItemNrs() {
    return itemNrs;
  }

  public void setItemNrs(List<Long> itemNrs) {
    this.itemNrs = itemNrs;
  }
}
