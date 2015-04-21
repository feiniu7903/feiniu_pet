package com.lvmama.pet.sup.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;

public class SupSupplierDAO extends BaseIbatisDAO {

	public int deleteByPrimaryKey(Long supplierId) {
		SupSupplier key = new SupSupplier();
		key.setSupplierId(supplierId);
		int rows = super.delete("SUP_SUPPLIER.deleteByPrimaryKey", key);
		return rows;
	}

	public Long insert(SupSupplier record) {
		Long key = (Long) super.insert("SUP_SUPPLIER.insert", record);
		return key;
	}

	public SupSupplier selectByPrimaryKey(Long supplierId) {
		SupSupplier record = (SupSupplier) super.queryForObject("SUP_SUPPLIER.selectByPrimaryKey", supplierId);
		return record;
	}
	/**
	 * 根据名称查询供应商，取前十条
	 */
	public List<SupSupplier> getSupplierForParent(String supplierName){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("supplierName", supplierName);
		param.put("rownum", 10);
		return super.queryForList("SUP_SUPPLIER.getSupplierForParent",param);
	}
	
	public List<SupSupplier> getSupplierByParam(Map<String,Object> param){
		param.put("rownum", 12);
		return super.queryForList("SUP_SUPPLIER.getSupplierForParent",param);
	}

	public int updateByPrimaryKey(SupSupplier record) {
		int rows = super.update("SUP_SUPPLIER.updateByPrimaryKey", record);
		return rows;
	}

	public Page<SupSupplier> getSupSupplierByParam(Map param,Long pageSize,Long currentPage) {
		Integer count = 0;
		count = (Integer) super.queryForObject("SUP_SUPPLIER.selectRowCount",param);
		Page<SupSupplier> page = Page.page(count, pageSize, currentPage);
		param.put("_startRow",page.getStartRows());
		param.put("_endRow", page.getEndRows());
		page.setItems(super.queryForList("SUP_SUPPLIER.selectSupplier", param)) ;
		return page;
	}
	
	public List<SupSupplier> getSupSuppliers(Map param) {
		if (param.get("_startRow")==null||!StringUtil.isNumber(param.get("_startRow").toString())) {
			param.put("_startRow", 0);
		}
		if (param.get("_endRow")==null||!StringUtil.isNumber(param.get("_endRow").toString())) {
			param.put("_endRow", 20);
		}
		return super.queryForList("SUP_SUPPLIER.selectSupplier", param);
	}
	
	public void markIsValid(Map params) {
		super.update("SUP_SUPPLIER.markIsValid", params);
	}
	
	public Long selectRowCount(Map searchConds){
		Long count = 0L;
		count = (Long) super.queryForObject("SUP_SUPPLIER.selectRowCount",searchConds);
		return count;
	}
	/**
	 * 查询供应商的直接子级供应商名称
	 * @param id
	 * @return
	 */
	public List<SupSupplier> getChildSuppliers(Long id){
		return super.queryForList("SUP_SUPPLIER.getChildSuppliers",id);
	}

	public List<SupSupplier> getSupSupplierBySupplierId(List<Long> list){
		if(list.size()>0){
			return super.queryForList("SUP_SUPPLIER.getSupSupplierBySupplierId",list);
		}
		return new ArrayList<SupSupplier>();
	}
}