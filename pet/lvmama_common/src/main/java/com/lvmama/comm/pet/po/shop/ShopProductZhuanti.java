package com.lvmama.comm.pet.po.shop;

import java.io.Serializable;

public class ShopProductZhuanti  implements Serializable{
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -3876914328178719393L;
	 
	/**
	 * 标识
	 */
	private Long shopProductZhuantiId;
	/**
	 * 产品标识
	 */
	private Long productId;
	/**
	 *专题优惠用户编号
	 */
	private String userId;
	/**
	 * 专题优惠百分比
	 */
	private Long changeRate;
	
	public Long getShopProductZhuantiId() {
		return shopProductZhuantiId;
	}
	public void setShopProductZhuantiId(Long shopProductZhuantiId) {
		this.shopProductZhuantiId = shopProductZhuantiId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getChangeRate() {
		return changeRate;
	}
	public void setChangeRate(Long changeRate) {
		this.changeRate = changeRate;
	}
	
}
