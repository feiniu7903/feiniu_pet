package com.lvmama.clutter.model.sumsang;

import java.io.Serializable;

public class ElementDataImage implements Serializable {
	private static final long serialVersionUID = -1906383825343017774L;
	private String value;
	private String type;
	private String align;
	private String bgcolor;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getBgcolor() {
		return bgcolor;
	}
	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}
	@Override
	public String toString() {
		return "ElementDataImage [value=" + value + ", type=" + type
				+ ", align=" + align + ", bgcolor=" + bgcolor + "]";
	}

}
