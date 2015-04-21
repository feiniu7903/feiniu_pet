package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;

/**
 * 城市
 * @author YangGan
 *
 */
public class City implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String pinyin;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
}
