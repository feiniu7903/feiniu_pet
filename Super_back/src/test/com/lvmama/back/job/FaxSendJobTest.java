package com.lvmama.back.job;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-back-beans.xml"})
public class FaxSendJobTest {
	@Resource
	private FaxSendJob faxSendJob;
	
	@Test
	public void testJob() {
		faxSendJob.run();
	}
}
