package com.client;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.lvmama.clutter.service.IClientOtherService;

public class TestOtherService extends TestBase{
	IClientOtherService os = (IClientOtherService) context.getBean("api_com_other");
	// 反馈意见测试
	public void testSubSuggest() {
        // &content=反馈意见测试!&email=******@joyu.com&firstChannel=PC
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("content", "反馈意见测试!");
		param.put("email", "******@joyu.com");
		param.put("firstChannel", "PC");
		os.subSuggest(param);
	}

	 
	//@Test
	public void testGetNameByLocation() {
        // &keyword=反馈意见测试!&email=******@joyu.com&firstChannel=PC
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("keyword", "上海");
		Map m = os.getNameByLocation(param);
		System.out.println("id===" +m.get("id") + "=name====" +m.get("name") );
	}
	
	
	
	public void testaddMobilePushDevice(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("imei", "12312312312112312");
		try {
			os.addMobilePushDevice(param);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testroll(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("imei", "12312312312112312123");
		param.put("firstChannel", "ANDROID");
		try {
			os.rollMarkCoupon(param);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
