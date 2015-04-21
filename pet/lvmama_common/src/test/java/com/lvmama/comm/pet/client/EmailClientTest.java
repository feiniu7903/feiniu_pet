package com.lvmama.comm.pet.client;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.service.email.EmailService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.pet.vo.EmailAttachmentData;

@ContextConfiguration(locations = { "classpath:/applicationContext-lvmama-comm-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class EmailClientTest {
	@Autowired
	private EmailClient emailClient;
	@Autowired
	private EmailService emailRemoteService;
	@Autowired
	private FSClient fsClient ;
//	@Test
	public void sendEmail(){
		EmailContent emailContent=new EmailContent();
		emailContent.setContentText("测试内容");
		emailContent.setFromAddress("from@lvmama.com");
		emailContent.setFromName("from");
		emailContent.setSubject("标题");
		emailContent.setToAddress("to@lvmama.com");
		emailClient.sendEmail(emailContent);
	}
//	@Test
	public void getEmail() throws UnsupportedEncodingException{
		EmailContent emailContent=emailRemoteService.getQueuedEmail(10, 3);
		ComFile file=fsClient.downloadFile(emailContent.getContentFileId());
		System.out.println(file.getFileName());
		System.out.println(new String(file.getFileData(),"UTF-8"));
	}
//	@Test
	public void getEmailDirect() throws UnsupportedEncodingException{
		EmailContent emailContent=emailRemoteService.getEmailDirectSend(39L);
		ComFile file=fsClient.downloadFile(emailContent.getContentFileId());
		System.out.println(file.getFileName());
		System.out.println(new String(file.getFileData(),"UTF-8"));
	}
//	@Test
	public void sendEmail2(){
		EmailContent emailContent=new EmailContent();
		emailContent.setContentText("ceshi1dddddddddd23");
		emailContent.setFromAddress("ruanxiequan@lvmama.com");
		emailContent.setFromName("rxq");
		emailContent.setSubject("subject");
		emailContent.setToAddress("ruanxiequan@lvmama.com");
		emailClient.sendEmailDirect(emailContent);
	}
//	@Test
	public void sendEmail3(){
		EmailContent emailContent=new EmailContent();
		emailContent.setContentText("ceshi1dddddddddd23");
		emailContent.setFromAddress("ruanxiequan@lvmama.com");
		emailContent.setFromName("rxq");
		emailContent.setSubject("subject");
		emailContent.setToAddress("ruanxie<ruanxiequan@lvmama.com>");
		emailClient.sendEmailDirect(emailContent);
	}
//	@Test
	public void sendEmail4(){
		EmailContent emailContent=new EmailContent();
		emailContent.setContentText("ceshi1dddddddddd23");
		emailContent.setFromAddress("ruanxiequan@lvmama.com");
		emailContent.setFromName("rxq");
		emailContent.setSubject("subject");
		emailContent.setToAddress("ruanxiequan@lvmama.com,阮谢全<rxqfj@126.com>");
		emailClient.sendEmailDirect(emailContent);
	}
	@Test
	public void sendEmail5(){
		EmailContent emailContent=new EmailContent();
		emailContent.setContentText("ceshi1dddddddddd23");
		emailContent.setFromAddress("ruanxiequan@lvmama.com");
		emailContent.setFromName("rxq");
		emailContent.setSubject("subject");
		emailContent.setToAddress("ruanxiequan@lvmama.com,阮谢全<rxqfj@126.com>");
		
		List<EmailAttachmentData> lst=new ArrayList<EmailAttachmentData>();
		EmailAttachmentData data=new EmailAttachmentData(new File("D:/attTest/2007Test.docx"));
		EmailAttachmentData data2=new EmailAttachmentData(new File("D:/attTest/学习计划-阮谢全.docx"));
		lst.add(data);
		lst.add(data2);
		emailClient.sendEmail(emailContent,lst);
	}
}
