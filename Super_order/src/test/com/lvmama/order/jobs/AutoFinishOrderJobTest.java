/**
 * 
 */
package com.lvmama.order.jobs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author liuyi
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-order-beans.xml" })
public class AutoFinishOrderJobTest {
	
	@Autowired
	private AutoFinishOrderJob autoFinishOrderJob;
	
	@Test
	public void test()
	{
		try {
			autoFinishOrderJob.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
