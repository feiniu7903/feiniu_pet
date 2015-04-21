package com.lvmama.comm.bee.po.meta;

import com.lvmama.comm.vo.Constant;

public class MetaProductRoute extends MetaProduct {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4067468685497341263L;
	private Long metaRouteId;
 
	public Long getMetaRouteId() {
		return metaRouteId;
	}

	public void setMetaRouteId(Long metaRouteId) {
		this.metaRouteId = metaRouteId;
	}
 
	public String getProductType() {
		return Constant.PRODUCT_TYPE.ROUTE.name();
	}
}