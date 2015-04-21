package com.lvmama.comm.bee.vo.ord;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderChannel;

public class OrderChannelInfo extends OrdOrderChannel {
	private static final long serialVersionUID = -6523816286488933536L;
	private OrdOrder ordOrder;
	
	public OrdOrder getOrdOrder() {
		return ordOrder;
	}
	public void setOrdOrder(OrdOrder ordOrder) {
		this.ordOrder = ordOrder;
	}
		
}
