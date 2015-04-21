package com.lvmama.pet.fin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.fin.SetSettlementItem;
import com.lvmama.comm.pet.vo.Page;

/**
 * 
 * 订单结算项DAO
 * 
 * @author yanggan
 * 
 */
@Repository
@SuppressWarnings("unchecked")
public class SetSettlementItemDAO extends BaseIbatisDAO {
	/**
	 * 查询分页的订单结算项
	 * 
	 * @param map
	 *            查询条件
	 * @return
	 */
	public Page<SetSettlementItem> searchItemList(Map<String, Object> map) {
		return super.queryForPage("SETTLEMENT.searchItemList", map);
	}
	
	/**
	 * 查询分页的订单结算项
	 * 
	 * @param map
	 *            查询条件
	 * @return
	 */
	public Page<SetSettlementItem> searchList(Map<String, Object> map) {
		return super.queryForPage("SET_SETTLEMENT_ITEM.searchList", map);
	}

	/**
	 * 根据查询条件查询订单结算项
	 * 
	 * @param map
	 *            查询条件
	 * @return
	 */
	public List<SetSettlementItem> searchItem(Map<String, Object> map) {
		return super.queryForList("SET_SETTLEMENT_ITEM.searchItem", map,true);
	}

	/**
	 * 更新订单结算项
	 * 
	 * @param settlementItemIds
	 *            订单结算项ID
	 * @return
	 */
	public int updateSettlementItems(Map<String,Object> map) {
		int limit = 3;
		int updateRow = 0;
		List<Long> itemIdList = (List<Long>) map.get("ids");
		int s;
		if(itemIdList.size() % limit == 0){
			s = (int) (itemIdList.size() / limit);
		}else{
			s = (int) (itemIdList.size() / limit) + 1;
		}
		for (int i = 0; i < s; i++) {
			int startInx = i * limit;
			int endInx = startInx + limit;
			endInx = endInx > itemIdList.size() ? itemIdList.size() : endInx;
			map.put("ids",itemIdList.subList(startInx, endInx));
			updateRow += super.update("SET_SETTLEMENT_ITEM.updateSettlementItems", map);
		}
		return updateRow;
	}


	/**
	 * 更新订单结算项的结算单号
	 * 
	 * @param settlementItemIds
	 *            订单结算项ID
	 * @param settlementId
	 *            结算单号
	 * @return
	 */
	public int updateSettlementItemSettlementId(List<Long> settlementItemIds, Long settlementId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", settlementItemIds);
		map.put("settlementId", settlementId);
		return super.update("SET_SETTLEMENT_ITEM.updateSettlementItems", map);
	}

	/**
	 * 从结算单中移除已结算项（更新settlement_id为null，结算状态，join_settlement_time为null）
	 * 
	 * @param settlementItemIds
	 * @param settlementStatus
	 * @return
	 */
	public int removeSettlementItem(List<Long> settlementItemIds, String settlementStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", settlementItemIds);
		map.put("settlementStatus", settlementStatus);
		return super.update("SET_SETTLEMENT_ITEM.removeSettlementItem", map);
	}

	/**
	 * 根据结算单ID更新订单结算项的结算状态
	 * 
	 * @param settlementId
	 *            结算单ID
	 * @param settlementStatus
	 *            结算状态
	 * @return
	 */
	public int updateItemSettlementStatusBySettlementId(Long settlementId, String settlementStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("settlementId", settlementId);
		map.put("status", settlementStatus);
		return super.update("SET_SETTLEMENT_ITEM.updateItemSettlementStatusBySettlementId", map);
	}

	/**
	 * 根据订单号查询订单结算项
	 * 
	 * @param orderId
	 *            订单号
	 * @return
	 */
	public List<SetSettlementItem> searchSettlementItemByOrderId(Long orderId) {
		return super.queryForList("SET_SETTLEMENT_ITEM.searchSettlementItemByOrderId", orderId);
	}

	/**
	 * 根据订单结算项的ID查询订单结算项
	 * 
	 * @param settlementItemIds
	 *            订单结算项ID
	 * @return
	 */
	public List<SetSettlementItem> searchItemsByItemIds(List<Long> settlementItemIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", settlementItemIds);
		return super.queryForList("SET_SETTLEMENT_ITEM.searchItemsByItemIds", map);
	}

	/**
	 * 根据订单子子项ID查询订单结算项
	 * 
	 * @param orderItemMetaId
	 *            订单子子项ID
	 * @return 订单结算项
	 */
	public SetSettlementItem searchItemByOrderItemMetaId(Long orderItemMetaId) {
		return (SetSettlementItem) super.queryForObject("SET_SETTLEMENT_ITEM.searchItemByOrderItemMetaId", orderItemMetaId);
	}

	/**
	 * 根据 订单结算项ID查询订单结算项
	 * 
	 * @param settlementItemId
	 *            订单结算项ID
	 * @return 订单结算项
	 */
	public SetSettlementItem searchItemBySettlementItemId(Long settlementItemId) {
		return (SetSettlementItem) super.queryForObject("SET_SETTLEMENT_ITEM.searchItemBySettlementItemId", settlementItemId);
	}

	/**
	 * 新增订单结算项
	 * 
	 * @param setSettlementItem
	 *            订单结算项
	 */
	public void insertSettlementItem(SetSettlementItem setSettlementItem) {
		super.insert("SET_SETTLEMENT_ITEM.insertSettlementItem", setSettlementItem);
	}

	/**
	 * 更新订单结算项
	 * 
	 * @param setSettlementItem
	 *            订单结算项
	 * 
	 * @return 更新影响的行数
	 */
	public Integer updateSettlementItem(SetSettlementItem setSettlementItem) {
		if(null==setSettlementItem.getSettlementItemId() && null==setSettlementItem.getOrderId()){
			return 0;
		}
		return super.update("SET_SETTLEMENT_ITEM.updateSettlementItem", setSettlementItem);
	}

	/**
	 * 根据结算单号查询订单子子项项ID
	 * 
	 * @param settlementId
	 *            结算单ID
	 * @return
	 */
	public List<Long> searchOrderItemMetaIdsBySettlementId(long settlementId) {
		return super.queryForList("SET_SETTLEMENT_ITEM.searchOrderItemMetaIdsBySettlementId", settlementId);
	}

	/**
	 * 根据结算单号查询导出的结算项数据
	 * 
	 * @param settlementIds
	 *            结算单ID
	 * @return
	 */
	public List<SetSettlementItem> searchItemExcelData1(List<Long> settlementIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("settlementIds", settlementIds);
		return super.queryForListForReport("SET_SETTLEMENT_ITEM.searchItemExcelData1", map);
	}

	/**
	 * 查询结算单的订单明细
	 * 
	 * @param searchParameter
	 * @return
	 */
	public Page<SetSettlementItem> searchItemDetailList(Map<String, Object> searchParameter) {
		return super.queryForPage("SET_SETTLEMENT_ITEM.searchItemDetailList", searchParameter);
	}

	/**
	 * 查询结算总价
	 * 
	 * @param searchParameter
	 *            查询参数
	 * @return
	 */
	public Long searchSumprice(Map<String, Object> searchParameter) {
		return (Long) super.queryForObject("SET_SETTLEMENT_ITEM.searchSumprice", searchParameter);
	}

	/**
	 * 查询导出结算单的订单明细
	 * 
	 * @param searchParameter
	 *            查询参数
	 */
	public List<SetSettlementItem> searchItemExcelData2(Map<String, Object> searchParameter) {
		return super.queryForListForReport("SET_SETTLEMENT_ITEM.searchItemExcelData2", searchParameter);
	}

	/**
	 * 根据订单号查询订单结算项
	 * 
	 * @param orderId
	 *            订单号
	 * @param 结算单类型
	 *            ORDER OR GROUP   
	 * @return
	 */
	public List<SetSettlementItem> searchListByOrderId(Long orderId, Long targetId,String settlementType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("targetId", targetId);
		map.put("settlementType", settlementType);
		return super.queryForList("SET_SETTLEMENT_ITEM.searchListByOrderId", map);
	}

	/**
	 * 根据结算单ID，采购产品ID修改实际结算价
	 * 
	 * @param metaProductId
	 *            采购产品ID
	 * @param settlementId
	 *            结算单ID
	 * @param settlementPrice
	 *            结算价
	 */
	public void updateSettlementPriceByMetaProductId(Long metaProductId, Long settlementId, Long settlementPrice) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("settlementId", settlementId);
		map.put("metaProductId", metaProductId);
		map.put("settlementPrice", settlementPrice);
		super.update("SET_SETTLEMENT_ITEM.updateSettlementPriceByMetaProductId",map);
	}

	/**
	 * 根据订单结算项ID查询订单子子项ID
	 * 
	 * @param settlementItemIds
	 *            订单结算项ID
	 * @return
	 */
	public List<Long> searchOrderItemMetaIdsBySettlementItemId(List<Long> settlementItemIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("settlementItemIds", settlementItemIds);
		return super.queryForList("SET_SETTLEMENT_ITEM.searchOrderItemMetaIdsBySettlementItemId",map);
	}
	/**
	 * 根据订单子子项ID查询结算单打款金额(针对老系统)
	 * @param orderItemMetaId 订单子子项ID
	 * @return 打款金额
	 */
	public Long searchSettlementPayByOrderItemMetaId(Long orderItemMetaId) {
		return (Long)queryForObject("SET_SETTLEMENT_ITEM.searchSettlementPayByOrderItemMetaId",orderItemMetaId);
	}
	
	/**
	 * 根据订单子子项ID查询结算单打款金额(针对新系统 )
	 * @param orderItemMetaId 订单子子项ID
	 * @return 打款金额
	 */
	public Long getSettlementPayAmount(Long orderItemMetaId,String businessName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderItemMetaId", orderItemMetaId);
		map.put("businessName", businessName);
		return (Long)queryForObject("SET_SETTLEMENT_ITEM.getSettlementPayAmount",map);
	}
	
	
}
