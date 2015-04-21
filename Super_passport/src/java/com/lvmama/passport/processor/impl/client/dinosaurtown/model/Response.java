package com.lvmama.passport.processor.impl.client.dinosaurtown.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 常州环球恐龙城
 * 
 * @author chenlinjun
 * 
 */
public class Response {
	private static final Log log = LogFactory.getLog(Response.class);
	private List<Order> orders = new ArrayList<Order>();
	private Order order;

	/**
	 * 响应数据
	 * 
	 * @return
	 */
	public String toResponseXml() {
		StringBuilder buf = new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<Response>");
		for (Order order : orders) {
			buf.append(order.toOrderXml());
		}
		buf.append("</Response>");
		log.info("常州环球恐龙城响应数据" + buf.toString());
		return buf.toString();
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
