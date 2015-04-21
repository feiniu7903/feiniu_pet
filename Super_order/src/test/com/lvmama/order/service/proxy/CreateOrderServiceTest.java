/**
 * 
 */
package com.lvmama.order.service.proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.service.OrderCreateService;

/**
 * @author yangbin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
//@TransactionConfiguration(transactionManager = "txManager")
//@Transactional(readOnly=false)
public class CreateOrderServiceTest {

	@Autowired
	private OrderCreateService orderBuildServiceProxy;
	@Test
	public void testCreateOrder(){
		BuyInfo buyInfo = new BuyInfo();
		buyInfo.setTodayOrder(true);
		BuyInfo.Item item = new BuyInfo.Item();
		
		List<BuyInfo.Item> list = new ArrayList<BuyInfo.Item>();
		item.setIsDefault("true");
		item.setProductBranchId(88580L);
		item.setProductId(66079L);
		item.setQuantity(1);
		Date date = DateUtil.getDayStart(new Date());
		item.setVisitTime(date);
		list.add(item);
		buyInfo.setItemList(list);
		
		buyInfo.setUserId("ff80808130e56a6d0130f2ea0d5e44d5");
		buyInfo.setChannel("CLIENT");
		List<Person> personList = new ArrayList<Person>();
		Person person = new Person();
		person.setName("杨杨");
		person.setMobile("1388");
		personList.add(person);		
		buyInfo.setPersonList(personList);
		buyInfo.setWaitPayment(Constant.WAIT_PAYMENT.PW_2HOUR.getValue());
		OrdOrder order = orderBuildServiceProxy.createOrder(buyInfo);
		Assert.assertNotNull(order);
		System.out.println(order.getOrderId());
		System.out.println(order.getOughtPay());
	}
}
