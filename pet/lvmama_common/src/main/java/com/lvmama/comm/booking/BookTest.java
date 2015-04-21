package com.lvmama.comm.booking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.util.JSONUtils;

import com.lvmama.comm.booking.po.BookingDetails;
import com.lvmama.comm.booking.po.BookingHotel;
import com.lvmama.comm.booking.vo.BookingOrder;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.JsonUtil;

public class BookTest {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		
		String url = "https://lvmamasynw:7660@distribution-xml.booking.com/json/bookings.getBookingDetails?id=146233380";//远程url
		HttpsUtil httpsUtil = new HttpsUtil();
		
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd"}));
		
		String jsonString = httpsUtil.requestGet(url);//返回值
		List<BookingOrder> list = JsonUtil.getList4Json(jsonString, BookingOrder.class, null);
		for(BookingOrder bo:list){
			System.out.println(bo.getGuest_city());
			System.out.println(bo.getId());
			System.out.println(bo.getArrival_date());
			System.out.println(bo.getCreation_datetime());
			System.out.println(bo.getTotalcost());
			System.out.println(bo.getCommission());
			System.out.println(bo.getEuro_fee());
			System.out.println(bo.getStatus());
		}
		
		/*
		String url = "https://lvmamasynw:7660@distribution-xml.booking.com/json/bookings.getHotels";//远程url
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("hotel_ids", "98251,");
		
		HttpsUtil httpsUtil = new HttpsUtil();
		
		String jsonHotelString = httpsUtil.requestGet(url,map);//返回值
		
		System.out.println(jsonHotelString);
		
		List<BookingHotel> list = JsonUtil.getList4Json(jsonHotelString, BookingHotel.class, null);
		
		for(BookingHotel bh:list){
			System.out.println(bh.getHotel_id());
			System.out.println(bh.getName());
			System.out.println(bh.getCountrycode());
			System.out.println(bh.getCity());
		}
		*/
		
	}

}
