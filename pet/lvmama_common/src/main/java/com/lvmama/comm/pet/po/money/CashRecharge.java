/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.pet.po.money;

import java.io.Serializable;

/**
 * CashRecharge 的modle 用于封装与应用程序的业务逻辑相关的数据.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class CashRecharge implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9004777079678496400L;
	//columns START
	/** 变量 cashRechargeId . */
	private java.lang.Long cashRechargeId;
	/** 变量 cashAccountId . */
	private java.lang.Long cashAccountId;
	/** 变量 serial . */
	private java.lang.String serial;
	/** 变量 paymentGateway . */
	private java.lang.String paymentGateway;
	/** 变量 amount . */
	private java.lang.Long amount;
	/** 变量 status . */
	private java.lang.String status;
	/** 变量 callbackTime . */
	private java.util.Date callbackTime;
	/** 变量 callbackInfo . */
	private java.lang.String callbackInfo;
	/** 变量 createTime . */
	private java.util.Date createTime;
	//columns END
	/**
	* CashRecharge 的构造函数
	*/
	public CashRecharge() {
	}
	/**
	* CashRecharge 的构造函数
	*/
	public CashRecharge(
		java.lang.Long cashRechargeId
	) {
		this.cashRechargeId = cashRechargeId;
	}
	public java.lang.Long getCashRechargeId() {
		return cashRechargeId;
	}
	public void setCashRechargeId(java.lang.Long cashRechargeId) {
		this.cashRechargeId = cashRechargeId;
	}
	public java.lang.Long getCashAccountId() {
		return cashAccountId;
	}
	public void setCashAccountId(java.lang.Long cashAccountId) {
		this.cashAccountId = cashAccountId;
	}
	public java.lang.String getSerial() {
		return serial;
	}
	public void setSerial(java.lang.String serial) {
		this.serial = serial;
	}
	public java.lang.String getPaymentGateway() {
		return paymentGateway;
	}
	public void setPaymentGateway(java.lang.String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}
	public java.lang.Long getAmount() {
		return amount;
	}
	public void setAmount(java.lang.Long amount) {
		this.amount = amount;
	}
	public java.lang.String getStatus() {
		return status;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	public java.util.Date getCallbackTime() {
		return callbackTime;
	}
	public void setCallbackTime(java.util.Date callbackTime) {
		this.callbackTime = callbackTime;
	}
	public java.lang.String getCallbackInfo() {
		return callbackInfo;
	}
	public void setCallbackInfo(java.lang.String callbackInfo) {
		this.callbackInfo = callbackInfo;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

}


