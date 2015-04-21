package com.lvmama.clutter.model.sumsang;

import java.io.Serializable;

public class ElementDataBarcode implements Serializable {
	private static final long serialVersionUID = -2191905901930663140L;
	private String value;
	private String type;
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
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	@Override
	public String toString() {
		return "ElementDataBarcode [value=" + value + ", type=" + type
				+ ", caption=" + caption + "]";
	}
	
}
