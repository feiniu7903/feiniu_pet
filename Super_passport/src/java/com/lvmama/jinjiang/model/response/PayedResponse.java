package com.lvmama.jinjiang.model.response;
/**
 * 订单已支付请求返回信息
 * @author chenkeke
 *
 */
public class PayedResponse extends AbstractResponse{
	private String orderNo;
	private String thirdOrderNo;
	private String orderStatus;
	private String payStatus;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getThirdOrderNo() {
		return thirdOrderNo;
	}
	public void setThirdOrderNo(String thirdOrderNo) {
		this.thirdOrderNo = thirdOrderNo;
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
}
