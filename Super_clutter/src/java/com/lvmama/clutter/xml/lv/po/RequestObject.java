package com.lvmama.clutter.xml.lv.po;

/**
 * 新版请求头信息
 * @author dengcheng
 *
 */
public class RequestObject {
	/**
	 * 头信息
	 */
	private RequestHead head;
	/**
	 * body信息
	 */
	private RequestBody body;
	
	public RequestHead getHead() {
		return head;
	}
	
	public void setHead(RequestHead head) {
		this.head = head;
	}
	public RequestBody getBody() {
		return body;
	}
	public void setBody(RequestBody body) {
		this.body = body;
	}

}
