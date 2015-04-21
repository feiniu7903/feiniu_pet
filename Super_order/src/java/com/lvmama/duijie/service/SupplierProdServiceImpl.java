package com.lvmama.duijie.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.duijie.SupplierProd;
import com.lvmama.comm.bee.service.duijie.SupplierProdService;
import com.lvmama.duijie.dao.SupplierProdDAO;

public class SupplierProdServiceImpl implements SupplierProdService{

	private SupplierProdDAO supplierProdDAO; 
	
	@Override
	public Long insert(SupplierProd prod) {
		return supplierProdDAO.insert(prod);
	}
	
	@Override
	public void update(SupplierProd prod) {
		supplierProdDAO.update(prod);
	}
	
	@Override
	public List<SupplierProd> selectSupplierProd(Map<String, Object> params) {
		return supplierProdDAO.selectSupplierProd(params);
	}
	
	@Override
	public Long selectBySupplierProdCount(Map<String, Object> params) {
		return supplierProdDAO.selectBySupplierProdCount(params);
	}
	
	@Override
	public Long getProductIdByCondition(Map<String, Object> params) {
		return supplierProdDAO.getProductIdByCondition(params);
	}

	public void setSupplierProdDAO(SupplierProdDAO supplierProdDAO) {
		this.supplierProdDAO = supplierProdDAO;
	}


}
