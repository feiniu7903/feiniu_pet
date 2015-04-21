package com.lvmama.comm.vo;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.utils.PriceUtil;

public class CouponProduct extends ProdProduct{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3500745972133216423L;
	private Long couponProductId;
	private Long amount;
	public Long getCouponProductId() {
		return couponProductId;
	}
	public void setCouponProductId(Long couponProductId) {
		this.couponProductId = couponProductId;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
	public float getAmountYuan(){
		if (this.amount != null)
			return PriceUtil.convertToYuan(this.amount);
		return 0;
	}
}
