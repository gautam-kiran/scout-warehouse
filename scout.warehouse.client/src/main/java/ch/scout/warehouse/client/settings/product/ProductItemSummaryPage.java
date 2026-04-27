package ch.scout.warehouse.client.settings.product;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.form.IForm;

public class ProductItemSummaryPage extends AbstractPageWithNodes {
  Long productNr;

  @Override
  protected Class<? extends IForm> getConfiguredDetailForm() {
    return ProductItemSummaryForm.class;
  }

  public Long getProductNr() {
    return productNr;
  }

  public void setProductNr(Long productNr) {
    this.productNr = productNr;
  }

  @Override
  protected void execInitDetailForm() {
    ProductItemSummaryForm form = (ProductItemSummaryForm) getDetailForm();
    form.setProductNr(productNr);
    form.setHandler(new ProductItemSummaryForm.FormHandler());
  }
}
