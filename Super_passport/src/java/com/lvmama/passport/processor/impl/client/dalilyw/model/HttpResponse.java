package com.lvmama.passport.processor.impl.client.dalilyw.model;

public class HttpResponse {
	
	private int code; //HTTP请求状态 200成功
	
	private String ResponseBody; //响应结果
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getResponseBody() {
		return ResponseBody;
	}

	public void setResponseBody(String responseBody) {
		ResponseBody = responseBody;
	}
}
