package com.lvmama.hotel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;

public class BaseHotelProductService {
	protected MetaProductBranchService metaProductBranchService;
	protected MetaProductService metaProductService;
	protected ProdProductBranchService prodProductBranchService;
	protected ProdProductService prodProductService;

	protected void onOffline(MetaProductBranch metaProductBranch, boolean isOnline) {
		String valid = isOnline ? "Y" : "N";
		Long metaProductId = metaProductBranch.getMetaProductId();
		Long metaBranchId = metaProductBranch.getMetaBranchId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("metaProductId", metaProductId);
		params.put("valid", valid);
		metaProductService.changeMetaProductValid(params, "System");
		List<ProdProductBranch> prodProductBranchs = prodProductBranchService.getProductBranchByMetaProdBranchId(metaBranchId);
		for (ProdProductBranch prodProductBranch : prodProductBranchs) {
			prodProductBranch.setOnline(String.valueOf(isOnline));
			if (Boolean.valueOf(prodProductBranch.getDefaultBranch())) {
				params.put("productId", prodProductBranch.getProductId());
				params.put("onLine", String.valueOf(isOnline));
				prodProductService.markIsSellable(params, "System");
			}
			if (!Boolean.valueOf(prodProductBranch.getDefaultBranch())) {
				prodProductBranchService.OnOfflineProductBranch(prodProductBranch, "System");
			}
		}
	}

	public void setMetaProductBranchService(MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public void setProdProductBranchService(ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
}
