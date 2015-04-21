package com.lvmama.comm.pet.vo.mark;

import java.io.Serializable;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.mark.MarkCouponProduct;

public class MarkCouponProductModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1511156120059756812L;
	private String type;
	private MarkCouponProduct markCouponProduct;
	private MarkCoupon coupon;
	private MarkCouponCode markCouponCode;
	private int productCodeUseQuantity;//产品相关的优惠券产品使用的数量
	private ProdProduct product;

	public MarkCoupon getCoupon() {
		return coupon;
	}
	public void setCoupon(MarkCoupon coupon) {
		this.coupon = coupon;
	}
	public ProdProduct getProduct() {
		return product;
	}
	public void setProduct(ProdProduct product) {
		this.product = product;
	}
	public MarkCouponProduct getMarkCouponProduct() {
		return markCouponProduct;
	}
	public void setMarkCouponProduct(MarkCouponProduct markCouponProduct) {
		this.markCouponProduct = markCouponProduct;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public MarkCouponCode getMarkCouponCode() {
		return markCouponCode;
	}
	public void setMarkCouponCode(MarkCouponCode markCouponCode) {
		this.markCouponCode = markCouponCode;
	}
	public int getProductCodeUseQuantity() {
		return productCodeUseQuantity;
	}
	public void setProductCodeUseQuantity(int productCodeUseQuantity) {
		this.productCodeUseQuantity = productCodeUseQuantity;
	}
}
