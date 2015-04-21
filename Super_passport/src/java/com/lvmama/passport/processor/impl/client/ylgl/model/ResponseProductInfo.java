package com.lvmama.passport.processor.impl.client.ylgl.model;

import java.util.List;

public class ResponseProductInfo {

	private String responseType;// 响应类型
	private List<Product> products;// 产品集

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
