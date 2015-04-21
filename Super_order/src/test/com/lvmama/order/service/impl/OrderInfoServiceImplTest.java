package com.lvmama.order.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.po.ord.OrderInfoDTO;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.Invoice;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.order.service.IBuildOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class OrderInfoServiceImplTest {

	@Resource
	private IBuildOrder orderInfoService;

	@Test
	public void testCreateOrderBuyInfo() {

		BuyInfo buyInfo = new BuyInfo();
		buyInfo.setUserId("UnitTest");
		buyInfo.setChannel("FRONTEND");
		buyInfo.setPaymentTarget("TOLVMAMA");
		buyInfo.setUserMemo("UnitTest");
		buyInfo.setResourceConfirmStatus("true");

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2011);
		cal.set(Calendar.MONTH, 2);
		cal.set(Calendar.DATE, 15);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Item item1 = new Item();
		item1.setProductId(20123);
		item1.setQuantity(1);
		item1.setVisitTime(cal.getTime());
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(item1);
		buyInfo.setItemList(itemList);

		Person person1 = new Person();
		person1.setPersonType("traveller");
		person1.setName("www");
		person1.setMobile("13918066110");
		person1.setEmail("sericwu@hotmail.com");
		Person person2 = new Person();
		person2.setPersonType("traveller");
		person2.setName("www");
		person2.setFax("13918066110");
		person2.setMemo("sericwu@hotmail.com");
		List<Person> personList = new ArrayList<Person>();
		personList.add(person1);
		personList.add(person2);
		buyInfo.setPersonList(personList);

		Invoice invoice1 = new Invoice();
		invoice1.setTitle("UnitTest1");
		invoice1.setDetail("UnitTest1Detail");
		invoice1.setAmount(0L);
		invoice1.setMemo("" + new Date());
		Invoice invoice2 = new Invoice();
		invoice2.setTitle("UnitTest2");
		invoice2.setDetail("UnitTest2Detail");
		invoice2.setAmount(0L);
		invoice2.setMemo("" + new Date());
		List<Invoice> invoiceList = new ArrayList<Invoice>();
		invoiceList.add(invoice1);
		invoiceList.add(invoice2);
		buyInfo.setInvoiceList(invoiceList);
		

		OrderInfoDTO orderInfo = null;
		//orderInfo = orderInfoService.createOrder(buyInfo);

		//System.out.println("-------" + orderInfo.getOrder().getOrderNo());
		System.out.println("======================");

	}

}
