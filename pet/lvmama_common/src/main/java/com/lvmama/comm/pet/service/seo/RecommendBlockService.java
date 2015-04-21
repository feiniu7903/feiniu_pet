package com.lvmama.comm.pet.service.seo;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.seo.RecommendBlock;

public interface RecommendBlockService {
   
	/**
	 * 根据recommendBlockId获取recommendBlock对象
	 * @param recommendBlockId
	 */
	public RecommendBlock getRecommendBlockById(Long recommendBlockId);
	/**
	 * 获取page
	 * 
	 * @param name
	 *           名字查询
	 * @param type
	 *            类型，景区，目的地等
	 * @param parentRecommendBlockId
	 *            上级RecommendBlockId
	 * @return
	 */
	public List<RecommendBlock> queryRecommendBlockByParam(Map<String, Object> param);

	/**
	 * 根据recommendBlock删除该模块及下属的所有信息
	 * @param recommendBlock
	 */
	public void deleteRecommendBlockAndInfo(RecommendBlock recommendBlock);
	/**
	 * 增加RecommendBlock
	 * @param recommendBlock
	 */
	public RecommendBlock insertRecommendBlock(RecommendBlock recommendBlock);
	 /**
     * 更新recommendBlock信息
     * @param recommendBlock
     */
	public void updateRecommendBlock(RecommendBlock recommendBlock);


}
