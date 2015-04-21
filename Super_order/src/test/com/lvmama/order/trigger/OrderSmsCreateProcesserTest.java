package com.lvmama.order.trigger;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.spring.SpringBeanProxy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class OrderSmsCreateProcesserTest implements ApplicationContextAware{
	@Resource
	private TopicMessageProducer orderMessageProducer;

	@Test
	public final void testProcess() {
		orderMessageProducer.sendMsg(MessageFactory
				.newTrafficIssueRefundMessage(1379551L));
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		SpringBeanProxy.setApplicationContext(arg0);
	}

}
