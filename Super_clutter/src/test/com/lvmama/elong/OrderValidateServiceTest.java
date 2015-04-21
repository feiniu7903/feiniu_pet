package com.lvmama.elong;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.ValidateCondition;
import com.lvmama.elong.service.IOrderValidateService;
import com.lvmama.elong.service.impl.OrderValidateServiceImpl;
import com.lvmama.elong.utils.Tool;

public class OrderValidateServiceTest {
	private Logger logger = Logger.getLogger(this.getClass());
	@Test
	public void testGetResult() throws ParseException, ElongServiceException {
		ValidateCondition condition = new ValidateCondition();
		Date date = DateUtils.addDays(new Date(), 1);
		Date departureDate = Tool.addDate(date, 3);

		
		String strDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		logger.info(DateUtils.parseDate(strDate, "yyyy-MM-dd"));
		condition.setArrivalDate(DateUtils.parseDate(strDate, "yyyy-MM-dd"));
		
		logger.info(DateUtils.parseDate(strDate, "yyyy-MM-dd").before(DateUtils.parseDate(strDate, "yyyy-MM-dd")));
		condition.setEarliestArrivalTime(DateUtils.addHours(DateUtils.parseDate(strDate, "yyyy-MM-dd"), 6));
		logger.info(DateUtils.addHours(DateUtils.parseDate(strDate, "yyyy-MM-dd"),8));
		condition.setLatestArrivalTime(DateUtils.addHours(DateUtils.parseDate(strDate, "yyyy-MM-dd"),6));
		
		condition.setDepartureDate(departureDate);
		condition.setHotelId("60101527");
		condition.setRoomTypeId("0001");
		condition.setRatePlanId(237625);
		condition.setNumberOfRooms(1);
		condition.setTotalPrice(BigDecimal.valueOf(3450));

		IOrderValidateService validateService = new OrderValidateServiceImpl();
		validateService.validateOrder(condition);
	}

}
