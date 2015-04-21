package com.lvmama.prd.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.seo.RecommendInfo;

public class RecommendInfoDAO extends BaseIbatisDAO {

	public List<RecommendInfo> getRecommendInfoByBlockId(long id, String objectId,long rownum) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("objectId", objectId);
		if(rownum>0){
			map.put("rownum", rownum);
		}
		if(StringUtils.isNotEmpty(objectId)) {
			return super.queryForList("RECOMMEND_INFO.getRecommendInfoByBlockId",map);
		} else {
			return super.queryForList("RECOMMEND_INFO.getRecommendInfoByBlockId2",map);
		}
		
	}

	public List<RecommendInfo> getRecommendPlaceByBlockId(long id, String objectId, long number) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("iblock_id", id);
		map.put("iobject_id", objectId);
		map.put("product_type", "");
		map.put("row_number", number);
		map.put("result_list", null);
		super.queryForList("RECOMMEND_INFO.getRecommendPlaceByBlockId",map);
		return (List<RecommendInfo>) map.get("result_list"); 
	}

	public List<RecommendInfo> getRecommendProductByBlockId(long id, String objectId,String product_type, long number) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("iblock_id", id);
		map.put("iobject_id", objectId);
		map.put("product_type", product_type);
		map.put("row_number", number);
		map.put("result_list", null);
		super.queryForList("RECOMMEND_INFO.getRecommendProductByBlockId",map);
		return (List<RecommendInfo>) map.get("result_list"); 
	}

	/*public List<RecommendInfo> getSpecialRecommProductList(Long toPlaceId, Long fromPlaceId, String subProductType, int limit) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("toPlaceId", toPlaceId.toString());
		param.put("fromPlaceId", fromPlaceId);
		param.put("subProductType", subProductType);
		param.put("limit", limit);
		return super.queryForList("RECOMMEND_INFO.getSpecialRecommProductList",param);
	}*/
}
