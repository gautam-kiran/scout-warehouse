package ch.scout.warehouse.shared.settings.product;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

public class VariantLookupCall extends LookupCall<Long> {
  private static final long serialVersionUID = 1L;
  private Long productNr;

  public Long getProductNr() {
    return productNr;
  }

  public void setProductNr(Long productNr) {
    this.productNr = productNr;
  }

  @Override
  protected Class<? extends ILookupService<Long>> getConfiguredService() {
    return IVariantLookupService.class;
  }
}
