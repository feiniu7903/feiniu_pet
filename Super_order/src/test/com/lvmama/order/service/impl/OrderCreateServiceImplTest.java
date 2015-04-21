package com.lvmama.order.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.order.service.OrderCreateService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class OrderCreateServiceImplTest {
	
//	@Resource
	private OrderCreateService orderCreateService;

	@Test
	public void testCreateOrderBuyInfo() {
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.YEAR, 2010);
//		cal.set(Calendar.MONTH, 8);
//		cal.set(Calendar.DATE, 10);
//		cal.set(Calendar.HOUR_OF_DAY, 0);
//		cal.set(Calendar.MINUTE, 0);
//		cal.set(Calendar.SECOND, 0);
//		cal.set(Calendar.MILLISECOND, 0);
//
//		BuyInfo buyInfo = new BuyInfo();
//		/**
//		 * 校验userId是否为null
//		 */
//		buyInfo.setUserId("40288a8b2288be27012291e8e6f416ad");
//
//		List<Person> personList = new ArrayList<Person>();
//		Person person = new Person();
//		personList.add(person);
//		/**
//		 * 校验personList是否为null
//		 */
//		buyInfo.setPersonList(personList);
//
//		List<Item> itemList = new ArrayList<Item>();
//		Item item = new Item();
//		/**
//		 * 校验item的quantity属性是否为0
//		 */
//		item.setQuantity(1);
//		/**
//		 * 校验通过productId所查询的product是否不存在
//		 */
//		item.setProductId(20129);
//		/**
//		 * 校验timePrice是否为空
//		 */
//		item.setVisitTime(cal.getTime());
//		itemList.add(item);
//		/**
//		 * 校验ItemList是否为null
//		 */
//		buyInfo.setItemList(itemList);
//
//		orderCreateService.createOrder(buyInfo);
	}

//	@Test
	public void testCreateOrderBuyInfoString() {
	}

//	@Test
	public void testCreateOrderBuyInfoLongString() {
	}

}
