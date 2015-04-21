package com.lvmama.clutter.model.sumsang;

import java.io.Serializable;

public class DateAlert implements Serializable {
	private static final long serialVersionUID = 5663503989037721559L;
	private String value;
	private String format;
	private Integer beforeinmin;
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
	public Integer getBeforeinmin() {
		return beforeinmin;
	}
	public void setBeforeinmin(Integer beforeinmin) {
		this.beforeinmin = beforeinmin;
	}
	@Override
	public String toString() {
		return "DateAlert [value=" + value + ", format=" + format
				+ ", beforeinmin=" + beforeinmin + "]";
	}
	
}
