package com.lvmama.prd.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.ProductModelType;
import com.lvmama.comm.bee.service.prod.ProductModelTypeService;
import com.lvmama.prd.dao.ProductModelTypeDAO;

public class ProductModelTypeServiceImpl implements ProductModelTypeService {
	private ProductModelTypeDAO productModelTypeDAO;

	@Override
	public List<ProductModelType> select(Map<String, Object> params) {
		return productModelTypeDAO.select(params);
	}

	@Override
	public Long count(Map<String, Object> params) {
		return productModelTypeDAO.count(params);
	}

	public void setProductModelTypeDAO(ProductModelTypeDAO productModelTypeDAO) {
		this.productModelTypeDAO = productModelTypeDAO;
	}
}
