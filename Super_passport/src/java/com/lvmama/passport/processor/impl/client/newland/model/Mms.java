package com.lvmama.passport.processor.impl.client.newland.model;

public class Mms {
	private String text;
	private String subject;

	public String toMmsxXML() {
		StringBuilder buf = new StringBuilder();
		buf.append("<Subject>").append(this.subject).append("</Subject>");
		buf.append("<Text>").append(this.text).append("</Text>");
		return buf.toString();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
