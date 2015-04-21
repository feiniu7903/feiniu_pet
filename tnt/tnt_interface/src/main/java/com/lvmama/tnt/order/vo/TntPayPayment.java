package com.lvmama.tnt.order.vo;

import java.io.Serializable;

public class TntPayPayment implements Serializable {

	private static final long serialVersionUID = -1327340076428874632L;

	private Long orderId;

	private String paymentGateway;

	private String operator;

	/**
	 * 流水号.
	 */
	private String serial;

	/**
	 * 支付网关交易流水号.
	 */
	private String gatewayTradeNo;

	/**
	 * 发出去的交易流水号.
	 */
	private String paymentTradeNo;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getPaymentGateway() {
		return paymentGateway;
	}

	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getGatewayTradeNo() {
		return gatewayTradeNo;
	}

	public void setGatewayTradeNo(String gatewayTradeNo) {
		this.gatewayTradeNo = gatewayTradeNo;
	}

	public String getPaymentTradeNo() {
		return paymentTradeNo;
	}

	public void setPaymentTradeNo(String paymentTradeNo) {
		this.paymentTradeNo = paymentTradeNo;
	}

}
