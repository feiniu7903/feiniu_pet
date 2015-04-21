package com.lvmama.duijie.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.duijie.SupplierViewContent;
import com.lvmama.comm.bee.service.duijie.SupplierViewContentService;
import com.lvmama.duijie.dao.SupplierViewContentDAO;

public class SupplierViewContentServiceImpl implements SupplierViewContentService{
	
	private SupplierViewContentDAO supplierViewContentDAO;
	
	@Override
	public void insert(SupplierViewContent content) {
		supplierViewContentDAO.insert(content);	
	}
	
	@Override
	public void update(SupplierViewContent content) {
		supplierViewContentDAO.update(content);
	}

	@Override
	public List<SupplierViewContent> selectSupplierViewContentByCondition(Map<String,Object> params) {
		return supplierViewContentDAO.selectSupplierViewContentByCondition(params);
	}

	public void setSupplierViewContentDAO(
			SupplierViewContentDAO supplierViewContentDAO) {
		this.supplierViewContentDAO = supplierViewContentDAO;
	}
	
}
