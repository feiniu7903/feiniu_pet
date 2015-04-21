package com.lvmama.comm.pet.po.mark;

import java.io.Serializable;

public class MarkCouponRelateUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1005771116529893134L;
	private Long id;
	private Long userId;
	private Long couponCodeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCouponCodeId() {
		return couponCodeId;
	}

	public void setCouponCodeId(Long couponCodeId) {
		this.couponCodeId = couponCodeId;
	}
}
