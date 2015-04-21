package com.lvmama.pet.sup.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.sup.SupSupplierAssess;

public class SupSupplierAssessDAO extends BaseIbatisDAO {

    public SupSupplierAssessDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long supplierAssessId) {
        SupSupplierAssess key = new SupSupplierAssess();
        key.setSupplierAssessId(supplierAssessId);
        int rows = super.delete("SUP_SUPPLIER_ASSESS.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(SupSupplierAssess record) {
        Object newKey = super.insert("SUP_SUPPLIER_ASSESS.insert", record);
        return (Long) newKey;
    }

    public SupSupplierAssess selectByPrimaryKey(Long supplierAssessId) {
        SupSupplierAssess key = new SupSupplierAssess();
        key.setSupplierAssessId(supplierAssessId);
        SupSupplierAssess record = (SupSupplierAssess) super.queryForObject("SUP_SUPPLIER_ASSESS.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(SupSupplierAssess record) {
        int rows = super.update("SUP_SUPPLIER_ASSESS.updateByPrimaryKeySelective", record);
        return rows;
    }
 
    public List<SupSupplierAssess> selectByParam(Map<String,Object> param){
    	if(!param.containsKey("_beginRow")){
    		param.put("_beginRow", 0);
    	}
    	if(!param.containsKey("_endRow")){
    		param.put("_endRow", 20);
    	}
    	return super.queryForList("SUP_SUPPLIER_ASSESS.selectByParam",param);
    }
}