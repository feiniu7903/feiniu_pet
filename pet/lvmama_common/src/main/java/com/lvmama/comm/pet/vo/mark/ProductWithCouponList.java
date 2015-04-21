package com.lvmama.comm.pet.vo.mark;

import java.io.Serializable;
import java.util.List;

import com.lvmama.comm.pet.po.mark.MarkCoupon;

public class ProductWithCouponList implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8419081270383114695L;
	private Long  productId;
	private String productName;
	private String shortName;
	private boolean hasCoupon;
	private List<MarkCoupon> couponList;
	
	public List<MarkCoupon> getCouponList() {
		return couponList;
	}
	public void setCouponList(List<MarkCoupon> couponList) {
		this.couponList = couponList;
	}
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
	public boolean isHasCoupon() {
		return couponList.size()>0?true:false;
	}
	public void setHasCoupon(boolean hasCoupon) {
		this.hasCoupon = hasCoupon;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
}
