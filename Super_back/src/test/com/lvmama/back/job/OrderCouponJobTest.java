package com.lvmama.back.job;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.service.tmall.OrdTmallMapService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-back-beans.xml"})
public class OrderCouponJobTest{
	@Resource
	private DownOrderTmallJob downOrderTmallJob;
	@Resource
	private CreateTmallOrderJob createTmallOrderJob;
	@Resource
	private OrdTmallMapService ordTmallMapService;
	
	@Test
	public void test() {
//		downOrderTmallJob.run();
		//createTmallOrderJob.run();
	//	OrdTmallMap ordTmallMap=ordTmallMapService.checkOrderStatus("222402209796399", 2266l, 2266l);
//		System.out.println(ordTmallMap.getTmallMemo());
	}

}
