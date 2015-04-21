package com.lvmama.comm.pet.service.sup;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.sup.MetaSettlement;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;

public interface SettlementTargetService {
	
	/**
	 * 新增结算对象
	 * @param supSettlementTarget
	 * @author yuzhibing
	 */
	 Long addSettlementTarget(SupSettlementTarget supSettlementTarget,String operatorName);
	/**
	 * 结算对象查询
	 * @param param
	 * @author yuzhibing
	 * @return
	 */
	 List<SupSettlementTarget> findSupSettlementTarget(Map param );
	
	/**
	 * 结算对象
	 * @param targetId
	 * @author yuzhibing
	 * @return
	 */
	 SupSettlementTarget getSettlementTargetById(long targetId);
	/**
	 * 修改结算对象
	 * @param supSettlementTarget
	 * @author yuzhibing
	 */
	void updateSettlementTarget(SupSettlementTarget supSettlementTarget,String operatorName);
	 
	/**
	 * 修改是否有效状态
	 * @param targetId
	 * */
	void markIsValid(Map params);
	
	public Integer selectRowCount(Map searchConds);
	 
	
	List<SupSettlementTarget> getSuperSupSettlementTargetByMetaProductId(Long metaProductId);
	/**
	 * 删除采购产品的结算关系
	 * @param metaSettlement
	 */
	void deleteMetaRelation(MetaSettlement metaSettlement,String operatorName);
	Integer selectByMetaProductId(Long metaProductId);
	void addMetaRelation(MetaSettlement metaSettlement,String operatorName);
	MetaSettlement findMetaSettlementByMetaProductId(Long metaProductId);
	
	/**
	 * 
	 * @param supplierId
	 * @return
	 */
	SupSettlementTarget getSupSettlementTargetBySupplierId(Long supplierId);
	
	
	/**
	 * 根据下拉框输入的内容动态的查询结算对象
	 * @param search 输入内容
	 * @param size 最多返回的数量
	 * @author yanggan 
	 * @return
	 */
	List<SupSettlementTarget> selectSupSettlementTargetByName(String search,Integer size);
	
	/**
	 * 根据采购产品ID查询结算对象ID
	 * 
	 * @param metaProductId 采购产品ID
	 * @author yanggan 
	 * @return 结算对象ID
	 */
	Long selectTargetIdByMetaProductId(Long metaProductId);
	
}
