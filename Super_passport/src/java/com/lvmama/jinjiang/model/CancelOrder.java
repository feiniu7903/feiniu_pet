package com.lvmama.jinjiang.model;

public class CancelOrder {

	private String thirdOrderNo;
	private String orderNo;
	private String orderStatus;
	private String payStatus;
	private Long lossAmount;
	private String refundRemark;
	public String getThirdOrderNo() {
		return thirdOrderNo;
	}
	public void setThirdOrderNo(String thirdOrderNo) {
		this.thirdOrderNo = thirdOrderNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public Long getLossAmount() {
		return lossAmount;
	}
	public void setLossAmount(Long lossAmount) {
		this.lossAmount = lossAmount;
	}
	public String getRefundRemark() {
		return refundRemark;
	}
	public void setRefundRemark(String refundRemark) {
		this.refundRemark = refundRemark;
	}
	
}
