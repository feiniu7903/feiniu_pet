package com.lvmama.comm.pet.po.client;

import java.io.Serializable;
import java.util.List;

public class ClientProductOrder implements Serializable {

	private static final long serialVersionUID = 4687442125456518627L;

	private Long productId;
	private String productName;
	private List<String> dateList;

	private List<ClientProduct> clientProductList;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public List<String> getDateList() {
		return dateList;
	}
	public void setDateList(List<String> dateList) {
		this.dateList = dateList;
	}
	public List<ClientProduct> getClientProductList() {
		return clientProductList;
	}
	public void setClientProductList(List<ClientProduct> clientProductList) {
		this.clientProductList = clientProductList;
	}
}
