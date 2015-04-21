package com.lvmama.distribution.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.distribution.DistributionPlaceImage;
import com.lvmama.comm.bee.po.distribution.DistributionPlaceProduct;

/**
 * 分销360景区
 * 
 * @author gaoxin
 * 
 */
@SuppressWarnings("unchecked")
public class DistributionPlaceImageDAO extends BaseIbatisDAO {
	
	public List<DistributionPlaceProduct> selectAllRouteProduct(Map<String,Object> param){
		return (List<DistributionPlaceProduct>)super.queryForList("DISTRIBUTION_PLACE_IMAGE.selectAllRouteProduct",param);
	}
	public Integer selectAllRouteProductCount(){
		return (Integer)super.queryForObject("DISTRIBUTION_PLACE_IMAGE.selectAllRouteProductCount");
	}
	
	public List<DistributionPlaceImage> selectPlaceCityByName(Map<String, Object> param) {
		return (List<DistributionPlaceImage>) super.queryForList("DISTRIBUTION_PLACE_IMAGE.selectPlaceCityByName",param);
	}

	public List<DistributionPlaceImage> selectPlaceImageByName(Map<String, Object> param) {
		return (List<DistributionPlaceImage>) super.queryForList("DISTRIBUTION_PLACE_IMAGE.selectPlaceByName",param);
	}

	public List<String> selectImageByPlaceId(long placeId) {
		return (List<String>) super.queryForList("DISTRIBUTION_PLACE_IMAGE.selectImageByPlaceId", placeId);
	}

	/**
	 * 通过景区id查询景区及景区下产品价格
	 * 
	 * @param placeId
	 * @return
	 */
	public List<DistributionPlaceProduct> selectProductByPlaceId(Long placeId) {
		return (List<DistributionPlaceProduct>) super.queryForList("DISTRIBUTION_PLACE_IMAGE.selectProductByPlaceId",placeId);
	}

	public DistributionPlaceImage selectSightByName(String placeName) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("placeName", placeName);
		return (DistributionPlaceImage) super.queryForObject("DISTRIBUTION_PLACE_IMAGE.selectSightByPlaceName",param);
	}

}
