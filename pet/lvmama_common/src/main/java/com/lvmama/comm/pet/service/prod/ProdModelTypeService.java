package com.lvmama.comm.pet.service.prod;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.prod.ProdModelType;

/**
 * 产品模块类型服务接口
 * @author ganyingwen
 *
 */
public interface ProdModelTypeService {
	
	/**
	 * 查询
	 * @param params 查询参数
	 * @return ProductModelProperty对象列表
	 */
	List<ProdModelType> select(Map<String, Object> params);
	
	/**
	 * 查询数量
	 * @param params 查询参数
	 * @return 数量
	 */
	Long count(Map<String, Object> params);
}
