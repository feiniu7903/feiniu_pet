package com.lvmama.order.dao.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaPrice;
import com.lvmama.comm.bee.po.ord.OrdSettlementPriceRecord;

/**
 * 修改结算价DAO
 * @author zhangwenjun
 *
 */
public class ModifySettlementPriceDAO extends BaseIbatisDAO {
	
	public Integer selectRowCount(Map<String, Object> searchConds){
		Integer count = 0;
		count = (Integer) this.queryForObject("ORD_ORDER_ITEM_META_PRICE.selectPriceRowCount",searchConds);
		return count;
	}

	public List<OrdOrderItemMetaPrice> selectByParms(Map<String, Object> map) {
		return this.queryForList("ORD_ORDER_ITEM_META_PRICE.selectPriceListByParms", map);
	}
	
	public Integer queryHistoryRecordCount(Map<String, Object> searchConds){
		Integer count = 0;
		count = (Integer) this.queryForObject("ORD_ORDER_ITEM_META_PRICE.queryHistoryRecordCount",searchConds);
		return count;
	}

	public List<OrdOrderItemMetaPrice> queryHistoryRecordList(Map<String, Object> map) {
		return this.queryForList("ORD_ORDER_ITEM_META_PRICE.queryHistoryRecordList", map);
	}

	public List<OrdOrderItemMetaPrice> exportHistoryRecordList(Map<String, Object> map) {
		return this.queryForList("ORD_ORDER_ITEM_META_PRICE.exportHistoryRecordList", map);
	}
	
	public boolean updateSettlementPrice(OrdOrderItemMetaPrice ordOrderItemMetaPrice) {
		Integer result = this.update("ORD_ORDER_ITEM_META_PRICE.updateSettlementPrice", ordOrderItemMetaPrice);
		if(result > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean insertOrdSettlementPriceRecord(OrdSettlementPriceRecord ordSettlementPriceRecord) {
		try{
			this.insert("ORD_ORDER_ITEM_META_PRICE.insertOrdSettlementPriceRecord", ordSettlementPriceRecord);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public OrdOrderItemMetaPrice selectByPrimaryKey(final Long ordOrderItemMetaId) {
		OrdOrderItemMeta key = new OrdOrderItemMeta();
		key.setOrderItemMetaId(ordOrderItemMetaId);
		OrdOrderItemMetaPrice orderItemMeta = (OrdOrderItemMetaPrice) this.queryForObject("ORD_ORDER_ITEM_META_PRICE.selectByPrimaryKey", key);
		return orderItemMeta;
	}
	
	public Integer queryVerifyListCount(Map<String, Object> searchConds){
		Integer count = 0;
		count = (Integer) this.queryForObject("ORD_ORDER_ITEM_META_PRICE.queryVerifyListCount",searchConds);
		return count;
	}

	public List<OrdOrderItemMetaPrice> queryVerifyList(Map<String, Object> map) {
		return this.queryForList("ORD_ORDER_ITEM_META_PRICE.queryVerifyList", map);
	}

	public int doVerify(Map<String, Object> map) {
		return this.update("ORD_ORDER_ITEM_META_PRICE.updateHistoryRecordStatus", map);
	}
	
	public OrdSettlementPriceRecord queryHistoryRecordById(Long recordId) {
		return (OrdSettlementPriceRecord)this.queryForObject("ORD_ORDER_ITEM_META_PRICE.queryHistoryRecordById", recordId);
	}
	
	public int updateHistoryRecordById(Map<String, Object> map) {
		return this.update("ORD_ORDER_ITEM_META_PRICE.updateHistoryRecordById", map);
	}

	public Integer queryRefundmentByMetaId(Long orderItemMetaId) {
		return (Integer) this.queryForObject("ORD_ORDER_ITEM_META_PRICE.queryRefundmentByMetaId", orderItemMetaId);
	}
	
	public OrdOrderItemMeta selectByOrderItemMetaId(Long orderItemId) {
		OrdOrderItemMeta orderItemMeta = new OrdOrderItemMeta();
		orderItemMeta.setOrderItemMetaId(orderItemId);
		return (OrdOrderItemMeta) super.queryForObject("ORDER_ITEM_META.selectByPrimaryKey", orderItemMeta);
	}

	public Integer searchUnverifiedRecord(Long orderItemMetaId) {
		return (Integer) this.queryForObject("ORD_ORDER_ITEM_META_PRICE.searchUnverifiedRecord", orderItemMetaId);
	}

}