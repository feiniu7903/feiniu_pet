package com.lvmama.order.service;

import java.util.Date;
import java.util.List;

public interface OrderItemMetaSaleAmountServie {
	public void updateOrderItemMetaSaleAmount(Long orderId);
	public void updateOrderItemSaleAmount(Long orderId);
	public List<Long> getHistoryOrderId(Date startDate, Date endDate);
}
