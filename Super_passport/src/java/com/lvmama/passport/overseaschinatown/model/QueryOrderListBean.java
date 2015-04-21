package com.lvmama.passport.overseaschinatown.model;

public class QueryOrderListBean {
	private String pageIndex;
	private String pageSize;
	private String mobile;
	private String dealTime;
	private String orderId;
	private String hvOrderId;
	private String hvOrderStatus;
	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDealTime() {
		return dealTime;
	}
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getHvOrderId() {
		return hvOrderId;
	}
	public void setHvOrderId(String hvOrderId) {
		this.hvOrderId = hvOrderId;
	}
	public String getHvOrderStatus() {
		return hvOrderStatus;
	}
	public void setHvOrderStatus(String hvOrderStatus) {
		this.hvOrderStatus = hvOrderStatus;
	}

}