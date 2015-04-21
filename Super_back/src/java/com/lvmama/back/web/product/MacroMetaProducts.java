package com.lvmama.back.web.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.service.meta.MetaProductService;

public class MacroMetaProducts extends BaseAction {

	private List<MetaProduct> metaProductsList;
	private MetaProductService metaProductService;

	public void changeMetaProduct(String name) {
		System.out.println("productName:" + name);
		Map<String,String> param = new HashMap<String,String>();
		if (name!=null && !"".equals(name)) {
			param.put("productName", name);
		}
		param.put("valid", "Y");
		metaProductsList=metaProductService.findMetaProduct(param);
	}

	public List<MetaProduct> getMetaProductsList() {
		return metaProductsList;
	}
	
}
