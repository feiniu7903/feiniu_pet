package com.lvmama.elong;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;


import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.EnumPaymentType;
import com.lvmama.elong.model.EnumProductProperty;
import com.lvmama.elong.model.EnumSortType;
import com.lvmama.elong.model.Hotel;
import com.lvmama.elong.model.HotelDetailCondition;
import com.lvmama.elong.model.HotelListCondition;
import com.lvmama.elong.model.ListRatePlan;
import com.lvmama.elong.model.RatePlan;
import com.lvmama.elong.model.Room;
import com.lvmama.elong.service.IHotelDetailService;
import com.lvmama.elong.service.IHotelSearchService;
import com.lvmama.elong.service.impl.HotelDetailServiceImpl;
import com.lvmama.elong.service.impl.HotelSearchServiceImpl;
import com.lvmama.elong.service.result.HotelListResult;
import com.lvmama.elong.utils.Tool;

public class HotelDetailServiceTest {

	@Test
	public void testGetResult() throws ElongServiceException {
		HotelDetailCondition condition = new HotelDetailCondition();
		Date date = DateUtils.addDays(new Date(), 1);
		Date date2 = DateUtils.addDays(new Date(), 2);
		
		condition.setArrivalDate(date);
		condition.setDepartureDate(date2);
		condition.setHotelIds("50101016");
		//condition.setHotelIds("40101025");
		
		
		IHotelDetailService hotelDetailService = new HotelDetailServiceImpl();
		Hotel hotel = hotelDetailService.getHotel(condition);
		System.out.println(hotel);
		for(Room room:hotel.getRooms()){
			//System.out.println(room);
			for(ListRatePlan listRatePlan : room.getRatePlans()){
				//System.out.println(listRatePlan);
			}
		}
	}

}
