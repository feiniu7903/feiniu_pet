package com.lvmama.comm.vo;

import java.io.Serializable;

import com.lvmama.comm.utils.PriceUtil;

/**
 * 现金账户支付信息.
 * @author sunruyi
 */
public class CashAccountPayInfoVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -984704177002093139L;
	/**
	 * 订单ID.
	 */
	private String orderId;
	/**
	 * 订单金额.
	 */
	private String paytotal;
	/**
	 * 客服号码.
	 */
	private String csno;
	/**
	 * 会员帐户名称.
	 */
	private String accountName;
	/**
	 * 存款账户绑定手机号.
	 */
	private String moneyAccountMobile;
	/**
	 * 支付密码.
	 */
	private String paymentPassword;
	/**
	 * 存款账户Id.
	 */
	private Long userId;
	/**
	 * 是否有动态支付密码.
	 */
	private String hasDynamicCode;
	/**
	 * 构造器.
	 */
	public CashAccountPayInfoVO(){
		
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getPaytotal() {
		return paytotal;
	}

	public void setPaytotal(String paytotal) {
		this.paytotal = paytotal;
	}

	public Long getPaytotalFen(){
		return PriceUtil.convertToFen(Float.parseFloat(paytotal));
	}
	
	public String getCsno() {
		return csno;
	}
	public void setCsno(String csno) {
		this.csno = csno;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getMoneyAccountMobile() {
		return moneyAccountMobile;
	}
	public void setMoneyAccountMobile(String moneyAccountMobile) {
		this.moneyAccountMobile = moneyAccountMobile;
	}
	public String getPaymentPassword() {
		return paymentPassword;
	}
	public void setPaymentPassword(String paymentPassword) {
		this.paymentPassword = paymentPassword;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getHasDynamicCode() {
		return hasDynamicCode.toUpperCase();
	}
	public void setHasDynamicCode(String hasDynamicCode) {
		this.hasDynamicCode = hasDynamicCode;
	}
}
