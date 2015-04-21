package com.lvmama.jinjiang.service;

import com.lvmama.jinjiang.model.request.NotifyCancelOrderRequest;
import com.lvmama.jinjiang.vo.order.OrderMessage;

public interface JinjiangOrderService {

	public String submitOrder(Long orderId);
	public String reSubmitOrder(Long orderId);
	public String cancelOrder(Long orderId);
	public String payOrder(Long orderId);
	
	public OrderMessage notifyCancelOrder(NotifyCancelOrderRequest cancelOrder);
}
