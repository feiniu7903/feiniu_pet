package com.lvmama.order.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.order.service.OrderCreateService;

/**
 * 测试ValidityOrderInfoAround这个AOP是否实现参数校验
 * @author sunruyi
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class OrderBuildServiceImplTest {

	@Resource
	private OrderCreateService orderBuildServiceProxy;
	
	/**
	 * 测试ValidityOrderInfoAround这个AOP是否实现参数校验
	 */
	@Test
	public void testCreateOrderBuyInfo() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2011);
		cal.set(Calendar.MONTH, 7);
		cal.set(Calendar.DATE, 15);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		BuyInfo buyInfo = new BuyInfo();
		/**
		 * 校验userId是否为null
		 */
		buyInfo.setUserId("40288a8b2288be27012291e8e6f416ad");

		List<Person> personList = new ArrayList<Person>();
		Person person = new Person();
		personList.add(person);
		/**
		 * 校验personList是否为null
		 */
		buyInfo.setPersonList(personList);

		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		/**
		 * 校验item的quantity属性是否为0
		 */
		item.setQuantity(1);
		/**
		 * 校验通过productId所查询的product是否不存在
		 */
		item.setProductId(29011);
		/**
		 * 校验timePrice是否为空
		 */
		item.setVisitTime(cal.getTime());
		itemList.add(item);
		/**
		 * 校验ItemList是否为null
		 */
		buyInfo.setItemList(itemList);

		orderBuildServiceProxy.createOrder(buyInfo);
	}


}
