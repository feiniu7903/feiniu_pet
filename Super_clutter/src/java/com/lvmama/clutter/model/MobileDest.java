package com.lvmama.clutter.model;

import java.io.Serializable;
import java.util.List;

public class MobileDest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1018830164920194222L;
	String name;
	String id;
	String pinyin;
	
	List<MobileDest> cities;
	
	private List<String> subjects;
	
	public List<MobileDest> getCities() {
		return cities;
	}
	public void setCities(List<MobileDest> cities) {
		this.cities = cities;
	}
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
	public List<String> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}
}
