package com.lvmama.pet.seo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.seo.RecommendInfo;

public class RecommendInfoDAO extends BaseIbatisDAO{

	public void updateRecommendInfo(RecommendInfo recommendInfo) {
		super.update("RECOMMEND_INFO.updateRecommendInfo", recommendInfo);
	}

	public void deleteRecommendInfoByParam(Map<String, Object> param) {
		super.delete("RECOMMEND_INFO.deleteRecommendInfoByParam", param);
	}

	public void deleteRecommendInfoByBlockIdAndSon(Long recommendBlockId) {
		 super.delete("RECOMMEND_INFO.deleteRecommendInfoByBlockIdAndSon", recommendBlockId);
	}
	
    public void insertRecommendInfo(RecommendInfo record) {
        super.insert("RECOMMEND_INFO.insertRecommendInfo", record);
    }

	public Long countRecommendInfoByParam(Map<String, Object> param) {
		return (Long) super.queryForObject("RECOMMEND_INFO.countRecommendInfoByParam", param);
	}

	/**
	 * 查询推荐模块中的信息（无关联价格）
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RecommendInfo> queryRecommendInfoByParam(Map<String, Object> param) {
		if (null == param || param.isEmpty()) {
			return new ArrayList<RecommendInfo>(0);
		}
		if (null == param.get("endRows")) {
			param.put("endRows", 100);
		}
		return (List<RecommendInfo>) super.queryForList("RECOMMEND_INFO.queryRecommendInfoByParam", param);
	}
	
	/**
	 * 推荐模块中的产品类型的推荐信息联合产品价格
	 * @param recommendBlockId
	 * @param itemNumberLimit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RecommendInfo> queryRecommendInfoAndProductByParam(Map<String,Object> param) {
		return (List<RecommendInfo>) super.queryForList("RECOMMEND_INFO.queryRecommendInfoAndProductByParam", param);
	}
	
	/**
	 * 查询推荐模块中的信息（无关联价格）
	 * @param param dataCode 形如 'ranked_destroute','ranked_around','ranked_abroad'
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RecommendInfo> queryRecommendInfoByDataCode(Map<String, Object> param) {
		if (null == param || param.isEmpty()) {
			return new ArrayList<RecommendInfo>(0);
		}
		if (null == param.get("endRows")) {
			param.put("endRows", 100);
		}
		return (List<RecommendInfo>) super.queryForList("RECOMMEND_INFO.queryRecommendInfoByDataCode", param);
	}
	
	/**
	 * 推荐模块中的产品类型的推荐信息联合产品价格
	 * @param recommendBlockId
	 * @param itemNumberLimit dataCode 形如 'ranked_destroute','ranked_around','ranked_abroad'
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RecommendInfo> queryRecommendInfoAndProductByDataCode(Map<String,Object> param) {
		return (List<RecommendInfo>) super.queryForList("RECOMMEND_INFO.queryRecommendInfoAndProductByDataCode", param);
	}

	/**
	 * 推荐模块中的Place类型的推荐信息联合place最低价格
	 * @param recommendBlockId
	 * @param itemNumberLimit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RecommendInfo> queryRecommendInfoAndPlaceByParam(Map<String,Object> param) {
		return (List<RecommendInfo>) super.queryForList("RECOMMEND_INFO.queryRecommendInfoAndPlaceByParam", param);
	}
	
	/**
	 * 推荐模块中的Place类型的推荐信息联合place最低价格
	 * @param recommendBlockId
	 * @param itemNumberLimit dataCode 形如 'ranked_destroute','ranked_around','ranked_abroad'
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RecommendInfo> queryRecommendInfoAndPlaceByDataCode(Map<String,Object> param) {
		return (List<RecommendInfo>) super.queryForList("RECOMMEND_INFO.queryRecommendInfoAndPlaceByDataCode", param);
	}

}