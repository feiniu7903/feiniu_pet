package com.lvmama.passport.processor.impl.client.newland.model;

public class Sms {
	private String text;

	public String toSmsxXML() {
		StringBuilder buf = new StringBuilder();
		buf.append("<Text>").append(this.text).append("</Text>");
		return buf.toString();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
