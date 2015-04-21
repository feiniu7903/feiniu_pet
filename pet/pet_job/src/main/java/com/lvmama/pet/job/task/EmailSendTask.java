package com.lvmama.pet.job.task;

import java.util.ArrayList;
import java.util.Date;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.po.email.EmailAttachment;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.service.email.EmailService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.job.util.TestResources;

/**
 * 
 * 这个类的对象是非singleton的，即Spring的prototype的，每一个发送任务都必须是一个单独的对象
 * @author Alex Wang
 *
 */
public class EmailSendTask implements Runnable{
	private static Log LOG = LogFactory.getLog(EmailSendTask.class);
	private FSClient fsClient;
	private JavaMailSender javaMailSender;
	private MimeMessage mimeMessage;
	private EmailService emailService;
	private EmailContent content;
	private String fromAddress;
	private String fromName;
	private boolean initStatus=true;
	public void initEmailContent(EmailContent content) {
		this.content = content; 
		this.mimeMessage = javaMailSender.createMimeMessage();
		try{
			MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(mimeMessage,true,"utf-8");
			if(StringUtils.isNotEmpty(content.getFromAddress())){
				fromAddress=content.getFromAddress();
			}
			if(StringUtils.isNotEmpty(content.getFromName())){
				fromName=content.getFromName();
			}
			InternetAddress from = new InternetAddress(fromAddress, fromName);
			mimeMessageHelper.setFrom(from);
			//查看是否是发送到测试环境
			if(TestResources.getInstance().emailJobIsTest()){
				LOG.info("[发送邮件]邮件测试发送,若要正式发送,请修改test.properties配置文件!");
				mimeMessageHelper.setTo(new String[]{TestResources.getInstance().getTestToEmail()});
			}else{
				//真实发送
				mimeMessageHelper.setTo(content.getToAddress().split(","));
			}
			mimeMessageHelper.setSubject(content.getSubject());
            mimeMessageHelper.setSentDate(new Date());
			if(!StringUtil.isEmptyString(content.getCcAddress())){
				mimeMessageHelper.setCc(content.getCcAddress().split(","));
			}
            ComFile comFile=fsClient.downloadFile(content.getContentFileId());
            mimeMessageHelper.setText(new String(comFile.getFileData(),"UTF-8"),true);
            if(content.getEmailAttachments()!=null && content.getEmailAttachments().size()>0){
            	for(EmailAttachment item:content.getEmailAttachments()){
            		ComFile file=fsClient.downloadFile(item.getFileId());
            		InputStreamSource in=new ByteArrayResource(file.getFileData());
            		if(file.getFileName().endsWith("html")){
            			mimeMessageHelper.addAttachment(file.getFileName(), in,"text/html; charset=UTF-8");
            		}else{
            			mimeMessageHelper.addAttachment(file.getFileName(), in);
            		}
            	}
            }
		}catch(Exception e) {
			e.printStackTrace();
			initStatus=false;
		}
	}
	@Override
	public void run() {
		LOG.info("EmailSendTask runing...");
		String status=Constant.emailStatus.FAIL.name();
		try{
			if(initStatus){
				LOG.info("EmailSendTask to:"+content.getToAddress()+" cc:"+content.getCcAddress());
				javaMailSender.send(mimeMessage);
				status=Constant.emailStatus.SUCCESS.name();
			}
		}catch(MailException e){
			e.printStackTrace();
		}finally{
			content.setSendTime(new Date());
			content.setStatus(status);
			emailService.updateSendStatus(content);
			LOG.info("EmailSendTask sendTime:"+content.getSendTime()+" status:"+content.getStatus());
		}
		LOG.info("EmailSendTask end...");
	}
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
}
