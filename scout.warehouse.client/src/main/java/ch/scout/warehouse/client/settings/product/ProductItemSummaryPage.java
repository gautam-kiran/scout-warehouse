package ch.scout.warehouse.client.settings.product;

import ch.scout.warehouse.shared.Icons;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.Order;

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


  @Order(1000)
  public class ReloadMenu extends AbstractMenu {

    @Override
    protected String getConfiguredIconId() {
      return Icons.Reload;
    }

    @Override
    protected byte getConfiguredHorizontalAlignment() {
      return HORIZONTAL_ALIGNMENT_RIGHT;
    }

    @Override
    protected void execAction() {
      getDetailForm().doReset();
    }
  }
}
