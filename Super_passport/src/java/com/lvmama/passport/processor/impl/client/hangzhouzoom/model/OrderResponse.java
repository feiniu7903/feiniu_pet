package com.lvmama.passport.processor.impl.client.hangzhouzoom.model;
/**
 * 杭野返回信息类<br/> 用于放置申码，废码返回信息
 * @author tangJing
 *
 */
public class OrderResponse {
	/**
	 *返回信息ID
	 *0为失败,1为成功
	 */
	private String orderStatus;
	/**
	 * 失败信息
	 * 只有失败才会有此值
	 */
	private String errorInfo;
	/**
	 *订单号
	 *只有成功才会返回订单号
	 */
	private String orderId;

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
