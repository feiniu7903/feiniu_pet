package com.lvmama.comm.bee.service.ord;

import java.util.Date;
import java.util.List;

public interface OrderItemMetaSaleAmountServie {
	public void updateOrderItemMetaSaleAmount(Long orderId);
	public List<Long> getHistoryOrderId(Date startDate, Date endDate);
}
