package com.lvmama.pet.search.dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.vo.place.DimensionVo;
import com.lvmama.comm.utils.GeoLocation;

public class PlaceSearchInfoDAO extends BaseIbatisDAO {
	/**
	 * @deprecated why comment call me?
	 * 根据点评率获取数据
	 * @param provinceId
	 * @param cityId
	 * @param stage
	 * @param size
	 * @return
	 */
	public List<PlaceSearchInfo> queryPlaceSearchInfoListByCmt(Long provinceId, Long cityId, String stage, int size) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("provinceId", provinceId);
		params.put("cityId", cityId);
		params.put("stage", stage);
		params.put("size", size);
		return super.queryForList("PLACE_SEARCH_INFO.queryPlaceSearchInfoListByCmt", params);
	}
	
	/**
	 * @param placeName
	 * @return
	 */
	public List<PlaceSearchInfo> getPlaceInfoFor360(String placeName){
		if(StringUtils.isEmpty(placeName))
			return null;
		else
			return super.queryForList("PLACE_SEARCH_INFO.queryPlaceSearchInfoListByPlaceName", placeName);
	}
	
	/**
	 * @deprecated why comment call me?
	 * @param placeId
	 * @return
	 */
	public PlaceSearchInfo getPlaceSearchInfoByPlaceId(Long placeId){
		return (PlaceSearchInfo)super.queryForObject("PLACE_SEARCH_INFO.getPlaceSearchInfoByPlaceId", placeId);
	}
	
	/**
	 * 根据查询条件查询place_search_info表
	 * @param param 查询条件
	 * @return 结果列表
	 * <p>当<code>param</code>为null或者空时，将返回null,否则根据<code>param</code>中的条件进行查询</p>
	 */
	@SuppressWarnings("unchecked")
	public List<PlaceSearchInfo> queryPlaceSearchInfoByParam(final Map<String, Object> param) {
		if (null == param || param.isEmpty()) {
			return null;
		}
		return super.queryForList("PLACE_SEARCH_INFO.queryPlaceSearchInfoByParam", param);
	}
	
	/**
	 * 根据参数获取周边指定景点/酒店
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PlaceSearchInfo> queryVicinityPlaceSearchInfoListByPlaceName(final Map<String, Object> param) {
		return super.queryForList("PLACE_SEARCH_INFO.queryVicinityPlaceSearchInfoListByPlaceName", param);
	}

	public List<PlaceSearchInfo> queryVicinityPlaceSearchInfoListByDimension(
			DimensionVo d) {
		return  super.queryForList("PLACE_SEARCH_INFO.queryVicinityPlaceSearchInfoListByDimension", d);
	}
}
