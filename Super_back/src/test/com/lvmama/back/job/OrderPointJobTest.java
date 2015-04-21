package com.lvmama.back.job;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class OrderPointJobTest {
	
	@Test
	public void test() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-back-beans.xml");
		OrderPointJob orderPointJob = (OrderPointJob)context.getBean("orderPointJob");
		try {
			orderPointJob.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
