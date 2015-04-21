package com.lvmama.prd.logic;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class ProductTimePriceLogicTest {

	@Autowired
	private ProductTimePriceLogic productTimePriceLogic;
	
	@Test
	public void testGetTimePriceListLongLongIntegerDate() {
		List list=productTimePriceLogic.getTimePriceList(63756L, 84603L, 20);
		Assert.assertNotNull(list);
	}

}
