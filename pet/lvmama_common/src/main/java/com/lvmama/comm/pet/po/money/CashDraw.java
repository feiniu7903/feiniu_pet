package com.lvmama.comm.pet.po.money;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

public class CashDraw implements Serializable {

	private static final long serialVersionUID = 6001215383552395149L;

	private Long cashDrawId;

	private Long moneyDrawId;

	private String serial;

	private String paymentGateway;

	private Long amount;

	private String status;

	private Date callbackTime;

	private String callbackInfo;

	private Date createTime;

	private String alipay2bankFile;
	
	private String operatorName;
	
	private String gatewayTradeNo;
	
	private Date transTime;
	
	/** 变量 cashAccountId . */
	private Long cashAccountId;
	
	public Long getCashDrawId() {
		return cashDrawId;
	}

	public void setCashDrawId(Long cashDrawId) {
		this.cashDrawId = cashDrawId;
	}

	public Long getMoneyDrawId() {
		return moneyDrawId;
	}

	public void setMoneyDrawId(Long moneyDrawId) {
		this.moneyDrawId = moneyDrawId;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getPaymentGateway() {
		return paymentGateway;
	}

	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public boolean isNewlyCreated() {
		return Constant.PAYMENT_SERIAL_STATUS.CREATE.name().equalsIgnoreCase(status);
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCallbackTime() {
		return callbackTime;
	}

	public void setCallbackTime(Date callbackTime) {
		this.callbackTime = callbackTime;
	}

	public String getCallbackInfo() {
		return callbackInfo;
	}

	public void setCallbackInfo(String callbackInfo) {
		this.callbackInfo = callbackInfo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAlipay2bankFile() {
		return alipay2bankFile;
	}

	public void setAlipay2bankFile(String alipay2bankFile) {
		this.alipay2bankFile = alipay2bankFile;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getGatewayTradeNo() {
		return gatewayTradeNo;
	}

	public void setGatewayTradeNo(String gatewayTradeNo) {
		this.gatewayTradeNo = gatewayTradeNo;
	}

	public Date getTransTime() {
		return transTime;
	}

	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}

	public Long getCashAccountId() {
		return cashAccountId;
	}

	public void setCashAccountId(Long cashAccountId) {
		this.cashAccountId = cashAccountId;
	}
}
