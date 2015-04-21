package com.lvmama.comm.pet.service.place;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.PlaceActivity;

public interface PlaceActivityService {
	/**
	 * 根据placeid获取该place的所有的活动
	 * @param placeId
	 * @return
	 */
	public List<PlaceActivity> queryPlaceActivityListByPlaceId(Long placeId);
	/**
	 * 根据主键获取活动
	 * @param placeActivityId
	 * @return
	 */
	public PlaceActivity queryPlaceActivityByPlaceActivityId(Long placeActivityId);
	/**
	 * 保存活动
	 * @param placeActivity
	 */
	public void savePlaceActivity(PlaceActivity placeActivity);
	/**
	 * 根据主键删除活动
	 * @param placeActivityId
	 */
	public void deletePlaceActivityByPlaceActivityId(Long placeActivityId);
	/**
	 * 根据参数获取该place的所有的活动
	 * @param map
	 * @return
	 * @author nixianjun 2013.7.22
	 */
	List<PlaceActivity> queryPlaceActivityListByParam(Map map);
}
