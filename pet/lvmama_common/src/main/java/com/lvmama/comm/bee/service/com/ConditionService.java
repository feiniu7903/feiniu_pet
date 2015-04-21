package com.lvmama.comm.bee.service.com;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pub.ComCondition;

public interface ConditionService {
	/**
	 * 添加
	 * @param condition
	 */
	public void addCondition(ComCondition condition);

	/**
	 * 更新
	 * @param condition
	 */
	public void updateCondition(ComCondition condition);
	
	/**
	 * 检索
	 * @param params
	 * @return
	 */
	public List<ComCondition> getConditionByObjectId(Map params);
	
	/**
	 * 根据主键获取
	 * @param conditionId
	 * @return
	 */
	public ComCondition getConditionByConditionId(Long conditionId);
	/**
	 * 删除信息提示
	 * @param cond
	 */
	public void delCondition(ComCondition cond);
	
	/**
	 * 获得指定类型的产品的信息
	 * @param productId
	 * @param conditionType
	 * @return
	 */
	public List<ComCondition> getMetaConditionByProduct(Long productId);
	
}
