package com.lvmama.passport.processor.impl.client.zhiyoubao.model;

import com.lvmama.passport.processor.impl.client.zhiyoubao.ZhiyoubaoUtil;
 
public class Header {
	private String application;//
	private String requestTime;//
	public Header() {}

	public Header(String application, String requestTime) {
		this.application = application;
		this.requestTime = requestTime;
	}


	/**
	 * 请求头
	 * @return
	 */
	public String toRequestHeaderXml(){
		StringBuilder buf=new StringBuilder();
    	buf.append("<header>");
    	buf.append("<application>SendCode</application>");// 固定值
    	buf.append(ZhiyoubaoUtil.buildElement("requestTime",requestTime));
		buf.append("</header>");
		return buf.toString();
	}
	
	
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	
}
