package com.lvmama.passport.processor.impl.client.gmedia.model;

public class CodeImg {
	private String type;
	private String content;

	/**
	 * Code 类型XML
	 * 
	 * @return
	 */
	public String toResponseCodeImgXml() {
		StringBuilder buf = new StringBuilder();
		buf.append("<Type>").append(this.type).append("</Type>").append("<Content>").append(this.content).append(
				"</Content>");
		return buf.toString();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
