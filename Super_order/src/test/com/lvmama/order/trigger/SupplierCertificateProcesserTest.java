package com.lvmama.order.trigger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class SupplierCertificateProcesserTest {
	@Autowired
	private SupplierCertificateProcesser supplierCertificateProcesser;

	@Test
	public void testProcess() {
		 //确认凭证
		Long orderId = 1986530L;
		Message message = MessageFactory.newOrderCreateMessage(orderId);
		supplierCertificateProcesser.process(message);
	}

}
