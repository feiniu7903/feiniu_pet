package com.lvmama.comm.pet.vo.mark;

import java.io.Serializable;
import java.util.List;

import com.lvmama.comm.pet.po.mark.MarkCoupon;

public class ProductCoupon implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1379543440070145548L;
	public String uuid;
	public List<MarkCoupon> couponList;
	public String productName;
	public Long productId;
	private Long couponProductId;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public List<MarkCoupon> getCouponList() {
		return couponList;
	}
	public void setCouponList(List<MarkCoupon> couponList) {
		this.couponList = couponList;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Long getCouponProductId() {
		return couponProductId;
	}
	public void setCouponProductId(Long couponProductId) {
		this.couponProductId = couponProductId;
	}
}
