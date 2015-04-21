package com.lvmama.comm.bee.service.pass;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.pass.PassProduct;

public interface PassProductService {

	/**
	 * 新增一个通关产品
	 * @param passProduct
	 */
	void insertPassProduct(PassProduct passProduct);
	
	/**
	 * 删除一个通关产品
	 * @param passProduct
	 */
	void deletePassProduct(Long passProdId);
	
	/**
	 * 修改通关产品
	 * @param passProduct
	 */
	void updatePassProduct(PassProduct passProduct);
	
	/**
	 * 查询符合条件的产品
	 * @param params
	 */
	@SuppressWarnings("unchecked")
	List<PassProduct> queryPassProduct(Map<String,Object> params);
	
	/**
	 * 
	 * @param queryOption
	 * @return
	 */
	Integer selectPassProductRowCount(Map<String, Object> queryOption);
	
	/**
	 * 按条件查询
	 * 
	 * @param 查询参数
	 */
	PassProduct selectPassProductByParams(Map<String, Object> params);
}
