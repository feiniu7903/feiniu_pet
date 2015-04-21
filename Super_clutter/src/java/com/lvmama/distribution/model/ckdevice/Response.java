package com.lvmama.distribution.model.ckdevice;

import java.util.List;

public class Response {
	private Order order;

	private List<Product> products;
	
	private Long totalNum;
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}
	
	
}
