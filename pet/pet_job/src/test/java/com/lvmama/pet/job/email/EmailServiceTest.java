package com.lvmama.pet.job.email;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.service.email.EmailService;
import com.lvmama.pet.job.BaseTest;

public class EmailServiceTest extends BaseTest{
	@Autowired
	private EmailService emailService;
	private int reSendTime=10;
	private int reSendCount=2;
	@Test
	public void getQueuedEmailTest(){
		EmailContent emailContent=emailService.getQueuedEmail(reSendTime,reSendCount);
		if(emailContent!=null){
//			JSONObject json=JSONObject.fromObject(emailContent);
//			System.out.println(json.toString());
		}
	}
	@Test
	public void updateSendStatusTest(){
		EmailContent emailContent=emailService.getQueuedEmail(reSendTime,reSendCount);
		if(emailContent!=null){
			emailContent.setSubject(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
			emailService.updateSendStatus(emailContent);
//			JSONObject json=JSONObject.fromObject(emailContent);
//			System.out.println(json.toString());
		}
	}
//	@Test
	public void insertTest(){
		EmailContent emailContent=new EmailContent();
		emailContent.setContentFileId(1000L);
		emailContent.setCreateTime(new Date());
//		emailContent.setEmailAttachments(emailAttachments)
		emailContent.setFromAddress("ruanxiequan@lvmama.com");
		emailContent.setFromName("ruanx");
		emailContent.setSendCount(0);
		emailContent.setStatus("UNSEND");
		emailContent.setSubject("test");
		emailContent.setToAddress("ruanxiequan@lvmama.com");
		emailService.insert(emailContent);
//		JSONObject json=JSONObject.fromObject(emailContent);
//		System.out.println(json.toString());
	}
}
