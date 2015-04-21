package com.lvmama.comm.bee.service;

import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdOrderRoute;

public interface GroupAdviceNoteService {
	OrdOrderItemProd getOrderItemProdByOrderId(Long orderId);
	OrdOrderRoute getOrderRouteByOrderId(Long orderId);
	void updateOrderGroupWordStatus(Long orderId, String status);
}
