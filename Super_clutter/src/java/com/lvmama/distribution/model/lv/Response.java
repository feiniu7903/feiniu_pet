package com.lvmama.distribution.model.lv;

/**
 * 分销返回报文总结点
 * 
 * @author lipengcheng
 * 
 */
public class Response {
	
	private ResponseHead head;
	private ResponseBody body;

	public Response(){}
	
	public ResponseHead getHead() {
		return head;
	}

	public void setHead(ResponseHead head) {
		this.head = head;
	}

	public ResponseBody getBody() {
		return body;
	}

	public void setBody(ResponseBody body) {
		this.body = body;
	}

	public Response(ResponseHead head, ResponseBody body) {
		this.head = head;
		this.body = body;
	}

	/**
	 * 构造分销接口响应报文
	 * 
	 * @return
	 */
	public String buildXmlStr() {
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		xmlStr.append("<response>");
		xmlStr.append(head.buildXmlStr());
		xmlStr.append(body.buildXmlStr());
		xmlStr.append("</response>");
		return xmlStr.toString();
	}

}
