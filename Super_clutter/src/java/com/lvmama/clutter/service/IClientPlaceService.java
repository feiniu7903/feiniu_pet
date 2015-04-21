package com.lvmama.clutter.service;

import java.util.Map;

import com.lvmama.clutter.model.MobilePlace;

public interface IClientPlaceService {

	/**
	 * 获得景点酒店详细信息
	 */
	MobilePlace getPlace(Map<String, Object> param);
	
	/**
	 * 获取branch详情
	 * @param param
	 * @return
	 */
	public Map<String,Object> getBranchDetail(Map<String, Object> param) ;
}
