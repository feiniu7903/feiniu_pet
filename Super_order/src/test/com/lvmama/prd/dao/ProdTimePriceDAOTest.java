package com.lvmama.prd.dao;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.bee.po.prod.TimePrice;

@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional(readOnly=false)
@RunWith(SpringJUnit4ClassRunner.class)
public class ProdTimePriceDAOTest {

	@Autowired
	private ProdTimePriceDAO prodTimePriceDAO;
	
	
	@Test
	public void testSelectLowestPriceByBranchId() {
		Long prodBranchId = 32364L;
		TimePrice price = prodTimePriceDAO.selectLowestPriceByBranchId(prodBranchId, false,new Date(),1);
		System.out.println(price);
		Assert.assertNotNull(price);
		Assert.assertTrue(price.getPrice()>0L);
	}

}
