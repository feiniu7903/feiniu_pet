package com.lvmama.back.web;

public interface CityFeatureAction {

	/**
	 * 默认选择的城市ID
	 * @return
	 */
	String getCityId();
	
	/**
	 * 改变了城市，需要做什么
	 * @param cityId 新选择的城市ID
	 */
	void changeCity(String cityId);
	
}
