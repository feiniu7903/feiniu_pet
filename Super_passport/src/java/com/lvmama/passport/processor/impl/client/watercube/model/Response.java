package com.lvmama.passport.processor.impl.client.watercube.model;

public class Response {
	/** 出票响应*/
	private String responseType;
	/** 请求流水号*/
	private String reqSeq;
	/** 结果信息*/
	private Result result;
	/** 订单信息*/
	private Order order;
	
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public String getReqSeq() {
		return reqSeq;
	}
	public void setReqSeq(String reqSeq) {
		this.reqSeq = reqSeq;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
}
