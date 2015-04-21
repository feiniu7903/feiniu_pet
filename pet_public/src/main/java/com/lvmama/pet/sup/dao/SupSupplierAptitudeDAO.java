package com.lvmama.pet.sup.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.sup.SupSupplierAptitude;

public class SupSupplierAptitudeDAO extends BaseIbatisDAO {

    public SupSupplierAptitudeDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long supplierAptitudeId) {
        SupSupplierAptitude key = new SupSupplierAptitude();
        key.setSupplierAptitudeId(supplierAptitudeId);
        int rows = super.delete("SUP_SUPPLIER_APTITUDE.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(SupSupplierAptitude record) {
        Object newKey = super.insert("SUP_SUPPLIER_APTITUDE.insert", record);
        return (Long) newKey;
    }
 

    public SupSupplierAptitude selectByPrimaryKey(Long supplierAptitudeId) {
        SupSupplierAptitude key = new SupSupplierAptitude();
        key.setSupplierAptitudeId(supplierAptitudeId);
        SupSupplierAptitude record = (SupSupplierAptitude) super.queryForObject("SUP_SUPPLIER_APTITUDE.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(SupSupplierAptitude record) {
        int rows = super.update("SUP_SUPPLIER_APTITUDE.updateByPrimaryKeySelective", record);
        return rows;
    }
    
    public int updateByPrimaryKey(SupSupplierAptitude record) {
        int rows = super.update("SUP_SUPPLIER_APTITUDE.updateByPrimaryKey", record);
        return rows;
    }
 
    public SupSupplierAptitude selectBySupplierId(Long supplierId){
    	SupSupplierAptitude record=new SupSupplierAptitude();
    	record.setSupplierId(supplierId);
    	List<SupSupplierAptitude> list=super.queryForList("SUP_SUPPLIER_APTITUDE.selectBySupplierId",record);
    	if(list.isEmpty()){
    		return null;
    	}else{
    		return list.get(0);
    	}
    }
}