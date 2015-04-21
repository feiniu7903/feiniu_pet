package com.lvmama.clutter.model;

import java.io.Serializable;

/**
 * 积分兑换优惠券模型.
 * @author qinzubo
 *
 */
public class MobileCouponPoint implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5749024738449710615L;
	
	/**
	 *  订单填写页面 积分兑换优惠券信息 
	 */
	private String title;
	
	/**
	 * 优惠券id
	 */
	private Long couponId = 0l;;
	
	/**
	 * 用户积分
	 */
	private Long userPoint= 0l;
	/**
	 * 兑换优惠券所需积分 
	 */
	private Long needPoint= 0l;
	
	/**
	 * 可兑换优惠券金额  单位元 
	 */
	private Float couponYuan= 0f;
	
	

	public Long getUserPoint() {
		return userPoint;
	}

	public void setUserPoint(Long userPoint) {
		this.userPoint = userPoint;
	}

	public Float getCouponYuan() {
		return couponYuan;
	}

	public void setCouponYuan(Float couponYuan) {
		this.couponYuan = couponYuan;
	}
	public Long getNeedPoint() {
		return needPoint;
	}

	public void setNeedPoint(Long needPoint) {
		this.needPoint = needPoint;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}


}
