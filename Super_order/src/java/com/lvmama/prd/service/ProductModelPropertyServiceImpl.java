package com.lvmama.prd.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.ProductModelProperty;
import com.lvmama.comm.bee.service.prod.ProductModelPropertyService;
import com.lvmama.prd.dao.ProductModelPropertyDAO;

public class ProductModelPropertyServiceImpl implements ProductModelPropertyService {
	private ProductModelPropertyDAO productModelPropertyDAO;

	@Override
	public ProductModelProperty insert(ProductModelProperty productModelProperty) {
		return productModelPropertyDAO.insert(productModelProperty);
	}

	@Override
	public int update(ProductModelProperty productModelProperty) {
		return productModelPropertyDAO.update(productModelProperty);
	}

	@Override
	public List<ProductModelProperty> select(Map<String, Object> params) {
		return productModelPropertyDAO.select(params);
	}

	@Override
	public List<ProductModelProperty> getProductModelPropertyByProductType(
			String subProductType) {
		
		return null;
	}
	@Override
	public Long count(Map<String, Object> params) {
		return productModelPropertyDAO.count(params);
	}

	public void setProductModelPropertyDAO(
			ProductModelPropertyDAO productModelPropertyDAO) {
		this.productModelPropertyDAO = productModelPropertyDAO;
	}

	
}
