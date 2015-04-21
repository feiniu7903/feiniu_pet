package com.lvmama.comm.pet.po.shop;

import java.io.Serializable;

public class ShopProductCoupon extends ShopProduct implements Serializable {
	private static final long serialVersionUID = 1132482464359447671L;
	private Long productCouponId;
	private Long couponId;
	
	public Long getProductCouponId() {
		return productCouponId;
	}
	public void setProductCouponId(Long productCouponId) {
		this.productCouponId = productCouponId;
	}
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
}
