package com.lvmama.distribution.model.lv;

public class Request {

	/** 报文头对象*/
	private RequestHead requestHead;
	/** 报文体对象*/
	private RequestBody requestBody;
	
	public Request(){}
	public Request(RequestHead requestHead,RequestBody requestBody){
		this.requestHead=requestHead;
		this.requestBody=requestBody;
	}
	
	/**
	 * 构造分销接口请求报文
	 * 
	 * @return
	 */
	public String buildXmlStr() {
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		xmlStr.append("<request>");
		xmlStr.append(requestHead.buildXmlStr());
		xmlStr.append(requestBody.buildXmlStr());
		xmlStr.append("</request>");
		return xmlStr.toString();
	}
	
	
	/**
	 * 获得签证信息
	 * @return
	 */
	public String getLocalSigned(){
		return requestBody.getLoaclSigned();
	}
	
	public RequestHead getRequestHead() {
		return requestHead;
	}
	public void setRequestHead(RequestHead requestHead) {
		this.requestHead = requestHead;
	}
	public RequestBody getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(RequestBody requestBody) {
		this.requestBody = requestBody;
	}

}
