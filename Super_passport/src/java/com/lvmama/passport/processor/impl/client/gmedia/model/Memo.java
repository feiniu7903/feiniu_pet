package com.lvmama.passport.processor.impl.client.gmedia.model;

import java.util.List;

public class Memo {
	private List<Field> fields;
	public String toResponseMemoXml() {
		StringBuilder buf = new StringBuilder();
		for (Field field : this.fields) {
			buf.append(field.toResponseFieldXml());
		}
		return buf.toString();
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

}
