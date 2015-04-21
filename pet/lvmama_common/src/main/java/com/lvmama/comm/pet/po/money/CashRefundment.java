/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.pet.po.money;

import java.io.Serializable;

/**
 * CashRefundment 的modle 用于封装与应用程序的业务逻辑相关的数据.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class CashRefundment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6590142348125979588L;
	//columns START
	/** 变量 fincRefundmentId . */
	private Long fincRefundmentId;
	/** 变量 cashAccountId . */
	private java.lang.Long cashAccountId;
	/** 变量 amount . */
	private java.lang.Long amount;
	/** 变量 serial . */
	private java.lang.String serial;
	/** 变量 createTime . */
	private java.util.Date createTime;
	/** 变量 orderRefundmentId . */
	private java.lang.Long orderRefundmentId;
	/** 变量 refundmentType . */
	private java.lang.String refundmentType;
	/** 变量 orderId . */
	private java.lang.Long orderId;
	/**
	 * 是否奖金退款(Y/N)
	 */
	private String bounsRefundment;
	
	//columns END
	/**
	* CashRefundment 的构造函数
	*/
	public CashRefundment() {
	}
	/**
	* CashRefundment 的构造函数
	*/
	public CashRefundment(
		Long fincRefundmentId
	) {
		this.fincRefundmentId = fincRefundmentId;
	}
	public Long getFincRefundmentId() {
		return fincRefundmentId;
	}
	public void setFincRefundmentId(Long fincRefundmentId) {
		this.fincRefundmentId = fincRefundmentId;
	}
	public java.lang.Long getCashAccountId() {
		return cashAccountId;
	}
	public void setCashAccountId(java.lang.Long cashAccountId) {
		this.cashAccountId = cashAccountId;
	}
	public java.lang.Long getAmount() {
		return amount;
	}
	public void setAmount(java.lang.Long amount) {
		this.amount = amount;
	}
	public java.lang.String getSerial() {
		return serial;
	}
	public void setSerial(java.lang.String serial) {
		this.serial = serial;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.lang.Long getOrderRefundmentId() {
		return orderRefundmentId;
	}
	public void setOrderRefundmentId(java.lang.Long orderRefundmentId) {
		this.orderRefundmentId = orderRefundmentId;
	}
	public java.lang.String getRefundmentType() {
		return refundmentType;
	}
	public void setRefundmentType(java.lang.String refundmentType) {
		this.refundmentType = refundmentType;
	}
	public java.lang.Long getOrderId() {
		return orderId;
	}
	public void setOrderId(java.lang.Long orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取是否奖金退款
	 * @return Y/N
	 */
	public String getBounsRefundment() {
		return bounsRefundment;
	}
	/**
	 * 设置是否奖金退款
	 * @param bounsRefundment Y/N
	 */
	public void setBounsRefundment(String bounsRefundment) {
		this.bounsRefundment = bounsRefundment;
	}

	
}


