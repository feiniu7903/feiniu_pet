package com.lvmama.pet.payment.phonepay;
public class RequestObject {
	private RequestHeadObject headobj;/*请求头信息*/
	private String body;/*加密请求消息体*/
	public RequestHeadObject getHeadobj() {
		return headobj;
	}
	public void setHeadobj(RequestHeadObject headobj) {
		this.headobj = headobj;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
