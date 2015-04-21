package com.lvmama.shholiday.service;

import com.lvmama.shholiday.vo.order.OrderDetailInfo;

public interface SHHolidayOrderService {

	public String submitOrder(Long orderId);
	public String reSubmitOrder(Long orderId);
	public OrderDetailInfo findOrder(Long orderId);
	public String updateOrder(Long orderId);
	public String cancelOrder(Long orderId);
	public String payOrder(Long orderId);
}
