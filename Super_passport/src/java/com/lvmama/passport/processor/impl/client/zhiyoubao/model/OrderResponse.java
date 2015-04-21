package com.lvmama.passport.processor.impl.client.zhiyoubao.model;

import java.util.List;
 
public class OrderResponse {
	private List<SubOrder> subOrders;
	private Order order;
	public OrderResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderResponse(List<SubOrder> subOrders) {
		super();
		this.subOrders = subOrders;
	}

	public List<SubOrder> getSubOrders() {
		return subOrders;
	}

	public void setSubOrders(List<SubOrder> subOrders) {
		this.subOrders = subOrders;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
