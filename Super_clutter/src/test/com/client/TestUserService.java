package com.client;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.lvmama.clutter.model.MobileOrder;
import com.lvmama.clutter.model.MobileUser;
import com.lvmama.clutter.service.client.v3_1.ClientUserServiceV31;


public class TestUserService extends TestBase{
	ClientUserServiceV31 os = (ClientUserServiceV31) context.getBean("api_com_user_3_1_0");
	
	/**
	 * 获取用户信息 
	 */
	//@Test
	public void testGetUser() {
		Map<String,String> param = new HashMap<String,String>();
		param.put("userNo", "ff808081328b6bbe01328b7dfd4000c8");
		param.put("lvsessionid", "f4233fca-029e-4f17-8075-ac8e7e2c2b0b");
		MobileUser c = os.getUser(param);
		if(null != c) {
			System.out.println("=获取用户信息 success  the user name =====" +c.getImageUrl());
		}else {
			System.out.println("=获取用户信息 faild  there is no user was found!=====");
		}
	}
	
	
	/**
	 * 获取用户信息 
	 */
	@Test
	public void testaddContact() {
		Map<String,String> param = new HashMap<String,String>();
		param.put("userNo", "5ad32f1a1d2250d7011d22a254e601c7");
		param.put("certType", "HUZHAO");
		param.put("certNo", "1231231231");
	//receiverName
		param.put("receiverName", "a");
		param.put("mobileNumber", "1111111111");
		param.put("gender", "F");
		param.put("birthday", "1987-09-08");
		os.addContact(param);
	}
	
	/**
	 * 获取用户优惠劵信息
	 */
	//@Test
	public void testGetCoupon() {
		Map<String,String> param = new HashMap<String,String>();
		param.put("userId", "298");
		param.put("state", "USED");
		param.put("lvsessionid", "f4233fca-029e-4f17-8075-ac8e7e2c2b0b");
//		List<MobileUserCoupon> c = os.getCoupon(param);
//		if(null != c) {
//			System.out.println("=获取用户优惠劵信息 success  the userCouponList size =====" +c.size());
//		}else {
//			System.out.println("=获取用户优惠劵信息 faild  there is no userCouponList was found!=====");
//		}
	}
	
	
	/**
	 * 获取用户某个订单 
	 */
	//@Test
	public void testGetOrder() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userNo", "40288add3fe1d53a013ff0d54850546f");
		param.put("orderId", "2696178");

		MobileOrder mo = os.getOrder(param);
		System.out.println(mo);
		
	}
	
	/**
	 * 获取用户订单列表
	 */
	public void testGetOrderList() {
		Map<String,String> param = new HashMap<String,String>();
		param.put("userId", "5ad32f1a1d2250d7011d22a254e601c7");
		param.put("lvsessionid", "f4233fca-029e-4f17-8075-ac8e7e2c2b0b");

	}
	
//	@Test
//	public void testgetBonusBalanceInCome(){
//		os.getBonusBalanceInCome();
//	}
	//@Test
	public void testqueryCmtWaitForOrder(){
		//{lvversion=3.1.0, osVersion=4.0.4, userAgent=ANDROID_LVMM 3.1.0 (SHV-E160L; Android OS 4.0.4; WIFI), udid=35955404018268, secondChannel=LVMM, isIphone=false, lvsessionid=7f9576e5-fc87-474a-aee5-3c794e6dc7ff, isAndroid=true, appVersion=310, userNo=5ad32f1a1d2250d7011d22a254e601c7, userId=12, method=api.com.user.queryCmtWaitForOrder, firstChannel=ANDROID, formate=json}
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", "12");
		param.put("userNo", "5ad32f1a1d2250d7011d22a254e601c7");
		param.put("lvsessionid", "f4233fca-029e-4f17-8075-ac8e7e2c2b0b");
		os.queryCmtWaitForOrder(param);
		
	}
}
