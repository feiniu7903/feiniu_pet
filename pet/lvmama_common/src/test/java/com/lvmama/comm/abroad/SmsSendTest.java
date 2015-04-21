package com.lvmama.comm.abroad;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.lvmama.comm.abroad.service.ISendSms;

public class SmsSendTest extends BaseTest {
	@Autowired
	@Qualifier("abroadhotelSendSmsService")
	private ISendSms iSend_Sms;
	
	@Test
	public void sendSmsTest(){
		String orderId="81";
		iSend_Sms.send(orderId);
	}

}
