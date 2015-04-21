/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.pet.po.money;

import java.io.Serializable;

/**
 * CashPay 的modle 用于封装与应用程序的业务逻辑相关的数据.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class CashPay implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1197885978486978982L;
	//columns START
	/** 变量 cashPayId . */
	private java.lang.Long cashPayId;
	/** 变量 cashAccountId . */
	private java.lang.Long cashAccountId;
	/** 变量 amount . */
	private java.lang.Long amount;
	/** 变量 serial . */
	private java.lang.String serial;
	/** 变量 status . */
	private java.lang.String status;
	/** 变量 outTradeNo . */
	private java.lang.String outTradeNo;
	/** 变量 createTime . */
	private java.util.Date createTime;
	/** 变量 orderId . */
	private java.lang.Long orderId;
	/**付款方式，用于区分奖金和现金**/
	private String payFrom;
	/**
	 * 区分新老奖金
	 * @see com.lvmama.comm.pet.po.money.CashPay.BonusFrom
	 **/
	private String bonusFrom;
	
	//columns END
	/**
	* CashPay 的构造函数
	*/
	public CashPay() {
	}
	/**
	* CashPay 的构造函数
	*/
	public CashPay(
		java.lang.Long cashPayId
	) {
		this.cashPayId = cashPayId;
	}
	public java.lang.Long getCashPayId() {
		return cashPayId;
	}
	public void setCashPayId(java.lang.Long cashPayId) {
		this.cashPayId = cashPayId;
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
	public java.lang.String getStatus() {
		return status;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	public java.lang.String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(java.lang.String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.lang.Long getOrderId() {
		return orderId;
	}
	public void setOrderId(java.lang.Long orderId) {
		this.orderId = orderId;
	}
	
	public String getBonusFrom() {
		return bonusFrom;
	}
	public void setBonusFrom(String bonusFrom) {
		this.bonusFrom = bonusFrom;
	}
	/**
	 * 获取付款方式
	 * @return
	 */
	public String getPayFrom() {
		return payFrom;
	}
	/**
	 * 设置付款方式 
	 * @param payFrom @see com.lvmama.comm.pet.po.money.CashPay.PayFrom
	 */
	public void setPayFrom(PayFrom payFrom) {
		this.payFrom = payFrom.name();
	}


	/**
	 * 支付方式
	 * @author taiqichao
	 *
	 */
	public enum PayFrom{
		/**
		 * 现金
		 */
		MONEY,
		/**
		 * 奖金
		 */
		BONUS
	}
	
	/**
	 * 区分新老奖金账户
	 * @author taiqichao
	 *
	 */
	public enum BonusFrom{
		OLD,
		NEW
	}
}


