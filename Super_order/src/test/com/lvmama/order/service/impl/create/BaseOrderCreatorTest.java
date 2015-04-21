package com.lvmama.order.service.impl.create;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.utils.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class BaseOrderCreatorTest {
	
//	@Resource
//	private BaseOrderCreator baseOrderCreator;
	
	@Test
	public final void testCreateOrder() {
//		BuyInfo buyInfo = createBuyInfo();
//		OrdOrder order = baseOrderCreator.createOrder(buyInfo);
//		Assert.assertNotNull(order);
	}
	
	private BuyInfo createBuyInfo() {
		BuyInfo buyInfo = new BuyInfo();
		List<Person> personList = new ArrayList<Person>();
		Person person = new Person();
		personList.add(person);
		
		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setProductId(21786L);
		item.setQuantity(1);
		item.setVisitTime(DateUtil.getClearCalendar().getTime());
		itemList.add(item);
		
		buyInfo.setUserId("40288a8d2256a4480122582e880813dc");
		buyInfo.setItemList(itemList);
		buyInfo.setPersonList(personList);
		return buyInfo;
	}
}
