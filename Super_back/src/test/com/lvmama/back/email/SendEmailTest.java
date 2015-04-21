package com.lvmama.back.email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.back.message.EContractProcesser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:applicationContext-back-beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class SendEmailTest {
	@Autowired
	private EContractProcesser eContractProcesser;
	@Test
	public void processTest(){
//		Message message=new Message(1306393L,"ORDER_SEND_ECONTRACT","ORDER_SEND_ECONTRACT");
//		eContractProcesser.process(message);
	}

}
