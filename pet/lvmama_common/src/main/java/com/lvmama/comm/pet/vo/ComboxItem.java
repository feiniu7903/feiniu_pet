package com.lvmama.comm.pet.vo;

import java.io.Serializable;

/**
 * 下拉框内容项
 * @author zhaojindong
 */
public class ComboxItem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5198848592667967646L;
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
