package com.lvmama.pet.job;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.pet.job.quartz.CmtStatisticsScoreJob;

public class CmtStatisticsScoreJobTest extends BaseTest {
	
	@Autowired
	private CmtStatisticsScoreJob cmtStatisticsScoreJob; 
	
//	@Test
	public void runTest(){
		try {
			cmtStatisticsScoreJob.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		 
}

