package com.lvmama.finance.common.ibatis.vo;

/**
 * input提示框内容项
 * 
 * @author yanggan
 * 
 */
public class AutocompleteItem {

	/**
	 * 显示
	 */
	private String label;
	/**
	 * 实际值
	 */
	private String value;

	public AutocompleteItem() {
		super();
	}

	public AutocompleteItem(String value, String label) {
		super();
		this.label = label;
		this.value = value;
	}

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
