package com.lvmama.order.service.impl;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.order.service.OrderUpdateService;
/**
 * 
 * @author liwenzhan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class OrderUpdateServiceImplTest {

	
	@Resource
	private OrderUpdateService orderUpdateService;
	@Test
	public void testEditOrder() {
		Assert.assertFalse(orderUpdateService.editOrder(962416L, 2L, 0L));
	}

}
