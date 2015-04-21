package com.lvmama.pet.job.email;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import com.lvmama.comm.pet.client.EmailClient;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.service.email.EmailService;
import com.lvmama.comm.pet.service.lvmamacard.LvmamacardService;
import com.lvmama.comm.pet.vo.EmailAttachmentData;
import com.lvmama.pet.job.BaseTest;
import com.lvmama.pet.job.task.EmailSendTask;

public class EmailSenderTest extends BaseTest {
	@Autowired
	private EmailSendTask emailSendTask;
	@Autowired
	private EmailService emailService;
	@Autowired
	private TaskExecutor emailSendTaskExecutor;
	@Autowired
	private EmailClient emailClient;
	private int reSendTime=10;
	private int reSendCount=2;
	@Autowired
	private LvmamacardService lvmamacardService;
 	@Test
	public void emailSendTaskStartTest(){
 		EmailContent content = emailService.getQueuedEmail(reSendTime,reSendCount);
		if (content!=null) {
			emailSendTask.initEmailContent(content);
			emailSendTaskExecutor.execute(emailSendTask);
		}
	}
 	@Test
	public void sendFileTest(){
		File file=new File("D:/Contract(1306393).pdf");
		byte[] b = new byte[1024];
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			FileInputStream fi = new FileInputStream(file);
			while(fi.read(b)!=-1){
				bos.write(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		Long fileId=comFSService.uploadFileByBytes(file.getName(),bos.toByteArray(), "EMAILFILE");
//		System.out.println("--------------------"+fileId);
	}
//	@Test
//	public void getFileTest(){
//		ComFile file=comFSService.downloadFile(5L);
//		System.out.println("------------------"+file.getFileName());
//	}
	@Test
	public void sendEmailTest(){
		EmailContent emailContent=new EmailContent();
/*		emailContent.setFromAddress("service@cs.lvmama.com");
*/		emailContent.setFromName("驴妈妈");
		emailContent.setSubject("subject");
		emailContent.setContentText("ceshi123");
		emailContent.setToAddress("nixianjun@lvmama.com,zhongshuangxi@lvmama.com");
		
		List<EmailAttachmentData> files = new ArrayList<EmailAttachmentData>();
		// 增加附件
 		 EmailAttachmentData data = new EmailAttachmentData(new File("C://Users/nixianjun/Desktop/1.xlsx"));
		 files.add(data);
		emailClient.sendEmailDirect(emailContent, files);
		//emailClient.sendEmail(emailContent);
	}
}
