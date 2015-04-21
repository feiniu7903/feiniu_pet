package com.lvmama.comm.bee.vo.view;

import java.io.Serializable;

import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;


public class MarkCouponUserInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -610216672553112301L;
	private MarkCoupon markCoupon;
	private MarkCouponCode markCouponCode;
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
}
