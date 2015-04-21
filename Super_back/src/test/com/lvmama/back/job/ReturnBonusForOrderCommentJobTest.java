package com.lvmama.back.job;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-back-beans.xml" })
public class ReturnBonusForOrderCommentJobTest {
	
	@Autowired
	private ReturnBonusForOrderCommentJob returnBonusForOrderCommentJob;
	
	@Test
	public void test() {
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-back-beans.xml");
//		ReturnBonusForOrderCommentJob job = (ReturnBonusForOrderCommentJob)context.getBean("returnBonusForOrderCommentJob");
		try {
			returnBonusForOrderCommentJob.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
//		ReturnBonusForOrderCommentJobTest test =new ReturnBonusForOrderCommentJobTest();
//		test.test();
//		System.out.println("success");
//	}
}
