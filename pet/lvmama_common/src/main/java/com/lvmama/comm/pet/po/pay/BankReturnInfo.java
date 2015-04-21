package com.lvmama.comm.pet.po.pay;

import java.io.Serializable;

/**
 * 退款返回信息
 * 
 * @author liwenzhan
 * 
 */
public class BankReturnInfo implements Serializable{
	/**
	 * 支付码(百付电话预授权要用).
	 */
	private static final long serialVersionUID = 4831618940100358072L;
	/**
	 * 支付码(百付电话要用).
	 */
	private String payCode;
	/**
	 * 返回CODE.
	 */
	private String code;
	/**
	 * 返回信息.
	 */
	private String codeInfo;
	/**
    * 成功标志.
    */
	private String successFlag;
	/**
	 * 存放到pay_payment_refundment中serial字段，代表网关回调我方通知结果时的标识
	 */
	private String serial;
	
	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

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

	public String getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}	
}
