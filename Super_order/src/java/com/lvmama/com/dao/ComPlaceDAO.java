package com.lvmama.com.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.Place;

public class ComPlaceDAO extends BaseIbatisDAO {

	private static final String[] String = null;
	
	public Place getComPlaceByPlaceIds(String placeIdStr){
		return (Place) super.queryForObject("COM_PLACE.getComPlaceByPlaceIds", placeIdStr);
	}

	
	public List<String> getBaiduCityNameByPlaceIds(List<Long> placeIds){
		Map<String ,Object> placeIdList = new HashMap<String, Object>();
		placeIdList.put("placeIdList", placeIds);
		return (List<String>) super.queryForList("COM_PLACE.getBaiduCityNameByPlaceIds", placeIdList);
	}
	
	public Place getFromDestByProductId(long productId) {
		return (Place) super.queryForObject("COM_PLACE.getFromDestByProductId", productId);
	}

	public Place getToDestByProductId(long productId) {
		return (Place) super.queryForObject("COM_PLACE.getToDestByProductId", productId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lvmama.dao..ComPlaceDAO#findComPlace(java.util.Map)
	 */
	public List<Place> findComPlace(Map param) {
		List<Place> result = new ArrayList<Place>();
		result = (List<Place>) super.queryForList("COM_PLACE.selectByParams", param);
		return result;
	}
	
	/**
	 * 取place信息，针对前台产品详情当中使用
	 * @param param
	 * @return
	 */
	public List<Place> findComPlaceSimple(Map param){
		return super.queryForList("COM_PLACE.selectSimpleByParams", param);
	}
 
	/**
	 * 根据主键加载一条记录
	 */
	public Place load(Long placeId) {
		return (Place) super.queryForObject("COM_PLACE.selectByPrimaryKey", placeId);
	}
	
	/**
	 * 根据主键集合获取记录集合
	 * @author ZHANG Nan
	 * @param placeIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Place> selectByPlaceIds(List<Long> placeIds){
		Map<String ,Object> placeIdList = new HashMap<String, Object>();
		placeIdList.put("placeIdList", placeIds);
		return (List<Place>) super.queryForList("COM_PLACE.selectByPlaceIds", placeIdList);
	}
	

	public Place getComPlaceByDestId(long placeId) {
		return (Place) super.queryForObject("COM_PLACE.getComPlaceByDestId", placeId);
	}
   
	/**
	 * 取分销产品的景点信息
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Place> getDistributionProductPlace(Map<String,Object> params){
		return (List<Place>)super.queryForList("COM_PLACE.getDistributionProductPlace",params);
	}
	
	/**
	 * 取分销产品的景点总数
	 * @param params
	 * @return
	 */
	public Long getDistributionProductPlaceCount(Map<String, Object> params) {
		return (Long) super.queryForObject("COM_PLACE.getDistributionProductPlaceCount", params);
	}
	
	/**
	 * 查询分销景区介绍
	 * 注：实际是把景区下的某个产品的介绍给做景区介绍
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getFeaturesForDistribution(Long placeId, Long distributorId) {
		Map <String,Object> params = new HashMap<String,Object>();
		params.put("placeId", placeId);
		params.put("distributorId", distributorId);
		return (List<String>) super.queryForList("COM_PLACE.getFeaturesForDistribution", params);
	}

	/**
	 * @param productId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Place> getComPlaceByProductId(Long productId) {
		return (List<Place>) super.queryForList("COM_PLACE.getComPlaceByProductId", productId);
	}


	public List<Place> getNewComPlaceByProductId(Long productId) {
		return (List<Place>) super.queryForList("COM_PLACE.getNewComPlaceByProductId", productId);
	}
}