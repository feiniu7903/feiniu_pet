package com.lvmama.comm.abroad.vo.request;

import java.io.Serializable;
public class CancelReservationReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4874665097773519510L;
	/**订单iD*/
	private String orderId;
	/**通知邮件*/
	private String ClientEmail;
	/**备注*/
	private String remark;
	/**废单人*/
	private String userId;
	/**
	 * 订单号，必填
	 * @param iDreservation
	 */
	
	public String getClientEmail() {
		return ClientEmail;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * 通知邮件
	 * @param clientEmail
	 */
	public void setClientEmail(String clientEmail) {
		ClientEmail = clientEmail;
	}
	public String getRemark() {
		return remark;
	}
	/**
	 * 备注
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
