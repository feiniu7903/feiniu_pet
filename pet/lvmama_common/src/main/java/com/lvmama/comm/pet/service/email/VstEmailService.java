package com.lvmama.comm.pet.service.email;

import java.net.MalformedURLException;
import java.util.List;

import com.lvmama.comm.pet.po.email.EmailAttachment;
import com.lvmama.comm.pet.po.email.EmailContent;

public interface VstEmailService {

	/**
	 * 新建邮件
	 * @param emailContent
	 * @throws MalformedURLException 
	 */
	public Long sendEmail(EmailContent emailContent);
	
	/**
	 * 新建邮件带附件
	 * @param emailContent
	 * @param attachmentFiles 附件
	 */
	public Long sendEmailFillAttachment(EmailContent emailContent,List<EmailAttachment> emailAttachmentData);
	
	/**
	 * 新建邮件，并立即发送
	 * @param emailContent
	 * @throws MalformedURLException 
	 */
	public Long sendEmailDirect(EmailContent emailContent);
	
	/**
	 * 新建邮件带附件，并立即发送
	 * @param emailContent
	 * @param attachmentFiles 附件
	 */
	public Long sendEmailDirectFillAttachment(EmailContent emailContent,List<EmailAttachment> emailAttachmentData);
	
	/**
	 * 新建邮件带附件
	 * @param emailContent
	 * @param attachmentFiles 附件
	 * @return 
	 */
	public Long sendEmailFillAttachment(EmailContent emailContent, EmailAttachment emailAttachment);
	
}
