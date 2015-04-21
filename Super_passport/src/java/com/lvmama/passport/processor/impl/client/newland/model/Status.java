package com.lvmama.passport.processor.impl.client.newland.model;

public class Status {
	private String status_code;
	private String status_text;

	public String toStatusXml() {
		StringBuilder buf = new StringBuilder();
		buf.append("<StatusCode>").append(this.status_code).append("</StatusCode>");
		buf.append("<StatusText>").append(this.status_text).append("</StatusText>");
		return buf.toString();
	}

	public String getStatus_code() {
		return status_code;
	}

	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}

	public String getStatus_text() {
		return status_text;
	}

	public void setStatus_text(String status_text) {
		this.status_text = status_text;
	}

}
