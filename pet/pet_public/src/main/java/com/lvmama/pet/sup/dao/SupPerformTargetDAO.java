package com.lvmama.pet.sup.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;


public class SupPerformTargetDAO extends BaseIbatisDAO{

	public Long insert(SupPerformTarget record) {
		return (Long)super.insert("SUP_PERFORM_TARGET.insert", record);
	}
	
	public SupPerformTarget selectByPrimaryKey(Long targetId) {
		SupPerformTarget key = new SupPerformTarget();
		key.setTargetId(targetId);
		SupPerformTarget record = (SupPerformTarget) super.queryForObject("SUP_PERFORM_TARGET.selectByPrimaryKey", key);
		return record;
	}

	public int updateByPrimaryKey(SupPerformTarget record) {
		int rows = super.update("SUP_PERFORM_TARGET.updateByPrimaryKey", record);
		return rows;
	}

	public List<SupPerformTarget> findSupPerformTarget(Map param) {
		if (param.get("_startRow")==null) {
			param.put("_startRow", 0);
		}
		if (param.get("_endRow")==null) {
			param.put("_endRow", 20);
		}
		List<SupPerformTarget> result = new ArrayList<SupPerformTarget>();
		result = super.queryForList("SUP_PERFORM_TARGET.selectByParams", param);
		return result;
	}
	
	/**
	 * 通过供应商ID拿到全部履行对象
	 * @param param
	 * @return
	 */
	public List<SupPerformTarget> findAllSupPerformTarget(Map param) {
		if (param.get("_startRow")==null) {
			param.put("_startRow", 0);
		}
		if (param.get("_endRow")==null) {
			param.put("_endRow", 100);
		}
		List<SupPerformTarget> result = new ArrayList<SupPerformTarget>();
		result = super.queryForList("SUP_PERFORM_TARGET.selectByParams", param);
		return result;
	}
	 
	public void markIsValid(Map params) {
		super.update("SUP_PERFORM_TARGET.markIsValid", params);
	}

	public Integer selectRowCount(Map searchConds){
		Integer count = 0;
		count = (Integer) super.queryForObject("SUP_PERFORM_TARGET.selectRowCount",searchConds);
		return count;
	}
	
	public int deleteByPrimaryKey(Long performTargetId){
		SupPerformTarget key = new SupPerformTarget();
		key.setTargetId(performTargetId);
		int rows = super.delete("SUP_PERFORM_TARGET.deleteByPrimaryKey", key);
		return rows;
	}
	 
	
	/**
	 * 通过产品编号查询履行对象
	 * @param metaProductId
	 * @return
	 */
	public List<SupPerformTarget> findSupPerformTargetByMetaProductId(Long metaProductId,String bizType) {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("metaProductId", metaProductId);
		p.put("bizType", bizType);
		return super.queryForList("SUP_PERFORM_TARGET.selectMetaPerformByMetaProductId", p);
	}
}