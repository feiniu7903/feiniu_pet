package com.lvmama.report.po;

import java.util.Date;

import com.lvmama.comm.utils.DateUtil;

public class CouponUsegeModel {
	private String objectId;
	private String channelName;
	private String productName;
	private String couponName;
	private String couponCode;	
	private Date createTime;
	private Float amount;
	private Long orderId;
	private Float actualPayFloat;
	private String formattedCreateTime;
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
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
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Float getActualPayFloat() {
		return actualPayFloat;
	}
	public void setActualPayFloat(Float actualPayFloat) {
		this.actualPayFloat = actualPayFloat;
	}
	public String getFormattedCreateTime() {
		return DateUtil.getFormatDate(createTime, "yyyy-MM-dd HH:mm:ss");
	}
	public void setFormattedCreateTime(String formattedCreateTime) {
		this.formattedCreateTime = formattedCreateTime;
	}
}
