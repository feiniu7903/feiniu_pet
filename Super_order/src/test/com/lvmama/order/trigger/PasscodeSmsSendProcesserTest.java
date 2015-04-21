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
public class PasscodeSmsSendProcesserTest {

	@Resource
	private TopicMessageProducer passportMessageProducer;
	@Test
	public void testProcess() {

		 //确认凭证
		//Long singlecodeId=269685L;
		Long codeId = 270156L;//269688L;// 269686L;
		passportMessageProducer.sendMsg(MessageFactory
				.newPasscodeApplyMessage(codeId));
	}

}
