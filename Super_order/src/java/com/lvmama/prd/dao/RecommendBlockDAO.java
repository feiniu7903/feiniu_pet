package com.lvmama.prd.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.vo.RecommendBlock;

public class RecommendBlockDAO extends BaseIbatisDAO {

	public RecommendBlock getRecommendBlockById(long blockId) {
		Object object = super.queryForObject("RECOMMEND_BLOCK.getRecommendBlockById", blockId);
		if (object != null) {
			return (RecommendBlock) object;
		}
		return null;
	}

	public List<RecommendBlock> getRecommendBlockByStation(Long blockId, String station) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("blockId", blockId);
		param.put("station", station);
		return super.queryForList("RECOMMEND_BLOCK.getRecommendBlockByBlockIdAndStation", param);
	}

	public RecommendBlock getRecommendBlockByPlaceIdAndStation(Long placeId, String station) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("placeId", String.valueOf(placeId));
		param.put("station", station);
		return (RecommendBlock) super.queryForObject("RECOMMEND_BLOCK.getRecommendBlockByPlaceIdAndStation", param);
	}
}
