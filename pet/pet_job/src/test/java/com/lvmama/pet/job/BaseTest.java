package com.lvmama.pet.job;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.opensymphony.xwork2.interceptor.annotations.Before;


@ContextConfiguration(locations = { "classpath*:/applicationContext-pet-job-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTest {
	protected ApplicationContext context;
	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(
				"classpath*:/applicationContext-pet-job-beans.xml");
	}
}