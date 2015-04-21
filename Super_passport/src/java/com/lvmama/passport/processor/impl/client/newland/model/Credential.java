package com.lvmama.passport.processor.impl.client.newland.model;

import java.io.UnsupportedEncodingException;

import com.lvmama.passport.processor.impl.client.newland.Base64;

public class Credential {
	private String content;
	private String assistNumber;

	public String toCredentialXml() {
		StringBuilder buf = new StringBuilder();
		try {
			this.content = Base64.encode(this.content.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		buf.append("<Content>").append(this.content).append("</Content>")
		.append("<AssistNumber>").append(this.assistNumber).append("</AssistNumber>");
		return buf.toString();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
			this.content = content;
	}

	public String getAssistNumber() {
		return assistNumber;
	}

	public void setAssistNumber(String assistNumber) {
			this.assistNumber = assistNumber;
	}

}
