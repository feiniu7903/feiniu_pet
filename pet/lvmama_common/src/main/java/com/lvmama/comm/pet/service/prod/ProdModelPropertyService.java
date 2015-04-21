package com.lvmama.comm.pet.service.prod;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.prod.ProdModelProperty;

/**
 * 产品模块属性服务接口
 * @author ganyingwen
 *
 */
public interface ProdModelPropertyService {
	/**
	 * 插入
	 * @param productModelProperty productModelProperty对象
	 * @return productModelProperty对象
	 */
	ProdModelProperty insert(ProdModelProperty productModelProperty);
	
	/**
	 * 更新
	 * @param productModelProperty 对象
	 * @return 更新行数
	 */
	int update(ProdModelProperty productModelProperty);
	
	/**
	 * 查询
	 * @param params 查询参数
	 * @return ProductModelProperty对象列表
	 */
	List<ProdModelProperty> select(Map<String, Object> params);
	
	/**
	 * 查询数量
	 * @param params 查询参数
	 * @return 数量
	 */
	Long count(Map<String, Object> params);
	
	List<ProdModelProperty> getProductModelPropertyByProductType(String subProductType);
}
