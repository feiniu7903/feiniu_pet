package com.lvmama.distribution.model.lv;

import java.util.List;

public class OrderList {
	private List<Order> orders;
	
	public String buildForPushOrder(){
		if (orders != null) {
			StringBuilder xmlStr = new StringBuilder();
			xmlStr.append("<orderList>");
			for (Order order : orders) {
				xmlStr.append(order.buildForPushOrder());
			}
			xmlStr.append("</orderList>");
			return xmlStr.toString();
		} else {
			return null;
		}
	}
	
	public String buildForGetOrder() {
		if (orders != null) {
			StringBuilder xmlStr = new StringBuilder();
			xmlStr.append("<orderList>");
			for (Order order : orders) {
				xmlStr.append(order.buildForGetOrder());
			}
			xmlStr.append("</orderList>");
			return xmlStr.toString();
		} else {
			return null;
		}
	}
	
	public String buildForGetOrderApprove(){
		if (orders != null) {
			StringBuilder xmlStr = new StringBuilder();
			xmlStr.append("<orderList>");
			for (Order order : orders) {
				xmlStr.append(order.buildForGetOrderApprove());
			}
			xmlStr.append("</orderList>");
			return xmlStr.toString();
		} else {
			return null;
		}
	}
	
	
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
}
