package com.lvmama.pet.email.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.comm.pet.po.email.EmailAttachment;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.service.email.EmailService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.email.dao.EmailAttachmentDAO;
import com.lvmama.pet.email.dao.EmailContentDAO;
public class EmailServiceImpl implements EmailService {
	
	private EmailContentDAO emailContentDAO;
	private EmailAttachmentDAO emailAttachmentDAO;
	@Override
	public EmailContent getQueuedEmail(int reSendTime,int reSendCount) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("sendTimeTo", DateUtils.addMinutes(new Date(), reSendTime * (-1)));
		params.put("sendCountTo", reSendCount);
		List<EmailContent> list=emailContentDAO.getQueuedEmailList(params);
		if(list!=null && list.size()>0){
			EmailContent emailContent=list.get(0);
			emailContent.setSendCount(emailContent.getSendCount()+1);
			emailContent.setSendTime(new Date());
			emailContent.setStatus(Constant.emailStatus.SENDING.name());
			emailContent.setEmailAttachments(emailAttachmentDAO.queryByContentId(emailContent.getContentId()));
			emailContentDAO.updateByPrimaryKey(emailContent);
			return emailContent;
		}else{
			return null;
		}
	}
	@Override
	public EmailContent getEmailDirectSend(Long contentId) {
		EmailContent emailContent=emailContentDAO.selectByPrimaryKey(contentId);
		if(emailContent!=null){
			emailContent.setSendCount(emailContent.getSendCount()+1);
			emailContent.setSendTime(new Date());
			emailContent.setStatus(Constant.emailStatus.SENDING.name());
			emailContent.setEmailAttachments(emailAttachmentDAO.queryByContentId(emailContent.getContentId()));
			emailContentDAO.updateByPrimaryKey(emailContent);
		}
		return emailContent;
	}
	@Override
	public boolean updateSendStatus(EmailContent content) {
		boolean flag=false;
		int count=emailContentDAO.updateByPrimaryKeySelective(content);
		if(count>0){
			flag=true;
		}
		return flag;
	}
	@Override
	public Long insert(EmailContent emailContent) {
		emailContent.setCreateTime(new Date());
		emailContent.setSendCount(0);
		emailContentDAO.insert(emailContent);
		if(emailContent.getEmailAttachments()!=null && emailContent.getEmailAttachments().size()>0){
			for(EmailAttachment item:emailContent.getEmailAttachments()){
				item.setMailContentId(emailContent.getContentId());
				emailAttachmentDAO.insert(item);
			}
		}
		return emailContent.getContentId();
	}
	@Override
	public EmailContent getEmailContent(Long contentId) {
		return emailContentDAO.selectByPrimaryKey(contentId);
	}

	@Override
	public Long queryByParamCount(Map<String, Object> params) {
		return emailContentDAO.queryByParamCount(params);
	}

	@Override
	public List<EmailContent> queryByParamList(Map<String, Object> params) {
		return emailContentDAO.queryByParamList(params);
	}
	
	@Override
	public List<EmailAttachment> queryByContentId(Long mailContentId) {
		return emailAttachmentDAO.queryByContentId(mailContentId);
	}
	
	public void setEmailContentDAO(EmailContentDAO emailContentDAO) {
		this.emailContentDAO = emailContentDAO;
	}

	public void setEmailAttachmentDAO(EmailAttachmentDAO emailAttachmentDAO) {
		this.emailAttachmentDAO = emailAttachmentDAO;
	}
}
