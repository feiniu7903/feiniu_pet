package com.lvmama.comm.pet.service.shop;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.shop.ShopCooperationCoupon;

/**
 * 积分商城合作网站优惠券的逻辑接口
 * @author Brian
 *
 */
public interface ShopCooperationCouponService {
	
	/**
	 * 批量插入优惠券
	 * @param list
	 * @return
	 */
	public int batchInsertCoupon(final List<ShopCooperationCoupon> list,final boolean isClean,final Long productId,final String operatorId);
	
	/**
	 * 根据条件批量删除优惠券
	 * @param parameters
	 * @return
	 */
	public int batchDeleteCoupon(final Map<String,Object> parameters);
	
	/**
	 * 根据条件查询优惠券列表
	 * @param parameters
	 * @return
	 */
	public List<ShopCooperationCoupon> query(final Map<String,Object> parameters);
	
	/**
	 * 根据条件查询优惠券列表总数
	 * @param parameters
	 * @return
	 */
	public Long count(final Map<String,Object> parameters);
	
	/**
	 * 根据条件查询单条优惠券
	 * @param parameters
	 * @return
	 */
	public ShopCooperationCoupon queryByParameters(final Map<String,Object> parameters);
	
	/**
	 * 根据主键查询单条优惠券
	 * @param key
	 * @return
	 */
	public ShopCooperationCoupon queryCouponByKey(final Long key);
	
	/**
	 * 根据产品编号取出合作网站优惠券信息，并把它设置为已使用
	 * @param productId
	 * @return 优惠券信息
	 */
	public List<String> subtractStock(final Long productId,final int quantity);
}
