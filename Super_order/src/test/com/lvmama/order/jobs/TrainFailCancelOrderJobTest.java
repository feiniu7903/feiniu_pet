package com.lvmama.order.jobs;

import static org.junit.Assert.*;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.po.pub.ComJobContent;
import com.lvmama.comm.bee.service.com.ComJobContentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-order-beans.xml" })
public class TrainFailCancelOrderJobTest {

	@Autowired
	private TrainFailCancelOrderJob trainFailCancelOrderJob;
	
	@Autowired
	private ComJobContentService comJobContentService;
	
	@Test
	public void doBefore(){
		ComJobContent content = new ComJobContent();
		content.setPlanTime(DateUtils.addMinutes(content.getCreateTime(), 20));
		content.setJobType(ComJobContent.JOB_TYPE.TRAIN_FAIL_CANCEL_ORDER.name());
		content.setObjectId(2470680L);
		content.setObjectType("ORD_ORDER");
		comJobContentService.add(content);
	}
	
	@Test
	public void testRun() {
		trainFailCancelOrderJob.run();
	}

}
