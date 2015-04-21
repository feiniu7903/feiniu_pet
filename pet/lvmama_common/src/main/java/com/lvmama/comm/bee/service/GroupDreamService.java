package com.lvmama.comm.bee.service;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.group.GroupDream;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.GroupDreamInfo;
/**
 * 团购梦想Serivice
 * @author gengsiqiang
 * @author songlianjun
 *
 */
public interface GroupDreamService {
	
	
	Integer selectRowCount(Map searchConds);
	GroupDream getGroupDream(Long dreamId);
	List<GroupDream> getGroupDreams(Map param);
	/**
	 * 保存团购梦想
	 * @param groupDream 
	 * @return
	 */
	Long addGroupDream(GroupDream groupDream,String operatorName);
	/**
	 * addGroupDream 的回滚方法
	 * 
	 * @author: ranlongfei 2012-8-2 下午6:10:16
	 * @param groupDream
	 * @param operatorName
	 */
	void addGroupDreamRollback (GroupDream groupDream,String operatorName);
	/**
	 * 更新
	 * @param groupDream
	 */
	void updateGroupDream(GroupDream groupDream,String operatorName);
	/**
	 * 逻辑删除团购梦想
	 * @param groupDream
	 */
    void deleteGroupDream(GroupDream groupDream);
    /**
     * 查询团购梦想参与的喜欢的提交信息
     * @param dreamId
     * @return
     */
    Page  getGroupDreamEnjoySubmitters(Page pageVO,Map queryParamMap);
    
    GroupDream submitDream(Long dreamId, String email, String isEnjoy, String ipAddress);

	/**
	 * 获取当月团够梦想产品
	 * 
	 * @return
	 */
	List<GroupDreamInfo> getCurrMonthDreamProducts();
	//团购方法
	 /**
	 * 查询团购上线产品
	 * 
	 * @param productIdList
	 */
	List<ProdProduct> queryOnlineProductInProductIds(List<Long> productIdList);
	/**
	 * 通过参数查询团购上线产品
	 * 
	 * @param productIdList
	 */
	List<ProdProduct> queryOnlineProductByParams(Map<String,Object> map);
	/**
	 * 分页显示团购疯抢记录
	 * 
	 * @return
	 */
	Map<String, Object> getOnlineAndOffelineProductByChannel(Long page, Long pageSize);
	/**
	 * 
	 * @分页查询团购产品参与用户 param productId 1@return
	 */
	Map<String, Object> getPrdJoinUsers(Long productId, Long page, Long pagesize);

	/**
	 * 统计产品已购买数量
	 * 
	 * @param productId
	 * @return
	 */
	Long countOrderByProduct(Long productId);
	/**
	 * 获取今日疯抢产品
	 * 
	 * @param productId
	 *            团购产品ID
	 * @return
	 */
	Map<String, Object> getTodayGroupProduct(Long productId);
	/**
	 * 能 根据当前产品查询产品标签
	 * 
	 * @param productId
	 *            产品ID
	 * @param limitRows
	 *            显示记录条数
	 * @return
	 */
	List<Map> getPrdTagByProductId(Long productId);
}
