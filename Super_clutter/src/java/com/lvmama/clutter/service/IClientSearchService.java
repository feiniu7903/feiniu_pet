package com.lvmama.clutter.service;

import java.util.List;
import java.util.Map;

import com.lvmama.clutter.model.MobilePlace;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.ProductBean;

/**
 * 
 * @author dengcheng
 *
 */
public interface IClientSearchService {
	/**
	 * 标的综合搜索
	 * @param params
	 * @return
	 */
	Map<String,Object>  placeSearch(Map<String,Object> params);
	/**
	 * 产品综合搜索
	 * @param params
	 * @return
	 */
	String productSearch(Map<String,Object> params);
	/**
	 * 获得城市省份列表
	 * @param params
	 * @return
	 */
	List<Object> getCities(Map<String,Object> params);
	/**
	 * 线路综合搜索
	 * @param params
	 * @return
	 */
	Map<String,Object> routeSearch(Map<String, Object> params);
	/**
	 * 景点自动完成
	 * @param params
	 * @return
	 */
	Map<String,Object> placeAutoComplete(Map<String, Object> params);
	/**
	 * 摇一摇接口
	 * @param params
	 * @return
	 */
	Map<String,Object> shakePlace(Map<String, Object> params);
	/**
	 * 线路自动补全
	 * @param params
	 * @return
	 */
	Map<String,Object>  routeAutoComplete(Map<String, Object> params);
	
	/**
	 * 分页查询景点
	 * @param params
	 * @return
	 */
	Page<PlaceBean> listPlace(Map<String, Object> params);
	
	/**
	 * 分页查询度假
	 * @param params
	 * @return
	 */
	Page<ProductBean> listRoute(Map<String, Object> params);
}
