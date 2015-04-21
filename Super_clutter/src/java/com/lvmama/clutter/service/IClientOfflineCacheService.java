package com.lvmama.clutter.service;

import java.util.Map;

/**
 * 客户端离线缓存服务
 * @author dengcheng
 *
 */
public interface IClientOfflineCacheService {
	Map<String,Object> getPlaceSubjectCache(Map<String,Object> param);
	/**
	 * 门票筛选数据
	 * @param param
	 * @return
	 */
	Map<String,Object> getPlaceCitiesCache(Map<String,Object> param);
	/**
	 * 团购筛选数据
	 * @param param
	 * @return
	 */
	Map<String,Object> getGroupCitiesCache(Map<String,Object> param);
	/**
	 * 自由行筛选数据
	 * @param param
	 * @return
	 */
	Map<String,Object> getRouteFilterCache(Map<String,Object> param);
}
