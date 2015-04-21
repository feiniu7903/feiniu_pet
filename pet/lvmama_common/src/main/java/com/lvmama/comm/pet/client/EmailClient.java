package com.lvmama.comm.pet.client;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.email.EmailAttachment;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.service.email.EmailService;
import com.lvmama.comm.pet.vo.EmailAttachmentData;
import com.lvmama.comm.vo.Constant;

public class EmailClient {
	/**
	 * 日志.
	 */
	private static Log logger = LogFactory.getLog(EmailClient.class);
	private EmailService emailRemoteService;
	private FSClient fsClient ;
	private TopicMessageProducer resourceMessageProducer;
	
	/**
	 * 新建邮件
	 * @param emailContent
	 * @return
	 * @throws MalformedURLException 
	 */
	public Long sendEmail(EmailContent emailContent){
		emailContent.setStatus(Constant.emailStatus.UNSEND.name());
		return insert(emailContent,null);
	}
	
	/**
	 * 新建邮件带附件
	 * @param emailContent
	 * @param attachmentFiles 附件
	 * @return 
	 */
	public Long sendEmail(EmailContent emailContent,List<EmailAttachmentData> emailAttachmentData){
		emailContent.setStatus(Constant.emailStatus.UNSEND.name());
		return insert(emailContent,emailAttachmentData);
	}
	
	/**
	 * 新建邮件带附件
	 * @param emailContent
	 * @param attachmentFiles 附件
	 * @return 
	 */
	public Long sendEmailWithEmailAttachment(EmailContent emailContent, List<EmailAttachment> emailAttachmentList){
		emailContent.setStatus(Constant.emailStatus.UNSEND.name());
		return insertEmailContent(emailContent, emailAttachmentList);
	}
	
	/**
	 * 新建邮件，并立即发送
	 * @param emailContent
	 * @return
	 * @throws MalformedURLException 
	 */
	public Long sendEmailDirect(EmailContent emailContent){
		emailContent.setStatus(Constant.emailStatus.SENDING.name());
		Long contentId=insert(emailContent,null);
		if(contentId.longValue()>0){
			resourceMessageProducer.sendMsg(MessageFactory.newEmailMessage(contentId));
		}
		return contentId;
	}
	/**
	 * 新建邮件带附件，并立即发送
	 * @param emailContent
	 * @param attachmentFiles 附件
	 * @return 
	 */
	public Long sendEmailDirect(EmailContent emailContent,List<EmailAttachmentData> emailAttachmentData){
		emailContent.setStatus(Constant.emailStatus.SENDING.name());
		Long contentId=insert(emailContent,emailAttachmentData);
		if(contentId.longValue()>0){
			resourceMessageProducer.sendMsg(MessageFactory.newEmailMessage(contentId));
		}
		return contentId;
	}
	
	/**
	 * 新建邮件带附件，并立即发送
	 * @param emailContent
	 * @param attachmentFiles 附件
	 * @return 
	 */
	public Long sendEmailDirectWithEmailAttachment(EmailContent emailContent, List<EmailAttachment> emailAttachmentList){
		emailContent.setStatus(Constant.emailStatus.SENDING.name());
		Long contentId = insertEmailContent(emailContent, emailAttachmentList);
		if(contentId.longValue()>0){
			resourceMessageProducer.sendMsg(MessageFactory.newEmailMessage(contentId));
		}
		return contentId;
	}
	
	private Long insert(EmailContent emailContent,List<EmailAttachmentData> emailAttachmentData){
		byte[] contentText=null;
		if(emailContent.getContentText()==null){
			emailContent.setContentText("");
		}
		if(!StringUtils.isNotEmpty(emailContent.getToAddress())){
			logger.error("Send mail fail->Mail receiving address is empty!");
			return 0L;
		}
		try {
			contentText=emailContent.getContentText().getBytes("UTF-8");
			Long fileId = fsClient.uploadFile(Constant.COM_FS_SERVER_TYPE.EMAIL_FILE.name()+System.currentTimeMillis(),
					contentText, Constant.COM_FS_SERVER_TYPE.EMAIL_FILE.name());
			if(fileId.intValue()!=0){
				if(emailAttachmentData!=null && emailAttachmentData.size()>0){
					List<EmailAttachment> emailAttachments=new ArrayList<EmailAttachment>();
					for(EmailAttachmentData item:emailAttachmentData){
						Long itemFileId = fsClient.uploadFile(item.getFileName(),
								item.getData(), Constant.COM_FS_SERVER_TYPE.EMAIL_FILE.name());
						if(itemFileId.intValue()!=0){
							EmailAttachment emailAttachment=new EmailAttachment();
							emailAttachment.setFileId(itemFileId);
							emailAttachment.setFileName(item.getFileName());
							emailAttachments.add(emailAttachment);
						}else{
							logger.error("Send mail fail->Upload file fail!");
							return 0L;
						}
					}
					emailContent.setEmailAttachments(emailAttachments);
				}
				emailContent.setContentFileId(fileId);
				Long contentId=emailRemoteService.insert(emailContent);
				if(contentId>0){
					logger.info("Send mail success! email_content_id="+contentId);
				}else{
					logger.error("Send mail fail!");
				}
				return contentId;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Send mail fail->"+e.getMessage());
		}
		logger.error("Send mail fail!");
		return 0L;
	}
	
	private Long insertEmailContent(EmailContent emailContent, List<EmailAttachment> emailAttachmentList){
		byte[] contentText=null;
		if(emailContent.getContentText()==null){
			emailContent.setContentText("");
		}
		if(!StringUtils.isNotEmpty(emailContent.getToAddress())){
			logger.error("Send mail fail->Mail receiving address is empty!");
			return 0L;
		}
		try {
			contentText=emailContent.getContentText().getBytes("UTF-8");
			Long fileId = fsClient.uploadFile(Constant.COM_FS_SERVER_TYPE.EMAIL_FILE.name()+System.currentTimeMillis(),
					contentText, Constant.COM_FS_SERVER_TYPE.EMAIL_FILE.name());
			if(fileId.intValue()!=0){
				if(emailAttachmentList!=null && emailAttachmentList.size()>0){
					emailContent.setEmailAttachments(emailAttachmentList);
				}
				emailContent.setContentFileId(fileId);
				Long contentId=emailRemoteService.insert(emailContent);
				if(contentId>0){
					logger.info("Send mail success! email_content_id="+contentId);
				}else{
					logger.error("Send mail fail!");
				}
				return contentId;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Send mail fail->"+e.getMessage());
		}
		logger.error("Send mail fail!");
		return 0L;
	}
	
	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}

	public void setEmailRemoteService(EmailService emailRemoteService) {
		this.emailRemoteService = emailRemoteService;
	}

	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}
}
