package com.lvmama.back.job;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.service.prod.ProdProductBranchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-back-beans.xml","classpath:org/codehaus/xfire/spring/xfire.xml"})
public class PriceUpdateJobTest {
	
	@Autowired
	PriceUpdateJob priceUpdateJob;
	
	@Autowired
	ProdProductBranchService prodProductBranchService;
	
	
	@Test
	public void testRun() {
		try {
			//priceUpdateJob.run();
			prodProductBranchService.updatePriceByBranchId(104360L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
