package com.lvmama.train.service;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.service.prod.ProdTrainService;
import com.lvmama.service.LocalTrainDataSyncService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:applicationContext-passport-beans.xml" })
//@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
//@Transactional
public class TrainClientTest {
	
	TrainClient client = new TrainClient();
	@Autowired
	private ProdTrainService prodTrainService;
	@Autowired
	private LocalTrainDataSyncService localTrainDataSyncService;

	@Test
	public void testExecute() {
		//fail("Not yet implemented");
//		LineStopsRequest request = new LineStopsRequest("G104", DateUtils.addDays(new Date(),1));
//		LineStopsResponse res= client.execute(request);
//		Assert.assertTrue(res.isSuccess());
//		LineInfo info = res.getInfo();
//		Assert.assertEquals(info.getFullName(), "G104");
		localTrainDataSyncService.syncLineInfo("G104", DateUtils.addDays(new Date(),2));
	}
	
	

}
