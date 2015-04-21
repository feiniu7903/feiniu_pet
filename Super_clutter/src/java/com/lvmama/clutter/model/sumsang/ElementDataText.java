package com.lvmama.clutter.model.sumsang;

import java.io.Serializable;

public class ElementDataText implements Serializable {
	private static final long serialVersionUID = -1587641374146929101L;
	private String value;
	private String color;
	private String align;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	@Override
	public String toString() {
		return "ElementDataText [value=" + value + ", color=" + color
				+ ", align=" + align + "]";
	}
	
}
