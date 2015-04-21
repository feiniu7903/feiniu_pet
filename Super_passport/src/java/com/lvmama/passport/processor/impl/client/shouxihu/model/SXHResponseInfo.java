package com.lvmama.passport.processor.impl.client.shouxihu.model;

/**
 * 瘦西湖对接--响应报文解析后的总对象
 * @author lipengcheng
 *
 */
public class SXHResponseInfo {
	private SXHResponseHead head;
	private SXHResponseBody body;
	public SXHResponseHead getHead() {
		return head;
	}
	public void setHead(SXHResponseHead head) {
		this.head = head;
	}
	public SXHResponseBody getBody() {
		return body;
	}
	public void setBody(SXHResponseBody body) {
		this.body = body;
	}
}
