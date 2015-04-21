package com.lvmama.comm.vo;

import java.io.Serializable;

public class CouponInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1103346308721099590L;
	private Long couponId;
	private String code;
	private String checked="false";
	private String paymentChannel;
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getPaymentChannel() {
		return paymentChannel;
	}
	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}
	


}
