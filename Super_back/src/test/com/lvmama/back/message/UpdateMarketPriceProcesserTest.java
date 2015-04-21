package com.lvmama.back.message;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.spring.SpringBeanProxy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-back-beans.xml" })
public class UpdateMarketPriceProcesserTest implements ApplicationContextAware{

	@Resource
	private UpdateMarketPriceProcesser updateMarketPriceProcesser;
	
	
	@Test
	public void testProcess() {
//		Message message=MessageFactory.newProductMetaPriceMessage(27961L);
//		updateMarketPriceProcesser.process(message);
	}



	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		SpringBeanProxy.setApplicationContext(arg0);
	}

}
