package com.lvmama.clutter.model.coupon;

import java.util.ArrayList;
import java.util.List;

public class MobilePriceInfo {
	private CouponValidateInfo couponInfo = new CouponValidateInfo();
	
	private List<MobilelValidateBusinessCouponInfo> businessCouponInfoList = new ArrayList<MobilelValidateBusinessCouponInfo>();
	
	private float  oughtPayYuan;
	
	public CouponValidateInfo getCouponInfo() {
		return couponInfo;
	}
	public void setCouponInfo(CouponValidateInfo couponInfo) {
		this.couponInfo = couponInfo;
	}
	public List<MobilelValidateBusinessCouponInfo> getBusinessCouponInfoList() {
		return businessCouponInfoList;
	}
	public void setBusinessCouponInfoList(
			List<MobilelValidateBusinessCouponInfo> businessCouponInfoList) {
		this.businessCouponInfoList = businessCouponInfoList;
	}
	public float getOughtPayYuan() {
		return oughtPayYuan;
	}
	public void setOughtPayYuan(float oughtPayYuan) {
		this.oughtPayYuan = oughtPayYuan;
	}
}
