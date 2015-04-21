package com.lvmama.comm.search.vo;

public class JasonPlaceBean {
	/** 名称 */
	private String name;
	/****/
	private String id;       //关联产品外键
	/** 拼音 */
	private String pinyin;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	

}
