package com.lvmama.hotel.model;

public class OrderStatus {
	/** 状态码 */
	private String statusCode;
	/** 状态描述 */
	private String statusName;
	
	public OrderStatus(String statusCode, String statusName) {
		this.statusCode = statusCode;
		this.statusName = statusName;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	@Override
	public String toString() {
		return "OrderStatus [statusCode=" + statusCode + ", statusName="
				+ statusName + "]";
	}
}
