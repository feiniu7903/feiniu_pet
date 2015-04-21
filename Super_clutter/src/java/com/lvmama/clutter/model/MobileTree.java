package com.lvmama.clutter.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MobileTree implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8923056192911142931L;
	private String key;
	private String value;
	private String pinYin;
	private List<MobileTree> list = new ArrayList<MobileTree>();
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<MobileTree> getList() {
		return list;
	}
	public void setList(List<MobileTree> list) {
		this.list = list;
	}
	public String getPinYin() {
		return pinYin;
	}
	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}
}
