package com.lvmama.pet.shop.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.shop.ShopProductZhuanti;
import com.lvmama.comm.pet.service.shop.ShopProductZhuantiService;
import com.lvmama.pet.shop.dao.ShopProductZhuantiDAO;

public class ShopProductZhuantiServiceImpl  implements ShopProductZhuantiService {
	/**
	 * 积分商城产品数据库操作层
	 */
	private ShopProductZhuantiDAO shopProductZhuantiDAO;
	  
	/**
	 * 插入
	 * @param ShopProductZhuanti 
	 * @return 表识
	 */
	public Long insert(final ShopProductZhuanti ShopProductZhuanti){
		return shopProductZhuantiDAO.insert(ShopProductZhuanti);
	}
	
	/**
	 * 根据给定参数查询订单
	 * @param parameters 查询参数
	 * @return 列表
	 */
	public ShopProductZhuanti query(final Map<String, Object> parameters){
		List<ShopProductZhuanti> list = shopProductZhuantiDAO.query(parameters);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据给定参数查询订单
	 * @param parameters 查询参数
	 * @return 列表
	 */
	public List<ShopProductZhuanti> queryList(final Map<String, Object> parameters){
		return shopProductZhuantiDAO.query(parameters);
	}
	
	/**
	 * 根据KEY删除记录
	 * @param parameters
	 */
	public void deleteByKey(final long shopProductZhuantiId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("shopProductZhuantiId", shopProductZhuantiId);
		shopProductZhuantiDAO.delete(parameters);
	}
	
	/**
	 * 根据KEY删除记录
	 * @param parameters
	 */
	public void delete(final Map<String, Object> parameters) {
		shopProductZhuantiDAO.delete(parameters);
	}
	
	
	public void setShopProductZhuantiDAO(ShopProductZhuantiDAO shopProductZhuantiDAO) {
		this.shopProductZhuantiDAO = shopProductZhuantiDAO;
	}
}
