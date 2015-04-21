package com.lvmama.comm.pet.service.mobile;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.mobile.MobileRecommendBlock;

/**
 * 推荐块. 
 * @author qinzubo
 *
 */
public interface MobileRecommendBlockService {

	/**
	 * 根据mobileRecommendBlockId获取mobileRecommendBlock对象
	 * @param mobileRecommendBlockId
	 */
	public MobileRecommendBlock getMobileRecommendBlockById(Long mobileRecommendBlockId);
	
	/**
	 * 获取page
	 * 
	 * @param blockName
	 *           名字查询
	 * @param parentId
	 *            上级MobileRecommendBlockId
	 * @return
	 */
	public List<MobileRecommendBlock> queryMobileRecommendBlockByParam(Map<String, Object> param);

	/**
	 * 根据MobileRecommendBlock删除该模块及下属的所有信息
	 * @param MobileRecommendBlock
	 * @param delType  节点类型 0：根节点 ，1：子节点 
	 */
	public void deleteMobileRecommendBlockAndInfo(MobileRecommendBlock mobileRecommendBlock,String delType);
	
	/**
	 * 增加MobileRecommendBlock
	 * @param MobileRecommendBlock
	 */
	public MobileRecommendBlock insertMobileRecommendBlock(MobileRecommendBlock mobileRecommendBlock);
	
	/**
     * 更新MobileRecommendBlock信息
     * @param MobileRecommendBlock
     */
	public void updateMobileRecommendBlock(MobileRecommendBlock mobileRecommendBlock);


}
