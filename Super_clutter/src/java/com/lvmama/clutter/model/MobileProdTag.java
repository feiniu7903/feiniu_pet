package com.lvmama.clutter.model;

/**
 * 前台展示标签 -- ProdTag 
 * @author qinzubo
 *
 */
public class MobileProdTag {

	private static final long serialVersionUID = 975305381032981639L;
	
	private Long tagId; // 标签id 
    private String tagName;		//标签名
    private String description;		//描述

    public Long getTagId() {
		return tagId;
	}
	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
