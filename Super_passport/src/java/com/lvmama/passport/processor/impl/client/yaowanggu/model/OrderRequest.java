package com.lvmama.passport.processor.impl.client.yaowanggu.model;

public class OrderRequest {
	private String customerTruename;//客户姓名
	private String customerMobile;//客户电话
	private String total;//总金额
	private ProductInfo product;//产品信息
	public String getCustomerTruename() {
		return customerTruename;
	}
	public void setCustomerTruename(String customerTruename) {
		this.customerTruename = customerTruename;
	}
	public String getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public ProductInfo getProduct() {
		return product;
	}
	public void setProduct(ProductInfo product) {
		this.product = product;
	}

}
