package com.lvmama.prd.logic;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.spring.SpringBeanProxy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class CalendarUtilV2Test implements ApplicationContextAware{
	
	@Test
	public void testSelectSaleTimePrice() {
		CalendarUtilV2 cv= new CalendarUtilV2();
		List<?> list=cv.selectSaleTimePrice(84603L);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 2);
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		SpringBeanProxy.setApplicationContext(arg0);
	}

}
