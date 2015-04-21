package com.lvmama.comm.pet.po.shop;

import java.io.Serializable;
import java.util.Date;

public class ShopCooperationCoupon implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3289346663508945722L;
	
	/**
	 * 合作网站优惠券编码
	 */
	private Long id; 
	/**
	 * 产品标识
	 */
	private Long productId;
	
	/**
	 * 优惠券信息
	 */
	private String couponInfo;
	/**
	 * 创建人
	 */
	private String createUser;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getCouponInfo() {
		return couponInfo;
	}

	public void setCouponInfo(String couponInfo) {
		this.couponInfo = couponInfo;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
