package com.lvmama.finance.settlement.ibatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.PageDao;
import com.lvmama.finance.common.ibatis.po.ComboxItem;
import com.lvmama.finance.settlement.ibatis.po.OrdRefundment;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlementPayment;
import com.lvmama.finance.settlement.ibatis.po.SettlementQueueItem;
import com.lvmama.finance.settlement.ibatis.po.SupSettlementTarget;

@Repository
@SuppressWarnings("unchecked")
public class SettlementQueueItemDAO extends PageDao {
	/**
	 * 查询采购产品的分支类型
	 * @param id
	 * @return
	 */
	public List<ComboxItem> getMetaBranchTypeByMetaProductId(Long id){
		return queryForList("SettlementQueueItem.getMetaBranchTypeByMetaProductId",id);
	}
	/**
	 * 查询结算队列项
	 * 
	 * @param map
	 * @return
	 */

	public Page<SettlementQueueItem> getSettlementQueueItems(Map<String, Object> map) {
		return queryForPageFin("SettlementQueueItem.querySettlementItem", map);
	}

	/**
	 * 全部生成结算单时，获取结算队列项ID和订单子子项ID
	 * 
	 * @param map
	 * @return SettlementQueueItem 只有结算队列项ID和订单子子项ID
	 */
	public List<SettlementQueueItem> getSettlementQueueItemOnlyIds(Map<String, Object> map) {
		return queryForList("SettlementQueueItem.getSettlementQueueItemOnlyIds", map);
	}

	/**
	 * 更新结算队列项状态
	 * 
	 * @param ids
	 *            订单子子项ID
	 * @return
	 */
	public List<Long> updateSettlementItemStatus(List<Long> ids, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		map.put("status", status);
		update("SettlementQueueItem.updateSettlementItemStatus", map);
		return ids;
	}

	/**
	 * 对结算队列项，新增抵扣款
	 * 
	 * @param id
	 *            结算队列项ID
	 * @return
	 */
	public Long insertSettlementItemsForCharge(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return (Long) insert("SettlementQueueItem.insertSettlementItemsForCharge", map);
	}

	/**
	 * 根据id查询结算队列项
	 * 
	 * @param id
	 * @return
	 */
	public SettlementQueueItem getSettlementQueueItemById(Long id) {
		return (SettlementQueueItem) queryForObject("SettlementQueueItem.getSettlementQueueItemById", id);
	}

	/**
	 * 根据订单子子项ID，查询结算所需的数据
	 * 
	 * @param orderItemMetaIdList
	 *            订单子子项ID列表
	 * @param queueItemIdList
	 *            订单子子项ID列表           
	 * @return 
	 */
	public List<SettlementQueueItem> getSettleDataByOrderItemMetaId(List<List<Long>> metaIdList,List<List<Long>> queueIdList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("metaIdList", metaIdList);
		if(queueIdList != null && queueIdList.size() > 0){
			map.put("queueIdList", queueIdList);
		}
		return queryForList("SettlementQueueItem.querySettleDataByOrderItemMetaId", map);
	}

	/**
	 * 
	 * 根据结算队列项ID，查询结算所需的数据
	 * 
	 * @param orderItemMetaIds
	 *            订单子子项ID列表
	 * @return
	 */
	public List<SettlementQueueItem> getSettleDataBySettlementQueueItemId(List<Long> queueItemIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", queueItemIds);
		return queryForList("SettlementQueueItem.getSettleDataBySettlementQueueItemId", map);
	}

	/**
	 * 删除结算队列项
	 * 
	 * @param ids
	 */
	public void deleteSettlementQueueItemById(List<List<Long>> ids) {
		if (ids == null || ids.size() == 0) {
			return;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		delete("SettlementQueueItem.deleteSettlementQueueItemById", map);
	}
	/**
	 * 不结，根据结算队列项ID，更新订单子子项状态为不结
	 * @param ids
	 */
	public void updateOrderItemMetaSettlementStatusForNeverSettle(List<Long> ids){
		if (ids == null || ids.size() == 0) {
			return;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		update("SettlementQueueItem.updateOrderItemMetaSettlementStatusForNeverSettle", map);
	}
	/**
	 * 更新订单子子项的结算状态
	 * 
	 * @param ids
	 */
	public void updateOrderItemMetaSettlementStatus(List<List<Long>> ids, String status) {
		if (ids == null || ids.size() == 0) {
			return;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		map.put("status", status);
		update("SettlementQueueItem.updateOrderItemMetaSettlementStatus", map);
	}

	/**
	 * 更新订单的结算状态
	 * 
	 * @param ids
	 */
	public void updateOrderSettlementStatus(List<List<Long>> ids, String status) {
		if (ids == null || ids.size() == 0) {
			return;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		map.put("status", status);
		update("SettlementQueueItem.updateOrderSettlementStatus", map);
	}

	/**
	 * 删除结算子单后，新增结算对列项
	 * 
	 * @param subSettlementId
	 *            结算子单ID
	 */
	public void insertSettlementQueueItem(Long subSettlementId) {
		this.insert("SettlementQueueItem.insertSettlementQueueItem", subSettlementId);
	}

	/**
	 * 删除结算子单项后，新增结算队列项
	 * 
	 * @param subSettlementItemIds
	 *            结算子单项ID
	 */
	@SuppressWarnings("rawtypes")
	public void batchInsertSettlementQueueItem(Long[] subSettlementItemIds, String status) {
		Map map = new HashMap();
		map.put("ids", subSettlementItemIds);
		map.put("status", status);
		this.insert("SettlementQueueItem.batchInsertSettlementQueueItem", map);
	}

	/**
	 * 生成抵扣款的结算对列项
	 * 
	 * @param amount
	 *            抵扣款金额
	 * 
	 * @param settlementId
	 */
	@SuppressWarnings("rawtypes")
	public void insertSettlementQueueItemDeduction(Double amount, Long subSettlementId, Long settlementId,Long ordItemMetaId) {
		Map map = new HashMap();
		map.put("settlementId", settlementId);
		map.put("amount", amount);
		map.put("subSettlementId", subSettlementId);
		map.put("ordItemMetaId", ordItemMetaId);
		this.insert("SettlementQueueItem.insertSettlementQueueItemDeduction", map);
	}
	/**
	 * 查询被删除的结算子单项的支付金额总和
	 * 
	 * @param settlementId
	 * 
	 * @return
	 */
	public Double searchDeletedSumPayedAmount(Long settlementId){
		return (Double) this.queryForObject("SettlementQueueItem.searchDeletedSumPayedAmount",settlementId);
	}
	
	public void updateSettlementQueueItem2Deduction(Long settlementId) {
		this.update("SettlementQueueItem.updateSettlementQueueItem2Deduction", settlementId);
	}
	@SuppressWarnings("rawtypes")
	public void updateSettlementQueueItem2DeductionSingle(Long settlementId,Double amount) {
		Map map = new HashMap();
		map.put("settlementId", settlementId);
		map.put("amount", amount);
		this.update("SettlementQueueItem.updateSettlementQueueItem2DeductionSingle", map);
	}
	
	public void updateSettlementQueueItem2Zero(Long settlementId) {
		this.update("SettlementQueueItem.updateSettlementQueueItem2Zero", settlementId);
	}
	

	/**
	 * 查询结算队列项的结算对象ID
	 * 
	 * @param id
	 * @return 如果不存在，返回null
	 */
	public Long getTargetIdByQueueItemId(Long id) {
		return (Long) queryForObject("SettlementQueueItem.getTargetIdByQueueItemId", id);
	}

	/**
	 * 插入支付记录
	 * 
	 * @param po
	 */
	public Long insertOrdSettlementPayment(OrdSettlementPayment po) {
		return (Long) insert("SettlementQueueItem.insertOrdSettlementPayment", po);
	}
	
	/**
	 * 根据订单号查询退款记录
	 * @param orderIds
	 * @return
	 */
	public List<OrdRefundment> getRefundmentByOrderItemMetaIds(List<Long> orderItemMetaIds){
		return (List<OrdRefundment>) queryForList("SettlementQueueItem.getRefundmentByOrderItemMetaIds", orderItemMetaIds);
	}
	/**
	 * 查询结算对象信息，附带供应商的支付对象字段
	 * @param id
	 * @return
	 */
	public SupSettlementTarget getSettlementTargetById(Long id){
		return (SupSettlementTarget) queryForObject("SettlementQueueItem.getSettlementTargetById",id);
	}
	/**
	 * 根据结算对象ID，查询供应商的我方结算主体
	 * @param id
	 * @return
	 */
	public String getCompanyIdByTargetId(Long id){
		return (String) queryForObject("SettlementQueueItem.getCompanyIdByTargetId",id);
	}
	
}
