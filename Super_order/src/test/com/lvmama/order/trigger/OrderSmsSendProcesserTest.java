package com.lvmama.order.trigger;



import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class OrderSmsSendProcesserTest {

	@Resource
	private TopicMessageProducer orderMessageProducer;

	 
	@Test
	public final void testProcess() {
		 //确认凭证isOrderApproveMsg
		Long orderId = 1321568L;
		orderMessageProducer.sendMsg(MessageFactory
				.newOrderApproveMessage(orderId));
	}
}
