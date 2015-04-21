package com.lvmama.elong;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.Contact;
import com.lvmama.elong.model.CreateOrderCondition;
import com.lvmama.elong.model.CreateOrderResult;
import com.lvmama.elong.model.CreateOrderRoom;
import com.lvmama.elong.model.Customer;
import com.lvmama.elong.model.EnumGender;
import com.lvmama.elong.model.EnumGuestTypeCode;
import com.lvmama.elong.model.EnumPaymentType;
import com.lvmama.elong.service.IOrderCreateService;
import com.lvmama.elong.service.impl.OrderCreateServiceImpl;
import com.lvmama.elong.service.result.WrapCreateOrderResult;
import com.lvmama.elong.utils.Tool;

public class OrderCreateServiceTest {

	@Test
	public void testGetResult() throws ParseException, ElongServiceException {
		CreateOrderCondition condition = new CreateOrderCondition();
		Date date = new Date();
		Date date2 = Tool.addDate(date, 2);

		condition.setArrivalDate(date);
		condition.setDepartureDate(date2);
		String strDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		condition.setEarliestArrivalTime(DateUtils.addHours(DateUtils.parseDate(strDate, "yyyy-MM-dd"), 15));
		condition.setLatestArrivalTime(DateUtils.addHours(DateUtils.parseDate(strDate, "yyyy-MM-dd"), 18));
		condition.setAffiliateConfirmationId("lvmama_test4");
		condition.setHotelId("50101011");
		condition.setRoomTypeId("0016");
		condition.setRatePlanId(13553);
		condition.setTotalPrice(BigDecimal.valueOf(1596));
		condition.setNumberOfRooms(1);
		condition.setNumberOfCustomers(2);
		condition.setCustomerType(EnumGuestTypeCode.All);
		condition.setPaymentType(EnumPaymentType.SelfPay);
		condition.setCustomerIPAddress("10.3.1.150");

		// 联系人信息
		Contact contact = new Contact();
		contact.setName("lvmama");
		contact.setMobile("13312121212");
		condition.setContact(contact);

		// 客人信息
		List<CreateOrderRoom> orderRooms = new ArrayList<CreateOrderRoom>();// start
																			// orderRooms
		CreateOrderRoom createOrderRoom = new CreateOrderRoom();
		List<Customer> customers = new ArrayList<Customer>();// start customers
		Customer customer = new Customer();
		customer.setName("lvmama");
		//customer.setGender(EnumGender.Maile);
		customers.add(customer);// end customers
		Customer customer2 = new Customer();
		customer2.setName("lvmama2");
		//customer2.setGender(EnumGender.Female);
		customers.add(customer2);// end customers
		createOrderRoom.setCustomers(customers);
		orderRooms.add(createOrderRoom);// end orderRooms
		condition.setOrderRooms(orderRooms);

		IOrderCreateService hotelOrderCreateService = new OrderCreateServiceImpl();
		CreateOrderResult createOrderResult = hotelOrderCreateService
				.createOrder(condition);
		System.out.println(createOrderResult);
	}

}
