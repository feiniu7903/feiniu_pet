package com.lvmama.prd.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProductModelProperty;

/**
 * 模块属性数据库统一访问接口
 * @author ganyingwen
 *
 */
public class ProductModelPropertyDAO  extends BaseIbatisDAO {
	/**
	 * 插入
	 * @param productModelProperty productModelProperty对象
	 * @return productModelProperty对象
	 */
	public ProductModelProperty insert(ProductModelProperty productModelProperty) {
		super.insert("PROD_MODEL_PROPERTY.insert", productModelProperty);
		return productModelProperty;
	}
	
	/**
	 * 更新
	 * @param params 更新参数值
	 * @return 更新行数
	 */
	public int update(ProductModelProperty productModelProperty) {
		return super.update("PROD_MODEL_PROPERTY.update", productModelProperty);
	}
	
	/**
	 * 查询
	 * @param params 查询参数
	 * @return ProductModelProperty对象列表
	 */
	public List<ProductModelProperty> select(Map<String, Object> params) {
		return super.queryForList("PROD_MODEL_PROPERTY.select", params);
	}
	
	/**
	 * 查询数量
	 * @param params 查询参数
	 * @return 数量
	 */
	public Long count(Map<String, Object> params) {
		return (Long)super.queryForObject("PROD_MODEL_PROPERTY.count", params);
	}
	
	public List<ProductModelProperty> getProductModelPropertyByProductType(
			String subProductType){
		return super.queryForList("PROD_MODEL_PROPERTY.getProductModelPropertyByProductType", subProductType);
	} 
}
