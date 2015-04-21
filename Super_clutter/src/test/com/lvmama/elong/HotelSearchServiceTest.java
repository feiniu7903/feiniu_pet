package com.lvmama.elong;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.CreateOrderCondition;
import com.lvmama.elong.model.CreateOrderResult;
import com.lvmama.elong.model.EnumPaymentType;
import com.lvmama.elong.model.EnumProductProperty;
import com.lvmama.elong.model.EnumSortType;
import com.lvmama.elong.model.Hotel;
import com.lvmama.elong.model.HotelListCondition;
import com.lvmama.elong.service.IOrderCreateService;
import com.lvmama.elong.service.IHotelSearchService;
import com.lvmama.elong.service.impl.OrderCreateServiceImpl;
import com.lvmama.elong.service.impl.HotelSearchServiceImpl;
import com.lvmama.elong.utils.Tool;

public class HotelSearchServiceTest {

	@Test
	public void testGetResult() throws ElongServiceException {
		HotelListCondition condition = new HotelListCondition();
		//Date date = DateUtils.addDays(new Date(), 0);
		Date date = new Date();
		Date date2 = DateUtils.addDays(new Date(), 1);
		
		condition.setArrivalDate(date);
		condition.setDepartureDate(date2);
		condition.setCityId("0101");
		condition.setPaymentType(EnumPaymentType.SelfPay);
		condition.setProductProperties(EnumProductProperty.All);
		condition.setSort(EnumSortType.Default);
		condition.setResultType("1,2,3");
		
		condition.setQueryText("API");
		//condition.setQueryText("上海维蒂利宾馆");
		//condition.setLocationId("020105");
		//condition.setLocationId("020180");
		
		IHotelSearchService hotelService = new HotelSearchServiceImpl();
		List<Hotel> hotels = hotelService.list(condition).getHotels();
		for(Hotel hotel:hotels){
			System.out.println(hotel);
		}
	}

}
