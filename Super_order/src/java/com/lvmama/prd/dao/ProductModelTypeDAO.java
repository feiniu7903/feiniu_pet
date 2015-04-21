package com.lvmama.prd.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProductModelType;

public class ProductModelTypeDAO extends BaseIbatisDAO {
	/**
	 * 查询
	 * @param params 查询参数
	 * @return ProductModelProperty对象列表
	 */
	public List<ProductModelType> select(Map<String, Object> params) {
		return super.queryForList("PROD_MODEL_TYPE.select", params);
	}
	
	/**
	 * 查询数量
	 * @param params 查询参数
	 * @return 数量
	 */
	public Long count(Map<String, Object> params) {
		return (Long)super.queryForObject("PROD_MODEL_PROPERTY.count", params);
	}
}
