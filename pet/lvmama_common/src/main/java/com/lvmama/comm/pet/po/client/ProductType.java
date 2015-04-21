package com.lvmama.comm.pet.po.client;

import java.io.Serializable;
import java.util.List;

public class ProductType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5071769266310544383L;
	private String productTypeName;
	private List<ClientProduct> clientProductList;
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	public List<ClientProduct> getClientProductList() {
		return clientProductList;
	}
	public void setClientProductList(List<ClientProduct> clientProductList) {
		this.clientProductList = clientProductList;
	}
}
