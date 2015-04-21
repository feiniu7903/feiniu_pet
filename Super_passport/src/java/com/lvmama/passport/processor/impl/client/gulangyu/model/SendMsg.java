package com.lvmama.passport.processor.impl.client.gulangyu.model;

public class SendMsg {
	
	/**16订单号*/
	private String uu16uorder;
	/** 重发次数*/
	private String uuResend;
	
	public String getUu16uorder() {
		return uu16uorder;
	}
	public void setUu16uorder(String uu16uorder) {
		this.uu16uorder = uu16uorder;
	}
	public String getUuResend() {
		return uuResend;
	}
	public void setUuResend(String uuResend) {
		this.uuResend = uuResend;
	}
	
}
