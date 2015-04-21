package com.client;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.lvmama.clutter.model.MobilePlace;
import com.lvmama.clutter.service.IClientPlaceService;


public class TestPlaceService extends TestBase{
	IClientPlaceService os = (IClientPlaceService) context.getBean("api_com_place");
	
	/**
	 * 获取标的详情
	 */
	@Test
	public void testGetPlace() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("placeId", "asfa");
		MobilePlace c = os.getPlace(param);
		if(null != c) {
			System.out.println("=获取标的详情 success  the place id =====" +c.getId());
		}else {
			System.out.println("=获取标的详情 faild  there is no place was found!=====");
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
