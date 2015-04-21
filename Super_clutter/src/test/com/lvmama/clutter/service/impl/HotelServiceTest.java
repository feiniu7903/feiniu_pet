package com.lvmama.clutter.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import com.lvmama.clutter.service.IClientHotelService;
import com.lvmama.elong.model.EnumPaymentType;
import com.lvmama.elong.model.EnumProductProperty;
import com.lvmama.elong.model.EnumSortType;
import com.lvmama.elong.model.Hotel;
import com.lvmama.elong.model.HotelListCondition;
import com.lvmama.elong.utils.Tool;

public class HotelServiceTest {

	@Test
	public void testGetResult() throws IllegalAccessException, InvocationTargetException {
		Map<String, Object> param = new HashMap<String, Object>();
		HotelListCondition condition = new HotelListCondition();
		Date date = new Date();
		Date date2 = Tool.addDate(date, 1);

		condition.setArrivalDate(date);
		condition.setDepartureDate(date2);
		condition.setCityId("0101");
		condition.setPaymentType(EnumPaymentType.SelfPay);
		condition.setProductProperties(EnumProductProperty.All);
		condition.setSort(EnumSortType.Default);

		BeanUtils.populate(condition, param);

		System.out.println(param);

		IClientHotelService hotelService = new ClientHotelServiceImpl();
		//Map<String, Object> resultMap = hotelService.search(param);
		//System.out.println(resultMap);
//		for (Hotel hotel : hotels) {
//			System.out.println(hotel);
//		}
	}

}
