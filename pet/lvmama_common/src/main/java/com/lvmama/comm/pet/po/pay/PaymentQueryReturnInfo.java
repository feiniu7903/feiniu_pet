package com.lvmama.comm.pet.po.pay;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付查询返回信息
 * 
 * @author zhangjie
 * 
 */
public class PaymentQueryReturnInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4935359408480031596L;
	
	/**
	 * 返回CODE.
	 */
	private String code;
	/**
	 * 返回信息.
	 */
	private String codeInfo;
	/**
	 * 支付回馈信息.
	 */
	private String callbackInfo;
	/**
	 * 支付网关交易流水号.
	 */
	private String gatewayTradeNo;
	/**
	 * 退款的流水号(中行与招行的分期与支付网关交易流水号不一样，其它的一样).
	 */
	private String refundSerial;
	/**
	 * 回馈时间.
	 */
	private Date callbackTime;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeInfo() {
		return codeInfo;
	}

	public void setCodeInfo(String codeInfo) {
		this.codeInfo = codeInfo;
	}

	public String getCallbackInfo() {
		return callbackInfo;
	}

	public void setCallbackInfo(String callbackInfo) {
		this.callbackInfo = callbackInfo;
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

	public Date getCallbackTime() {
		return callbackTime;
	}

	public void setCallbackTime(Date callbackTime) {
		this.callbackTime = callbackTime;
	}

}
