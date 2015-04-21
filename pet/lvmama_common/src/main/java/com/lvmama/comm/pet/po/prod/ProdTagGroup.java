package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;

public class ProdTagGroup implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2887249859962645886L;

	private Long tagGroupId;

	private String tagGroupName;
	
	/**
	 * 单选(一个产品只可以同时关联一个小组里面一个标签)
	 * 备注:强制关联业务，某一产品关联一个标签后，
	 * 		还必须让它关联一个其他标签
	 */
	private String isRadio = "";
	
	//复选(一个产品可以同时关联这个小组里面的多个标签)
	private String isCheck = "";

	private String type = "";
	
	private String cssId;//标签组默认css

	public Long getTagGroupId() {
		return tagGroupId;
	}

	public void setTagGroupId(Long tagGroupId) {
		this.tagGroupId = tagGroupId;
	}

	public String getTagGroupName() {
		return tagGroupName;
	}

	public void setTagGroupName(String tagGroupName) {
		this.tagGroupName = tagGroupName.trim();
	}

	public String getIsRadio() {
		return isRadio == null ? "" : isRadio;
	}

	public void setIsRadio(String isRadio) {
		this.isRadio = isRadio;
	}

	public String getIsCheck() {
		return isCheck == null ? "" : isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	/**
	 * 检测属于该组下的标签是否是单选
	 */
	public boolean isSingleOption() {
		return this.getIsRadio().equals("true");
	}

	/**
	 * 检测属于该组下的标签是否是复选
	 */
	public boolean isMultipleOption() {
		return this.getIsCheck().equals("true");
	}

	public String getType() {
		if (isMultipleOption()) {
			type = "multipleOption";
		} else if (isSingleOption()) {
			type = "singleOption";
		}
		return type;
	}

	public void setType(String type) {
		this.type = type;
		if (type.equals("singleOption")) {
			setIsRadio("true");
		} else if (type.equals("multipleOption")) {
			setIsCheck("true");
		}
	}

	public String getCssId() {
		return cssId;
	}

	public void setCssId(String cssId) {
		this.cssId = cssId;
	}

}