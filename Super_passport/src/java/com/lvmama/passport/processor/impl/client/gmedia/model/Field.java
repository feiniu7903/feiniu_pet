package com.lvmama.passport.processor.impl.client.gmedia.model;

public class Field {
	private String id;
	private String name;
	private String value;
	public String toResponseFieldXml() {
		StringBuilder buf = new StringBuilder();
		buf.append("<Field>")
			.append("<Id>")
			.append(this.id)
			.append("</Id>")
			.append("<Name>")
			.append(this.name)
			.append("</Name>")
			.append("<Value>")
			.append(this.value)
			.append("</Value>")
		.append("</Field>");
		return buf.toString();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
