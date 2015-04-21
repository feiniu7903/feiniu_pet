package com.lvmama.pet.shop.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.po.shop.ShopProductCondition;
import com.lvmama.comm.pet.po.shop.ShopProductCoupon;
import com.lvmama.comm.vo.Constant;

/**
 * 积分商城产品的数据库操作实现类
 * @author Brian
 *
 */
public class ShopProductDAO extends BaseIbatisDAO {

	/**
	 * 查询积分商城的产品
	 * @param parameters 参数
	 * @return 产品列表
	 */
	@SuppressWarnings("unchecked")
	public List<ShopProduct> query(final Map<String, Object> parameters) {
		return super.queryForList("SHOP_PRODUCT.query", parameters);
	}

	/**
	 * 查询积分商城的产品数
	 * @param parameters 参数
	 * @return 产品数
	 */
	public 	Long count(final Map<String, Object> parameters) {
		return (Long) super.queryForObject("SHOP_PRODUCT.count", parameters);
	}

	/**
	 * 根据产品标识查询产品
	 * @param id 标识
	 * @return 产品
	 */
	public ShopProduct queryByPk(final Long id) {
		return (ShopProduct) super.queryForObject("SHOP_PRODUCT.queryByPk", id);
	}

	/**
	 * 根据产品标识查询详细的产品
	 * @param id 标识
	 * @return 详细的产品
	 */	
	public ShopProduct queryDetailByPk(final Long id) {
		ShopProduct p = (ShopProduct) super.queryForObject("SHOP_PRODUCT.queryByPk", id);
		if (null != p && Constant.SHOP_PRODUCT_TYPE.COUPON.name().equals(p.getProductType())) {
			return (ShopProductCoupon) super.queryForObject("SHOP_PRODUCT.queryCouponByPk", id);
		}
		return p;
	}

	/**
	 * 保存产品信息
	 * @param product 产品
	 * @return 产品标识
	 */
	public Long insert(final ShopProduct product) {
		super.insert("SHOP_PRODUCT.insert", product);
		return product.getProductId();
	}

	/**
	 * 保存优惠券类的产品信息
	 * @param product 产品
	 * @return 产品标识
	 */
	public Long insert(final ShopProductCoupon couponProduct) {
		super.insert("SHOP_PRODUCT.insert", couponProduct);
		super.insert("SHOP_PRODUCT.insertCouponProduct", couponProduct);
		return couponProduct.getProductId();
	}

	/**
	 * 更新产品信息
	 * @param product 产品
	 * @return 产品标识
	 */
	public Long update(final ShopProduct product) {
		super.update("SHOP_PRODUCT.update", product);
		return product.getProductId();
	}

	/**
	 * 更新产品信息
	 * @param product 产品
	 * @return 产品标识
	 */
	public Long update(final ShopProductCoupon couponProduct) {
		super.update("SHOP_PRODUCT.update", couponProduct);
		if (null == queryDetailByPk(couponProduct.getProductId())) {
			super.insert("SHOP_PRODUCT.insertCouponProduct", couponProduct);
		} else {
			super.update("SHOP_PRODUCT.updateCouponProduct", couponProduct);
		}
		return couponProduct.getProductId();
	}

	/**
	 * 减少库存
	 * @param parameters 参数
	 */
	public void reduceStocks(final Map<String, Object> parameters) {
		super.update("SHOP_PRODUCT.reduceStocks", parameters);
	}
	
	/**
	 * 取出兑换条件集合
	 * @param productId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ShopProductCondition> getShopProductConditionByShopProductId(Long productId){
		return queryForList("SHOP_PRODUCT.getShopProductConditionByShopProductId",productId);
	}
	
	/**
	 * 保存兑换条件
	 * @param entity
	 * @return
	 */
	public ShopProductCondition insertShopProductCondition(ShopProductCondition entity){
		super.insert("SHOP_PRODUCT.insertShopProductCondition",entity);
		return entity;
	}
	/**
	 * 根据ShopProduct主键全部删除兑换条件
	 * @param productId
	 */
	public void deleteShopProductConditionByProductId(Long productId){
		super.delete("SHOP_PRODUCT.deleteShopProductConditionByProductId", productId);
	}
}
