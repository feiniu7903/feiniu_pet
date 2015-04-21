package com.lvmama.comm.vo;

import java.io.Serializable;
import java.util.List;

import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.mark.MarkCouponProduct;

public class UserCouponDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8671770986067328177L;
	private MarkCoupon markCoupon;
	private MarkCouponCode markCouponCode;
	private List<MarkCouponProduct> productTypes;
	private Float amountYuan;//优惠金额
	private Long orderId;
	public MarkCoupon getMarkCoupon() {
		return markCoupon;
	}
	public void setMarkCoupon(MarkCoupon markCoupon) {
		this.markCoupon = markCoupon;
	}
	public MarkCouponCode getMarkCouponCode() {
		return markCouponCode;
	}
	public void setMarkCouponCode(MarkCouponCode markCouponCode) {
		this.markCouponCode = markCouponCode;
	}
	public List<MarkCouponProduct> getProductTypes() {
		return productTypes;
	}
	public void setProductTypes(List<MarkCouponProduct> productTypes) {
		this.productTypes = productTypes;
	}
	public Float getAmountYuan() {
		return amountYuan;
	}
	public void setAmountYuan(Float amountYuan) {
		this.amountYuan = amountYuan;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

}
