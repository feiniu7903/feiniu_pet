package com.lvmama.comm.pet.service.email;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.client.EmailClient;
import com.lvmama.comm.pet.po.email.EmailAttachment;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.service.email.VstEmailService;
import com.lvmama.comm.pet.vo.EmailAttachmentData;

public class VstEmailServiceImpl implements VstEmailService {
	/**
	 * 日志.
	 */
	private static Log logger = LogFactory.getLog(VstEmailServiceImpl.class);
	private EmailClient emailClient;
	
	/**
	 * 新建邮件
	 * @param emailContent
	 * @return
	 * @throws MalformedURLException 
	 */
	public Long sendEmail(EmailContent emailContent){
		return emailClient.sendEmail(emailContent,null);
	}
 
	/**
	 * 新建邮件，并立即发送
	 * @param emailContent
	 * @return
	 * @throws MalformedURLException 
	 */
	public Long sendEmailDirect(EmailContent emailContent){
		return emailClient.sendEmailDirect(emailContent);
	}
 
	 /**
	 * 新建邮件带附件，并立即发送
	 * @param emailContent
	 * @param emailAttachmentList 附件
	 * @return 
	 */
	public Long sendEmailDirectFillAttachment(EmailContent emailContent, List<EmailAttachment> emailAttachmentList){
		return emailClient.sendEmailDirectWithEmailAttachment(emailContent,  emailAttachmentList);
	}
			
	/**
	 * 新建邮件带附件
	 * @param emailContent
	 * @param emailAttachmentList 附件
	 * @return 
	 */
	public Long sendEmailFillAttachment(EmailContent emailContent, List<EmailAttachment> emailAttachmentList){
		return emailClient.sendEmailWithEmailAttachment(emailContent,  emailAttachmentList);
	}
			

	/**
	 * 新建邮件带附件
	 * @param emailContent
	 * @param attachmentFiles 附件
	 * @return 
	 */
	public Long sendEmailFillAttachment(EmailContent emailContent, EmailAttachment emailAttachment){
		List<EmailAttachment> emailAttachments = new ArrayList<EmailAttachment>();
		emailAttachments.add(emailAttachment);
		return emailClient.sendEmailWithEmailAttachment(emailContent, emailAttachments);
	}
	
	public void setEmailClient(EmailClient emailClient) {
		this.emailClient = emailClient;
	}
	 
}
