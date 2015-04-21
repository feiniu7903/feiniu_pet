package com.lvmama.job;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.po.ord.OrdOrderTraffic;
import com.lvmama.comm.bee.po.pub.ComJobContent;
import com.lvmama.comm.bee.service.com.ComJobContentService;
import com.lvmama.comm.bee.service.ord.OrderTrafficService;
import com.lvmama.comm.vo.Constant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-passport-beans.xml" })
public class TrainCreateFailReconnectSupplierJobTest {
	
	@Autowired
	TrainCreateFailReconnectSupplierJob trainCreateFailReconnectSupplierJob;
	
	@Autowired
	private ComJobContentService comJobContentService;
	
	@Autowired
	private OrderTrafficService orderTrafficService;
	
	/*@Test
	public void doBefore() {
		OrdOrderTraffic traffic = new OrdOrderTraffic();
		traffic.setOrderItemMetaId(746636L);
		traffic = orderTrafficService.makeTrafficOrder(traffic);
		
		ComJobContent comJobContent = new ComJobContent();
		comJobContent.setJobType(ComJobContent.JOB_TYPE.TRAIN_CREATE_FALL_RECONNECT_SUPPLIER.name());
		comJobContent.setObjectId(traffic.getOrderTrafficId());
		comJobContent.setObjectType("ORD_ORDER_TRACK");
		comJobContent.setPlanTime(DateUtils.addMinutes(comJobContent.getCreateTime(), 3));
		comJobContentService.add(comJobContent);
	}*/
	
	@Test
	public void testRun() {
		trainCreateFailReconnectSupplierJob.run();
	}

}
