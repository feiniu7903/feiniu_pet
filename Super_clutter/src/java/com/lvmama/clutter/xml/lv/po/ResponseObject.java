package com.lvmama.clutter.xml.lv.po;

/**
 * 详细xml消息体
 * @author dengcheng
 *
 */
public class ResponseObject {
	
	
	
	/**
	 * 头信息
	 */
	private ResponseHead head;
	/**
	 * body信息
	 */
	private ResponseBody body;
	
	
	
	public ResponseObject(String version,String page,boolean isLastPage){
		head = new ResponseHead();
		head.setLastPage(isLastPage);
		head.setVersion(version);
		head.setPage(page);
	}
	
	
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
}
