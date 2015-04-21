package com.lvmama.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.bee.vo.ord.BuyInfo.Coupon;

public class CouponList {

	
	static List<Coupon> markCouponList(){
		List<Coupon> conList=new ArrayList<Coupon>();
//		Coupon coupon=new Coupon();
//		coupon.setCouponId(767L);
//		coupon.setCode("AHLW7685368465");
//		coupon.setChecke\d("true");
//		conList.add(coupon);
		
		Coupon coupon2=new Coupon();
		coupon2.setCouponId(967L);
		coupon2.setCode("AHD4872496725");
		coupon2.setChecked("true");
		conList.add(coupon2);
		 
		
		return conList;
	}
	
}
