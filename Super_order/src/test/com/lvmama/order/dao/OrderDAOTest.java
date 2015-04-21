package com.lvmama.order.dao;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.bee.po.ord.OrdOrder;

@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderDAOTest {
	@Autowired
	private OrderDAO orderDAO;
	
	@Test
	public void test() {
		Long orderId = 423539L;
		OrdOrder order = orderDAO.selectByPrimaryKey(orderId);
		Assert.assertEquals(0L, order.getRefundedAmount().longValue());
		Assert.assertFalse(order.isRefundExists());
		
		orderDAO.updateRefundedAmount(orderId, 500L);
		order = orderDAO.selectByPrimaryKey(orderId);
		Assert.assertEquals(500L, order.getRefundedAmount().longValue());
		Assert.assertTrue(order.isRefundExists());
		
		orderDAO.updateRefundedAmount(orderId, 400L);
		order = orderDAO.selectByPrimaryKey(orderId);
		Assert.assertEquals(900L, orderDAO.selectByPrimaryKey(orderId).getRefundedAmount().longValue());
		Assert.assertTrue(order.isRefundExists());
	}

}
