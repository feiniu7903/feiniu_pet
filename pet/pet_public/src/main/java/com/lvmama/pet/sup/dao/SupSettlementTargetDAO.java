package com.lvmama.pet.sup.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;


public class SupSettlementTargetDAO extends BaseIbatisDAO {

	public Long insert(SupSettlementTarget record) {
		Object key = super.insert("SUP_SETTLEMENT_TARGET.insert", record);
		
		if(key == null)
			return null;
		else
			return (Long) key;
	}

	public int updateByPrimaryKey(SupSettlementTarget record) {
		int rows = super.update("SUP_SETTLEMENT_TARGET.updateByPrimaryKey", record);
		return rows;
	}
	
	public int updateMemoByPrimaryKey(Map<String, String> params) {
		int rows = super.update("SUP_SETTLEMENT_TARGET.updateMemoByPrimaryKey", params);
		return rows;
	}

	public List<SupSettlementTarget> findSupSettlementTarget(Map param) {
		if (param.get("_startRow")==null) {
			param.put("_startRow", 0);
		}
		if (param.get("_endRow")==null) {
			param.put("_endRow", 20);
		}
		return super.queryForList("SUP_SETTLEMENT_TARGET.findSupSettlementTarget", param);
	}

	public SupSettlementTarget getSettlementTargetById(long targetId) {
		return (SupSettlementTarget) super.queryForObject("SUP_SETTLEMENT_TARGET.selectByPrimaryKey", targetId);
	}
	public void markIsValid(Map params) {
		super.update("SUP_SETTLEMENT_TARGET.markIsValid", params);
	}
	
	public Integer selectRowCount(Map searchConds){
		Integer count = 0;
		count = (Integer) super.queryForObject("SUP_SETTLEMENT_TARGET.selectRowCount",searchConds);
		return count;
	}

	public List<SupSettlementTarget> getMetaSettlementByMetaProductId(Long metaProductId,String bizType) {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("metaProductId", metaProductId);
		p.put("bizType", bizType);
		return super.queryForList("SUP_SETTLEMENT_TARGET.selectMetaSettlementByMetaProductId", p);
	}
	
	public SupSettlementTarget getSupSettlementTargetBySupplierId(Long supplierId){
		List<SupSettlementTarget> list = super.queryForList("SUP_SETTLEMENT_TARGET.getSupSettlementTargetBySupplierId",supplierId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public List<SupSettlementTarget> selectSupSettlementTargetByName(String search , Integer size){
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("search", search);
		p.put("size", size);
		return super.queryForList("SUP_SETTLEMENT_TARGET.selectSupSettlementTargetByName", p);
	}
}