package com.lvmama.pet.email;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.service.email.EmailService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.BaseTest;

public class EmailServiceTest extends BaseTest{
	@Autowired
	private EmailService emailService;
	private int reSendTime=10;
	private int reSendCount=2;
	@Test
	public void getQueuedEmailTest(){
		EmailContent emailContent=emailService.getQueuedEmail(reSendTime,reSendCount);
		Assert.assertNotNull(emailContent.getContentId());
		Assert.assertEquals(emailContent.getStatus(), Constant.emailStatus.SENDING.name());
	}
	@Test
	public void getEmailDirectSend(){
		EmailContent emailContent=emailService.getEmailDirectSend(40L);
		Assert.assertEquals(emailContent.getStatus(), Constant.emailStatus.SENDING.name());
	}
	@Test
	public void updateSendStatus(){
		EmailContent emailContent = new EmailContent();
		emailContent.setContentId(2L);
		emailContent.setStatus(Constant.emailStatus.FAIL.name());
		boolean b = emailService.updateSendStatus(emailContent);
		Assert.assertEquals(emailContent.getStatus(), Constant.emailStatus.FAIL.name());
	}
	@Test
	public void getEmailContent(){
		Long id = 2L;
		EmailContent emailContent = emailService.getEmailContent(id);
	}
	@Test
	public void insert(){
		EmailContent emailContent = new EmailContent();
		emailContent.setContentId(1L);
		emailContent.setContentId(22L);
		emailContent.setContentText("sdfsadfas");
		emailContent.setCreateTime(new Date());
		emailContent.setFromAddress("test@lvmama.com");
		emailContent.setFromName("test");
		emailContent.setSubject("test");
		emailContent.setToAddress("heweia@lvmama.com");
		emailService.insert(emailContent);
	}
	@Test
	public void queryByParamCount(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("toAddress", "%ruanxiequan%");
		emailService.queryByParamCount(params);
	}
	@Test
	public void queryByParamList(){
		Map<String,Object> params = new HashMap<String,Object>();
		emailService.queryByParamList(params);
	}
	@Test
	public void queryByContentId(){
		Long id = 1L;
		emailService.queryByContentId(id);
	}
}
