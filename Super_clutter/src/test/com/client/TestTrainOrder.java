package com.client;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.lvmama.clutter.service.impl.ClientTrainOrderServiceImpl;
import com.lvmama.clutter.service.impl.ClientTrainProductServiceImpl;

public class TestTrainOrder extends TestBase {
	
	/**
	 * 订单
	 */
	ClientTrainOrderServiceImpl trainOrder = (ClientTrainOrderServiceImpl) context.getBean("api_com_order_train");
	
	ClientTrainProductServiceImpl cps = (ClientTrainProductServiceImpl) context.getBean("api_com_product_train_4_1_0");
	
	@Test
	public void testgetProductItems(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("branchId", "787746");
		param.put("udid", "85028");
		param.put("todayOrder", "false");
		//param.put("branchId", "84700");
		
		param.put("visitTime", "2013-12-20");
		param.put("productId", "70147");
		
		Map<String,Object> map = cps.getProductItems(param);
		System.out.println(map);
	}
	
	
	/*
	@Test
	public void testcommitOrder(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("branchItem", "787746-1");
		param.put("firstChannel", "IPHONE");
		param.put("secondChannel", "LVMM");
		param.put("userNo", "5ad32f1a1ccdf220011ccfc756ab0012");
		param.put("visitTime", "2013-12-20");
		param.put("personItem", "sdd-13333333333-CONTACT-420821198709085057");
		param.put("udid", "1231231");
		param.put("lvversion", "4.0.0");
		param.put("osVersion", "4.4");
		param.put("todayOrder", "false");
		Map<String,Object> obj = trainOrder.commitOrder(param);
		System.out.println(obj);
	}
	*/
	
	// firstChannel","secondChannel","branchItem","visitTime","udid","validateCouponCode
	/*@Test
	public void testValidateTravellerInfo(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("branchItem", "787746-1");
		param.put("firstChannel", "IPHONE");
		param.put("secondChannel", "LVMM");
		param.put("userNo", "ff808081328b6bbe01328b7dfd4000c8");
		param.put("visitTime", "2013-12-20");
		param.put("personItem", "AppleseedJohn-13333333333-CONTACT");
		param.put("udid", "1231231");
		param.put("appVersion", "310");
		param.put("osVersion", "4.4");
		param.put("todayOrder", "false");
		param.put("validateCouponCode", "false");
		Map<String,Object> obj = trainOrder.validateTravellerInfo(param);
		System.out.println(obj);
	
	}*/
	/*@Test
	public void testCheckStock(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("branchItem", "787746-1");
		param.put("firstChannel", "IPHONE");
		param.put("secondChannel", "LVMM");
		param.put("userNo", "ff808081328b6bbe01328b7dfd4000c8");
		param.put("visitTime", "2013-12-20");
		param.put("personItem", "AppleseedJohn-13333333333-CONTACT");
		param.put("udid", "1231231");
		param.put("appVersion", "310");
		param.put("osVersion", "4.4");
		param.put("todayOrder", "false");
		param.put("validateCouponCode", "false");
		Map<String,Object> obj = cps.checkStock(param);
		System.out.println(obj);
	
	}*/
	
}
