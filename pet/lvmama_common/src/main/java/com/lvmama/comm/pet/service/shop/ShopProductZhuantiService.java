package com.lvmama.comm.pet.service.shop;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.shop.ShopProductZhuanti;

public interface ShopProductZhuantiService {

	/**
	 * 插入
	 * @param ShopProductZhuanti 
	 * @return 表识
	 */
	 Long insert(final ShopProductZhuanti ShopProductZhuanti);
	
	/**
	 * 根据给定参数查询订单
	 * @param parameters 查询参数
	 * @return 列表
	 */
	 List<ShopProductZhuanti> queryList(final Map<String, Object> parameters);
  
	
	 ShopProductZhuanti query(final Map<String, Object> parameters);
	  
	/**
	 * 根据条件删除记录
	 * @param parameters
	 */
	void delete(final Map<String, Object> parameters);

	/**
	 * 根据KEY删除记录
	 * @param parameters
	 */
	void deleteByKey(final long shopProductZhuantiId);

}
