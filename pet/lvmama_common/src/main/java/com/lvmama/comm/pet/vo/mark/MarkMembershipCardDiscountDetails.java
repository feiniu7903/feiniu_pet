package com.lvmama.comm.pet.vo.mark;

import com.lvmama.comm.pet.po.mark.MarkMembershipCardDiscount;
import com.lvmama.comm.utils.PriceUtil;

public class MarkMembershipCardDiscountDetails extends MarkMembershipCardDiscount {
	private String couponName;
	private String amountDescription;
	
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public String getAmountDescription() {
		return amountDescription;
	}
	public void setAmountDescription(String amountDescription) {
		this.amountDescription = amountDescription;
	}
	
}
