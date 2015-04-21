package com.lvmama.pet.sms.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.BaseTest;
public class SmsServiceImplTest extends BaseTest {
	@Autowired
	private SmsRemoteService smsRemoteService;
	@Autowired
	private ComSmsTemplateService comSmsTemplateService;
	@Test
	public void test() throws Exception {
		Map parameters = new HashMap();
		parameters.put("code", "1234");
//		smsRemoteService.sendSms(SMS_SSO_TEMPLATE.SMS_MOBILE_RESET_PASSWORD, "18930631289 ", parameters);
		String content = comSmsTemplateService.getSmsContent(Constant.SMS_SSO_TEMPLATE.SMS_MOBILE_AUTHENTICATION_CODE.name(), parameters);
		smsRemoteService.sendSms(content, "18602115672",1);
	}
}
