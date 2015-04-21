package com.lvmama.comm.pet.po.pay;

import java.io.Serializable;
import java.util.Date;

public class PaymentToBankInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8371815594911033306L;
	/**
	 * 发起的商户订单号.
	 */
	private String paySerial;
	/**
	 * 订单号.
	 */
	private Long objectId;
	/**
	 * 支付金额.
	 */
	private Long payAmount;
	/**
	 * 客服号.
	 */
	private String csno;
	/**
	 * 用户手机.
	 */
	private String mobilenumber;
	/**
	 * 哪个业务类型(自由行/代售).
	 */
	private String bizType;
	/**
	 * 对象类型(订单).
	 */
	private String objectType;
	/**
	 * 支付类型(正常支付/预授权).
	 */
	private String paymentType;
	
	/**
	 * 操作网关.
	 */
	private String paymentGateway;
	
	/**
	 * 网关交易号
	 */
	private String gatewayTradeNo;

	/**
	 * 商户支付的订单号(对应pay_payment的paymentTradeNo).
	 */
	private String paymentTradeNo;
	/**
	 * 发起的类型(只限预授权使用)
	 */
	private String preRefundType;
	/**
	 * IP.
	 */
	private String customerIp;
	/**
	 * 操作时间.
	 */
	private Date createTime;
	
	public String getPaySerial() {
		return paySerial;
	}
	public void setPaySerial(String paySerial) {
		this.paySerial = paySerial;
	}
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public Long getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}
	public String getCsno() {
		return csno;
	}
	public void setCsno(String csno) {
		this.csno = csno;
	}
	public String getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentGateway() {
		return paymentGateway;
	}
	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}
	public String getPaymentTradeNo() {
		return paymentTradeNo;
	}
	public void setPaymentTradeNo(String paymentTradeNo) {
		this.paymentTradeNo = paymentTradeNo;
	}
	public String getPreRefundType() {
		return preRefundType;
	}
	public void setPreRefundType(String preRefundType) {
		this.preRefundType = preRefundType;
	}
	public String getCustomerIp() {
		return customerIp;
	}
	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getGatewayTradeNo() {
		return gatewayTradeNo;
	}
	public void setGatewayTradeNo(String gatewayTradeNo) {
		this.gatewayTradeNo = gatewayTradeNo;
	}
}
