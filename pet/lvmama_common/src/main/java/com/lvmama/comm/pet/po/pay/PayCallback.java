package com.lvmama.comm.pet.po.pay;

import java.io.Serializable;

public class PayCallback implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7992319999637787547L;

	private String url;
	
	private String content;
	
	private String gateway;
	
	private String processed;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}
	
}
