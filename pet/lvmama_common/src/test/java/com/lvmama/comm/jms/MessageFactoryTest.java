package com.lvmama.comm.jms;

import org.junit.Assert;
import org.junit.Test;

import com.lvmama.comm.vo.Constant;

public class MessageFactoryTest {

	@Test
	public void newOrderTransferPaymentMessageTest() {
		Long orderId = 12L;
		Long orgOrderId = 123L;
		String bizType = "SUPER_ORDER";
		String objectType = "ORD_ORDER";
		Message message = MessageFactory.newOrderTransferPaymentMessage(orderId, orgOrderId, bizType, objectType);
		Assert.assertNotNull(message);
		Assert.assertEquals(orderId, (Long) message.getObjectId());
		Assert.assertEquals("ORD_ORDER", message.getObjectType());
		Assert.assertEquals(Constant.EVENT_TYPE.ORDER_TRANSFER_PAYMENT.name(), message.getEventType());
		Assert.assertEquals(orderId + "," + orgOrderId + "," + bizType + "," + objectType, message.getAddition());
		
		message = MessageFactory.newOrderTransferPaymentMessage(orderId, null, bizType, objectType);
		Assert.assertNotNull(message);
		Assert.assertNull(message.getAddition());
		
		message = MessageFactory.newOrderTransferPaymentMessage(orderId, orgOrderId, null, objectType);
		Assert.assertNotNull(message);
		Assert.assertNull(message.getAddition());
		
		message = MessageFactory.newOrderTransferPaymentMessage(orderId, orgOrderId, bizType, null);
		Assert.assertNotNull(message);
		Assert.assertNull(message.getAddition());	
	}

}
