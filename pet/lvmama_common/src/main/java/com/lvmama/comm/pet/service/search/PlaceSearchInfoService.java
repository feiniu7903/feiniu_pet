package com.lvmama.comm.pet.service.search;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.search.PlaceSearchInfo;


public interface PlaceSearchInfoService {
	/**
	 * 获取周边跟团游频道数据
	 * @param fromPlaceId
	 * @param toPlaceId
	 * @return
	 */
	public Map<String, Object> getAroundChannelData(Long fromPlaceId, String toPlaceId);
	/**
	 * 获取门票频道数据
	 * @return
	 */
	public Map<String,Object> getTicketChannelData();
	
	/**
	 * 专门为360分销用
	 * 1. 取当前景区
	 * 2. 取景区所在城市的8个景点
	 * 3. 取景区所在省份的8个景点
	 */
	public List<PlaceSearchInfo> getPlaceInfoFor360(String placeName);
	
	public PlaceSearchInfo getPlaceSearchInfoByPlaceId(Long placeId);
	
	/**
	 * 根据查询条件查询placeSearchInfo表数据
	 * @param param 查询条件
	 * @return
	 */
	List<PlaceSearchInfo> queryPlaceSearchInfoByParam(Map<String, Object> param);
}
