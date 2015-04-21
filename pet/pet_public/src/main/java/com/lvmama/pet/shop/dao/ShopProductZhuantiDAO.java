package com.lvmama.pet.shop.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.shop.ShopProductZhuanti;

/**
 * 积分商城专题零几分用户接口实现
 * @author yuzhizeng
 *
 */
public class ShopProductZhuantiDAO extends BaseIbatisDAO {
	/**
	 * 插入
	 * @param ShopProductZhuanti 
	 * @return 表识
	 */
	public Long insert(final ShopProductZhuanti ShopProductZhuanti) {
		return (Long) super.insert("SHOP_PRODUCT_ZHUANTI.insert", ShopProductZhuanti);
	}

	/**
	 * 根据给定参数查询订单
	 * @param parameters 查询参数
	 * @return 列表
	 */
	@SuppressWarnings("unchecked")
	public List<ShopProductZhuanti> query(final Map<String, Object> parameters) {
		return super.queryForList("SHOP_PRODUCT_ZHUANTI.query", parameters);
	}
  
	/**
	 * 根据条件删除记录
	 * @param parameters
	 */
	public void delete(final Map<String, Object> parameters){
		super.delete("SHOP_PRODUCT_ZHUANTI.deleteByProductIdAndUserId", parameters);
	}
	 
}
