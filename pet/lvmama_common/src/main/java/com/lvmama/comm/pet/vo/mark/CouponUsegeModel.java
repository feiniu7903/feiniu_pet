package com.lvmama.comm.pet.vo.mark;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.bee.po.prod.ProdProduct;

public class CouponUsegeModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6435911692223113206L;
	private Long orderId;
	private ProdProduct product;
	private String couponCode;
	private Date createTime;
	private Float amount;
	private String couponName;
	private Float actualPayFloat;
	private Long couponId;
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public ProdProduct getProduct() {
		return product;
	}
	public void setProduct(ProdProduct product) {
		this.product = product;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public Float getActualPayFloat() {
		return actualPayFloat;
	}
	public void setActualPayFloat(Float actualPayFloat) {
		this.actualPayFloat = actualPayFloat;
	}
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}


}
