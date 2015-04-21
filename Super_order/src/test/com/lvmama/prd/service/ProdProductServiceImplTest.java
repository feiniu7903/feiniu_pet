package com.lvmama.prd.service;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.spring.SpringBeanProxy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class ProdProductServiceImplTest implements ApplicationContextAware{

	@Autowired
	ProdProductService prodProductService;
	@Test
	public void test() {
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
		Long productId=70148L;
		Long branchId = 783893L;
		Date date = new Date();
		List<TimePrice> list = prodProductService.getMainProdTimePrice(productId,  branchId);
		Assert.assertNotNull(list);
		Date date2 = new Date();
		System.out.print("xxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println(date2.getTime()-date.getTime());
	}
	
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		SpringBeanProxy.setApplicationContext(arg0);
	}

}
