package com.lvmama.pet.seo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.seo.RecommendBlock;

public class RecommendBlockDAO extends BaseIbatisDAO{

    @SuppressWarnings("unchecked")
	public List<RecommendBlock> queryRecommendBlockByParam(Map<String, Object> param) {
		List<RecommendBlock> recommendBlockList=(List<RecommendBlock>)super.queryForList("RECOMMEND_BLOCK.queryRecommendBlockByParam", param);
		return recommendBlockList;
	}

	@SuppressWarnings("unchecked")
	public RecommendBlock getRecommendBlockById(Long recommendBlockId) {
        Map<String, Object> param = new HashMap<String, Object>();
		param.put("recommendBlockId", recommendBlockId);
		List<RecommendBlock> recommendBlockList=(List<RecommendBlock>)super.queryForList("RECOMMEND_BLOCK.queryRecommendBlockByParam", param);
		if(recommendBlockList!=null && recommendBlockList.size()>0){
			return recommendBlockList.get(0);
		}
        return new RecommendBlock();
	}

	public void deleteRecommendBlockByParam(RecommendBlock recommendBlockParam) {
		super.delete("RECOMMEND_BLOCK.deleteRecommendBlockByParam", recommendBlockParam);
	}

	public void insertRecommendBlock(RecommendBlock recommendBlock) {
		super.insert("RECOMMEND_BLOCK.insertRecommendBlock", recommendBlock);
	}
	
	public void updateRecommendBlock(RecommendBlock recommendBlock) {
		super.update("RECOMMEND_BLOCK.updateRecommendBlock", recommendBlock);
	}

}