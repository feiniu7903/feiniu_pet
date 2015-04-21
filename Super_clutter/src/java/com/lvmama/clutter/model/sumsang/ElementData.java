package com.lvmama.clutter.model.sumsang;

import java.io.Serializable;

public class ElementData implements Serializable {
	private static final long serialVersionUID = -5688897756571638059L;
	private String value;
	private String type;
	private String align;
	private String bgcolor;
	private String color;
	private String caption;
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
	public String getBgcolor() {
		return bgcolor;
	}
	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	@Override
	public String toString() {
		return "ElementData [value=" + value + ", type=" + type + ", bgcolor="
				+ bgcolor + ", align=" + align + ", color=" + color
				+ ", caption=" + caption + "]";
	}
	
}
