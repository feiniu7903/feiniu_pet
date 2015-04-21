package com.lvmama.hotel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lvmama.hotel.service.longtengjielv.LongtengjielvProductService;


public class LongTengTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-passport-beans.xml");
		LongtengjielvProductService longtengjielvProductService=(LongtengjielvProductService)context.getBean("longtengjielvProductService");
//		longtengjielvProductService.initProductTimePrice();
//		longtengjielvProductService.initAdditionalProdTimePrice();
//		longtengjielvProductService.updateProductTimePrice("22655", "120115");
//		longtengjielvProductService.updateAdditionalProdTimePrice("22655", "120115");
		try {
			longtengjielvProductService.onOfflineHotel("22655");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*List<String> a=null;
		for(String b : a){
			System.out.println(a);
		}*/
	}
}
