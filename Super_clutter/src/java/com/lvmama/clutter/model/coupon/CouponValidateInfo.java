package com.lvmama.clutter.model.coupon;

public class CouponValidateInfo {
	
	private String key;
	private String value;
	private boolean valid=false;
	private Long youhuiAmountYuan; 
	private String returnCouponCode;
	
	public String getReturnCouponCode() {
		return returnCouponCode;
	}
	public void setReturnCouponCode(String returnCouponCode) {
		this.returnCouponCode = returnCouponCode;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public Long getYouhuiAmountYuan() {
		return youhuiAmountYuan;
	}
	public void setYouhuiAmountYuan(Long youhuiAmountYuan) {
		this.youhuiAmountYuan = youhuiAmountYuan;
	}
}
