package com.lvmama.comm.bee.service.prod;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.ProductModelProperty;

/**
 * 产品模块属性服务接口
 * @author ganyingwen
 *
 */
public interface ProductModelPropertyService {
	/**
	 * 插入
	 * @param productModelProperty productModelProperty对象
	 * @return productModelProperty对象
	 */
	ProductModelProperty insert(ProductModelProperty productModelProperty);
	
	/**
	 * 更新
	 * @param productModelProperty 对象
	 * @return 更新行数
	 */
	int update(ProductModelProperty productModelProperty);
	
	/**
	 * 查询
	 * @param params 查询参数
	 * @return ProductModelProperty对象列表
	 */
	List<ProductModelProperty> select(Map<String, Object> params);
	
	/**
	 * 查询数量
	 * @param params 查询参数
	 * @return 数量
	 */
	Long count(Map<String, Object> params);
	
	List<ProductModelProperty> getProductModelPropertyByProductType(String subProductType);
}
