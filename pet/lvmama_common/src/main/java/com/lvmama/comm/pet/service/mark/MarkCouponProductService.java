package com.lvmama.comm.pet.service.mark;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.mark.MarkCouponProduct;

/**
 * 订单优惠批次和产品对应关系的逻辑接口
 * @author Administrator
 *
 */
public interface MarkCouponProductService {
	/**
	 * 根据查询条件统计优惠券和产品对应关系的条目数
	 * @param param 查询条件
	 * @return 条目数
	 */
	Long selectMarkCouponProductRowCount(Map<String,Object> param);
	
	/**
	 * 根据查询条件返回所有符合条件的优惠券和产品对应关系
	 * @param param 查询条件
	 * @return 优惠券和产品对应关系列表
	 */
	List<MarkCouponProduct> selectMarkCouponProductByParam(Map<String,Object> param);
	
	/**
	 * 根据PK条件返回优惠券和产品对应关系
	 * @param  couponProductId(PK)
	 * @return 优惠券和产品对应关系列表
	 */
	MarkCouponProduct selectMarkCouponProdByPK(Long couponProductId);
	
	/**
	 * 根据优惠ID(couponId)条件返回所有符合条件的优惠券和产品对应关系
	 * @param param 查询条件
	 * @return 优惠券和产品对应关系列表
	 */
	List<MarkCouponProduct> selectMarkCouponProdByCouponId(Long couponId);
	
	/**
	 * 插入优惠券和产品对应关系
	 * @param markCouponProduct
	 */
	Long insert(MarkCouponProduct markCouponProduct);
	
	/**
	 * 删除优惠券和产品对应关系
	 * @param markCouponProduct
	 */
	void delete(MarkCouponProduct markCouponProduct);
	
	/**
	 * 更新优惠券和产品对应关系
	 * @param markCouponProduct
	 */
	void update(MarkCouponProduct markCouponProduct);
	
	
	/**
	 * 返回合适的优惠产品对应关系
	 * @param markCouponId 优惠批次标识
	 * @param productId 销售产品标识
	 * @param subProductType 销售产品子类型
	 * @return
	 */
	MarkCouponProduct getSuitableMarkCouponProduct(Long markCouponId, Long productId, String subProductType);
	
	
	/**
	 * 对于同一优惠活动,检查同一产品是否同时绑定了产品ID和产品子类型.
	 * <br/>1:绑定产品ID时，检查此产品所属的产品子类型是否已绑定当前活动.
	 * <br/>2.绑定产品子类型时，检查属于此产品子类型的产品ID是否已绑定当前活动.
	 * @param mcp MarkCouponProduct对象.
	 * @return 返回ProductId列表或产品子类型.
	 */
	String checkProductIdOrSubProductTypeAgainBound(MarkCouponProduct mcp);
	
	/**
	 * 批量保存优惠和产品的关系
	 * @param couponId
	 * @param productId
	 */
	void saveProductCoupon(Long couponId, List<Long> productIdList);
	
	/**
	 * 删除优惠和产品的关系
	 * @param parameters
	 */
	void deleteMarkCouponProdByMap(Map<String,Object> parameters);
	
}
