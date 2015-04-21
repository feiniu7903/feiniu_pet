package com.lvmama.comm.abroad.vo.response;

public class DownOrderRes extends ErrorRes {
	private static final long serialVersionUID = 1L;
	private String orderId;//订单ID
	private String orderStatus;//订单状态
	private String userId;//用户id
	private boolean isSuccess;// 下单结果

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}
