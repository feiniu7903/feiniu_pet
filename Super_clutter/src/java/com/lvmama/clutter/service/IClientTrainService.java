package com.lvmama.clutter.service;

import java.util.Map;

public interface IClientTrainService {
	
	/**
	 * 查询火车信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> search(Map<String, Object> param) throws Exception;
	
	/**
	 * 自动补全站点名称
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> searchTrainPlace(Map<String, Object> param) throws Exception;
	
	/**
	 * 获取火车站所在城市名称拼音
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> listTrainCities(Map<String, Object> param) throws Exception;
	
	/**
	 * 获取车次类型
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getTrainTypes(Map<String, Object> param) throws Exception;
	
}
