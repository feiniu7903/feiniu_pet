package com.lvmama.passport.overseaschinatown.model;

public class ResendVoucherBean {
	private String pageIndex;
	private String pageSize;
	private String orderId;
	private String hvOrderId;
	private String voucherId;
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
	public String getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

}
