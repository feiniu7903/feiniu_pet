package com.lvmama.duijie.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.duijie.SupplierProd;

/**
 * 供应商产品DAO
 * @author yanzhirong
 *
 */
public class SupplierProdDAO extends BaseIbatisDAO{

	public Long insert(SupplierProd prod){
		Long id = (Long)super.insert("SUPPLIER_PROD.insert",prod);
		return id;
	}
	
	public void update(SupplierProd prod){
		super.update("SUPPLIER_PROD.update",prod);
	}

	public Long selectBySupplierProdCount(Map<String,Object> params){
		return (Long)super.queryForObject("SUPPLIER_PROD.selectBySupplierProdCount",params);
	}
	
	public List<SupplierProd> selectSupplierProd(Map<String,Object> params){
		return (List<SupplierProd>)super.queryForList("SUPPLIER_PROD.selectSupplierProd",params);
	}
	
	public Long getProductIdBySupplierProdId(String supplierProdId){
		return (Long)super.queryForObject("SUPPLIER_PROD.getProductIdBySupplierProdId", supplierProdId);
	}
	
	public Long getProductIdByCondition(Map<String,Object> params){
		return (Long)super.queryForObject("SUPPLIER_PROD.getProductIdByCondition",params);
	}
	
}
