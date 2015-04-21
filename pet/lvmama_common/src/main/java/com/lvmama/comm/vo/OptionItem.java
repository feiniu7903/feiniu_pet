package com.lvmama.comm.vo;

import java.io.Serializable;

public class OptionItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4021429951568204448L;
	private String label;
	private String value;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
