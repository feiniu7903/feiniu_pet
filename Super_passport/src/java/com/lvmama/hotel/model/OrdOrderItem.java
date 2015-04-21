package com.lvmama.hotel.model;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;

public class OrdOrderItem {
	private OrdOrderItemMeta meta;
	private OrdOrderItemProd prod;

	public OrdOrderItem(OrdOrderItemMeta meta, OrdOrderItemProd prod) {
		this.meta = meta;
		this.prod = prod;
	}

	public OrdOrderItemMeta getMeta() {
		return meta;
	}

	public void setMeta(OrdOrderItemMeta meta) {
		this.meta = meta;
	}

	public OrdOrderItemProd getProd() {
		return prod;
	}

	public void setProd(OrdOrderItemProd prod) {
		this.prod = prod;
	}
}
