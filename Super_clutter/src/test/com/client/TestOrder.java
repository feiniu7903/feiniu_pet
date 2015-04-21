package com.client;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.lvmama.clutter.service.IClientOrderService;
import com.lvmama.clutter.service.client.v3_1.ClientOrderServiceV31;

public class TestOrder extends TestBase {
	ClientOrderServiceV31 os = (ClientOrderServiceV31) context.getBean("api_com_order_3_1_0");
	
	public void test() {
		fail("Not yet implemented");
	}
//T
	//2013-08-19 14:49:31,344 INFO  clutter [http-8035-2] (Router.java:183) - request
	//paramters:{lvversion=3.1.0, osVersion=4.1.2, userAgent=ANDROID_LVMM 3.1.0 (GT-I9220; Android OS 4.1.2; WIFI), 
	//		todayOrder=true, udid=358882049937054, 
	//		isIphone=false, visitTime=2013-08-19, secondChannel=LVMM, branchItem=90282-1, isAndroid=true,
	//lvsessionid=3a4a7e6b-acf0-49a5-be6a-d4310a9d7480, appVersion=310, userNo=000000003f21b2b7013f22ee52d8002d, 
	//userId=7481, method=api.com.order.commitOrder, firstChannel=ANDROID, formate=json, personItem=cs-13162418126-CONTACT-}
	
	//java.lang.reflect.InvocationTargetException
	@Test
	public void testcommitOrder(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("branchItem", "91420-4_93640-1_93641-1");
		param.put("firstChannel", "IPHONE");
		param.put("secondChannel", "LVMM");
		param.put("userNo", "5ad32f1a1d2250d7011d22a254e601c7");
		param.put("visitTime", "2013-11-08");
		param.put("personItem", "sdd-13333333333-CONTACT-420821198709085057");
		param.put("udid", "1231231");
		param.put("lvversion", "4.0.0");
		param.put("osVersion", "4.4");
		param.put("todayOrder", "false");
		os.commitOrder(param);
	}
	
	
	//@Test
	
	public void testValidateTravellerInfo(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("branchItem", "88800-1");
		param.put("firstChannel", "IPHONE");
		param.put("secondChannel", "LVMM");
		param.put("userNo", "ff808081328b6bbe01328b7dfd4000c8");
		param.put("visitTime", "2013-10-31");
		param.put("personItem", "AppleseedJohn-13333333333-CONTACT");
		param.put("udid", "1231231");
		param.put("appVersion", "310");
		param.put("osVersion", "4.4");
		param.put("todayOrder", "false");
		Map<String,Object> obj = os.validateTravellerInfo(param);
		System.out.println(obj);
	
	}

	/**
	 * T
2013-08-08 18:18:23,019 INFO  clutter [http-8035-4] (Router.java:183) - request paramters:{lvversion=3.1.0, osVersion=5.0.1, userAgent=IPHONE_APPSTORE 3.1.0 (iPhone; iPhone OS 5.0.1; 46002), todayOrder=false, couponCode=B191788949375692, udid=871d9cea870c5234394f1a9c24841988, format=json, isIphone=true, visitTime=2013-08-08, secondChannel=APPSTORE, branchItem=84601-1_84641-1, isAndroid=false, validateCouponCode=true, lvsessionid=c467ccf3-a267-4073-a97d-8977d49d5b65, appVersion=310, userNo=5ad32f1a1d2250d7011d22a254e601c7, userId=12, method=api.com.order.validateCoupon, firstChannel=IPHONE}
	 */
	//@Test
	public void testvalidateCoupon(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("branchItem", "84601-1_84641-1");
		param.put("firstChannel", "IPHONE");
		param.put("secondChannel", "LVMM");
		param.put("userNo", "ff808081328b6bbe01328b7dfd4000c8");
		param.put("visitTime", "2013-08-09");
		param.put("personItem", "test2-13600066600-CONTACT");
		param.put("udid", "1231231");
		param.put("appVersion", "310");
		param.put("osVersion", "4.4");
		param.put("userAgent", "1231231");
		param.put("validateCouponCode", true);
		param.put("couponCode", "B191788949375692");
		Map<String,Object> result = os.validateCoupon(param);
		System.out.println(result);
	
	}

	public void testonlineSign(){
		//contactEmail=ty@xv.com&orderId=1313084&optionsCheckBox1=true&optionsCheckBox2=true&optionsCheckBox3=true&optionsCheckBox4=true
		//邓程14:44
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("contactEmail", "ty@xv.com");
		param.put("orderId", "1313171");
		param.put("userNo", "000000003f21b2b7013f22ee52d8002d");
		param.put("optionsCheckBox1", "true");
		param.put("optionsCheckBox2", "true");
		param.put("optionsCheckBox3", "true");
		param.put("optionsCheckBox4", "true");
		try {
			os.onlineSign(param);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
