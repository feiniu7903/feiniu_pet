package com.client;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.lvmama.clutter.service.IClientHotelService;

public class HotelTest extends TestBase {
	IClientHotelService hotel = (IClientHotelService) context.getBean("api_com_hotel_4_0_0");
	@Test
	public void testOrderList() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("pageIndex", "1");
		param.put("userId", "5");
		param.put("contactMobile", "13312121212");
//		p.put("", "");
//		p.put("", "");
//		p.put("", "");
//		p.put("", "");
		try {
			hotel.orderList(param);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//fail("Not yet implemented");
	}

}
