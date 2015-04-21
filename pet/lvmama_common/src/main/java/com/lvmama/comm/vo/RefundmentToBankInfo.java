package com.lvmama.comm.vo;

import java.io.Serializable;
import java.util.Date;

public class RefundmentToBankInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7077350287436891298L;
	
	/**
	 * 用户ID
	 */
	private Long userId;
	
	/**
	 * 对象ID
	 */
	private Long objectId;
	
	/**
	 * 对象类型
	 */
	private String objectType;
	/**
	 * 业务类型
	 */
	private String bizType;
	/**
	 * 退款金额.
	 */
	private Long refundAmount;
	
	/**
	 * 退款类型
	 */
	private String refundType;
	/**
	 * 退款网关.
	 */
	private String refundGateway;
	/**
	 * 支付网关
	 */
	private String paymentGateway;
	
	/*
	 * 
	 */
	private Long payRefundmentId;

	/**
	 * 我方支付流水号
	 */
	private String paymentTradeNo;
	/**
	 * 支付网关的交易流水号
	 */
	private String gatewayTradeNo;
	/**
	 * 退款流水号
	 */
	private String refundSerial;
	
	/**
	 * 退款表退款回调时的序列号
	 */
	private String payPaymentRefunfmentSerial;

	/**
	 * 预授权退款类型
	 */
	private String preRefundType;
	/**
	 * 实际支付金额.
	 */
	private Long payAmount;
	/**
	 * 支付时间.
	 */
	private Date callbackTime;
	/**
	 * IP.
	 */
	private String customerIp;
	/**
	 * 操作时间.
	 */
	private Date createTime;
	/**
	 * paymentId
	 */
	private Long paymentId;
	/**
	 * 操作者.
	 */
	private String operator;
	
	/**
	 * 预授权完成支付的时间.
	 */
	private Date succTime;

	/**
	 * 汇付天下使用，标识使用哪家银行
	 */
	private String gateId;
	
	private Long orderId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getRefundGateway() {
		return refundGateway;
	}

	public void setRefundGateway(String refundGateway) {
		this.refundGateway = refundGateway;
	}

	public Long getPayRefundmentId() {
		return payRefundmentId;
	}

	public void setPayRefundmentId(Long payRefundmentId) {
		this.payRefundmentId = payRefundmentId;
	}

	public String getPaymentTradeNo() {
		return paymentTradeNo;
	}

	public String getPreRefundType() {
		return preRefundType;
	}

	public void setPreRefundType(String preRefundType) {
		this.preRefundType = preRefundType;
	}

	public void setPaymentTradeNo(String paymentTradeNo) {
		this.paymentTradeNo = paymentTradeNo;
	}

	public String getGatewayTradeNo() {
		return gatewayTradeNo;
	}

	public void setGatewayTradeNo(String gatewayTradeNo) {
		this.gatewayTradeNo = gatewayTradeNo;
	}

	public String getRefundSerial() {
		return refundSerial;
	}

	public void setRefundSerial(String refundSerial) {
		this.refundSerial = refundSerial;
	}

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public Date getCallbackTime() {
		return callbackTime;
	}

	public void setCallbackTime(Date callbackTime) {
		this.callbackTime = callbackTime;
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

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getSuccTime() {
		return succTime;
	}

	public void setSuccTime(Date succTime) {
		this.succTime = succTime;
	}

	public String getGateId() {
		return gateId;
	}

	public void setGateId(String gateId) {
		this.gateId = gateId;
	}

	public String getPaymentGateway() {
		return paymentGateway;
	}

	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public String getPayPaymentRefunfmentSerial() {
		return payPaymentRefunfmentSerial;
	}

	public void setPayPaymentRefunfmentSerial(String payPaymentRefunfmentSerial) {
		this.payPaymentRefunfmentSerial = payPaymentRefunfmentSerial;
	}	
}
