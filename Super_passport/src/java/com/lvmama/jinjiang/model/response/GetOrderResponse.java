package com.lvmama.jinjiang.model.response;

import com.lvmama.jinjiang.model.OrderDetail;
/**
 * 查询订单返回信息
 * @author chenkeke
 *
 */
public class GetOrderResponse extends AbstractResponse{
	private OrderDetail order;

	public OrderDetail getOrder() {
		return order;
	}

	public void setOrder(OrderDetail order) {
		this.order = order;
	}
}
