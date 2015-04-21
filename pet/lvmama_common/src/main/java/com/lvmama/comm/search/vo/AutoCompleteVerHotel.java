package com.lvmama.comm.search.vo;

import java.io.Serializable;

import org.apache.commons.lang3.math.NumberUtils;


public class AutoCompleteVerHotel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	
	/** 经纬度 */
	protected Double longitude;
	/** 经纬度 */
	protected Double latitude;
	/** 百度geo 经纬度连在一起 */
	private String baidugeo;
	
	/** 自动补全的名字   行政区，地标，和酒店名称*/
	private String autocompleteName;
	
	/** 自动补全的名字  上一级名字 */
	private String parentName;
	
	/** 自动补全的拼音 */
	private String autocompletepinyin;
	
	/** 自动补全的拼音简写 */
	private String autocompletepinyinSimple;
	
	/** 自动补全的标示符  1是行政区 2是地标 3是酒店*/
	private String autocompleteMark;
	/** 名字加拼音加简拼*/
	private String matchword ;
	
	private String parentId;
	
	private int order;

	public Double getLongitude() {
		return longitude;
	}

//	public void setLongitude(Double longitude) {
//		this.longitude = longitude;
//	}
	
	public void setLongitude(String longitude) { 
		this.longitude = NumberUtils.toDouble(longitude); 
	}

	public Double getLatitude() {
		return latitude;
	}

//	public void setLatitude(Double latitude) {
//		this.latitude = latitude;
//	}

	public void setLatitude(String latitude) {
		this.latitude = NumberUtils.toDouble(latitude); 
	}
	
	public String getAutocompleteName() {
		return autocompleteName;
	}

	public void setAutocompleteName(String autocompleteName) {
		this.autocompleteName = autocompleteName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getAutocompletepinyin() {
		return autocompletepinyin;
	}

	public void setAutocompletepinyin(String autocompletepinyin) {
		this.autocompletepinyin = autocompletepinyin;
	}

	public String getAutocompletepinyinSimple() {
		return autocompletepinyinSimple;
	}

	public void setAutocompletepinyinSimple(String autocompletepinyinSimple) {
		this.autocompletepinyinSimple = autocompletepinyinSimple;
	}

	public String getAutocompleteMark() {
		return autocompleteMark;
	}

	public void setAutocompleteMark(String autocompleteMark) {
		this.autocompleteMark = autocompleteMark;
	}

	public String getBaidugeo() {
		return baidugeo;
	}

	public void setBaidugeo(String baidugeo) {
		this.baidugeo = baidugeo;
	}

	public String getMatchword() {
		return matchword;
	}

	public void setMatchword(String matchword) {
		this.matchword = matchword;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	
	
}
