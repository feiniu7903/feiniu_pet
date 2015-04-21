package com.lvmama.passport.processor.impl.client.shouxihu.model;

/**
 * 扬州瘦西湖--响应报文消息头
 * @author lipengcheng
 *
 */
public class SXHResponseHead {
	
	private String rspCode;//请求响应码
	private String rspDesc;////请求响应结果
	private String rspTime;//请求响应时间
	
	public String getRspCode() {
		return rspCode;
	}

	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

	public String getRspDesc() {
		return rspDesc;
	}

	public void setRspDesc(String rspDesc) {
		this.rspDesc = rspDesc;
	}

	public String getRspTime() {
		return rspTime;
	}

	public void setRspTime(String rspTime) {
		this.rspTime = rspTime;
	}
	
}
