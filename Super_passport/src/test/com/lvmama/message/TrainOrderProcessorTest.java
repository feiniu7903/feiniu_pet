package com.lvmama.message;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.spring.SpringBeanProxy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-passport-beans.xml" })
public class TrainOrderProcessorTest implements ApplicationContextAware{

	@Autowired
	TrainOrderProcessor trainOrderProcessor;
	
	@Test
	public void testProcess() {
		trainOrderProcessor.process(MessageFactory.newOrderPaymentMessage(1324406L));
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		SpringBeanProxy.setApplicationContext(arg0);
	}

}
