package com.lvmama.job;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.service.TrainDataSyncService;
import com.lvmama.comm.pet.service.search.ProdTrainCacheService;
import com.lvmama.comm.utils.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-passport-beans.xml" })
public class UpdateTrainJobTest {
	
	@Autowired
	UpdateTrainJob updateTrainJob;
	
	@Autowired
	ProdTrainCacheService prodTrainCacheService;
	
	@Autowired
	private TrainDataSyncService trainDataSyncService;

	
	public void testRunCopy() {
		Date date = DateUtil.toDate("2013-09-26", "yyyy-MM-dd");
		prodTrainCacheService.copyDataToNewDay(date);
		System.out.println("done.");
	}
	@Test
	public void testSync(){
		trainDataSyncService.syncTicketPriceInfo("update", "2013-09-27", null, null, null);
	}

}
