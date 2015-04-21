package com.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.lvmama.clutter.service.IClientProductService;
import com.lvmama.clutter.service.client.v4_0.ClientProductServiceV40;
import com.lvmama.comm.pet.po.client.ClientTimePrice;

public class TestProductService extends TestBase {

	ClientProductServiceV40 os = (ClientProductServiceV40) context.getBean("api_com_product_4_0_0");
	
	/**
	 * 获取产品详情 
	 */
	public void testGetPlace() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("productId", "61496");
//		Product c = os.getProduct(param);
//		if(null != c) {
//			System.out.println("=获取产品详情 success the place id =====" +c.getProductName());
//		}else {
//			System.out.println("=获取产品详情 faild there is no place was found!=====");
//		}
	}
	
	/**
	 * 时间价格表  /63777/84700
	 */
	public void testTimePrice() {
//		Map<String,Object> param = new HashMap<String,Object>();
//		param.put("productId", "63277");
//		//param.put("branchId", "84700");
//		List<ClientTimePrice> c = os.timePrice(param);
//		if(null != c) {
//			System.out.println("=时间价格表 success the size =====" +c.size());
//		}else {
//			System.out.println("=时间价格表 faild there is no time price was found!=====");
//		}
	}
	
	@Test
	public void testgetProductItems(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("branchId", "84700");
		param.put("udid", "85028");
		param.put("todayOrder", "false");
		//param.put("branchId", "84700");
		Map<String,Object> map = os.getProductItems(param);
		System.out.println(map);
	}
	
	
	public void testgetGroupOnList(){
		os.getGroupOnList(null);
	}
}
