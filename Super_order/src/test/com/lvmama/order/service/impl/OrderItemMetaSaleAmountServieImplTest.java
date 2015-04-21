package com.lvmama.order.service.impl;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.order.service.OrderItemMetaSaleAmountServie;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class OrderItemMetaSaleAmountServieImplTest {

	@Resource
	OrderItemMetaSaleAmountServie orderItemMetaSaleAmountServie;
	@Test
	public void testUpdateOrderItemMetaSaleAmount() {
		Long[] ids = {2322984L};
		for(Long id:ids){
		orderItemMetaSaleAmountServie.updateOrderItemMetaSaleAmount(id);
		}
	}

	@Test
	public void testUpdateOrderItemMetaAmount() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateOrderItemSaleAmount() {
		fail("Not yet implemented");
	}

}
