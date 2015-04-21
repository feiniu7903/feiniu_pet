package com.lvmama.passport.processor.impl.client.ylgl.model;

public class ResponseReplayCode {
	
	private String responseType;// 响应类型
	private String sysSeq;// 平台流水号
	private String reqSeq;// 请求订单号
	private String transTime;// 交易时间
	private Result result;

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getSysSeq() {
		return sysSeq;
	}

	public void setSysSeq(String sysSeq) {
		this.sysSeq = sysSeq;
	}

	public String getReqSeq() {
		return reqSeq;
	}

	public void setReqSeq(String reqSeq) {
		this.reqSeq = reqSeq;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}


}
