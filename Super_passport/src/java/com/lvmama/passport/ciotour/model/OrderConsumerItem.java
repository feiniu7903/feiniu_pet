package com.lvmama.passport.ciotour.model;

public class OrderConsumerItem {
	private int orderConsumerSysNo;// 系统编号
	private String productID;// 商品ID
	private String unitDate;// 预订商品的消费日期
	public int getOrderConsumerSysNo() {
		return orderConsumerSysNo;
	}
	public void setOrderConsumerSysNo(int orderConsumerSysNo) {
		this.orderConsumerSysNo = orderConsumerSysNo;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getUnitDate() {
		return unitDate;
	}
	public void setUnitDate(String unitDate) {
		this.unitDate = unitDate;
	}

	
}
