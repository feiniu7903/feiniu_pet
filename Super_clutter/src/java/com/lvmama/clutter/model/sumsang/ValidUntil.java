package com.lvmama.clutter.model.sumsang;

import java.io.Serializable;

public class ValidUntil implements Serializable {
	private static final long serialVersionUID = 2144736053247776256L;
	private String value;
	private String format;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	@Override
	public String toString() {
		return "ValidUntil [value=" + value + ", format=" + format + "]";
	}
	
}
