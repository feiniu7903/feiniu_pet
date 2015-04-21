package com.lvmama.back.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.api.Bandbox;
import org.zkoss.zul.api.Textbox;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.spring.SpringBeanProxy;

public class MacroProductSelect extends GenericForwardComposer {

	private ProdProductService productService = (ProdProductService)SpringBeanProxy.getBean("productService");
	Textbox productId;
	Bandbox mc_bandproduct;
	List<ProdProduct> productList;

	public void changeProduct(InputEvent event) {
		String name = event.getValue();
		Map<String, String> param = new HashMap<String, String>();
		if (name != null && !"".equals(name)) {
			param.put("productSearch", name);
		}
		param.put("_startRow", "0");
		param.put("_endRow", "10");
		productList = productService.selectProductByParms(param);
	}

	public void doAfterCompose(Component win) throws Exception {
		Components.wireVariables(win, this);
		Events.addEventListeners(win, this);
		Components.addForwards(win, this);

		if (productId != null && productId.getValue() != null && !productId.getValue().equals("")) {
			Long productIdValue = Long.parseLong(productId.getValue());
			ProdProduct prodProduct = productService.getProdProductById(productIdValue);
			mc_bandproduct.setValue(prodProduct.getProductName());
		}
	}

	public List<ProdProduct> getProductList() {
		return productList;
	}

	public void setProductList(List<ProdProduct> productList) {
		this.productList = productList;
	}

}
