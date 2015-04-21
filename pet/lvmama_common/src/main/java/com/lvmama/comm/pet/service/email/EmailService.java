package com.lvmama.comm.pet.service.email;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.email.EmailAttachment;
import com.lvmama.comm.pet.po.email.EmailContent;

public interface EmailService {
	
	/**
	 * 获取待发送邮件
	 * @param reSendTime 超时重发，分钟
	 * @param reSendCount 失败重发次数
	 * @return
	 */
	public EmailContent getQueuedEmail(int reSendTime,int reSendCount);
	
	/**
	 * 获取待发送邮件,并设为发送中
	 * @param contentId
	 * @return
	 */
	public EmailContent getEmailDirectSend(Long contentId);
	
	/**
	 * 更改邮件发送状态
	 * @param content
	 * @return
	 */
	public boolean updateSendStatus(EmailContent content);
	
	/**
	 * 查询邮件信息
	 * @param getEmailContent
	 * @return
	 */
	public EmailContent getEmailContent(Long contentId);
	
	/**
	 * 新建邮件
	 * @param emailContent
	 * @return
	 */
	public Long insert(EmailContent emailContent);
	
	/**
	 * 条件查询邮件数量
	 * @param params
	 * @return
	 */
	public Long queryByParamCount(Map<String,Object> params);
	
	/**
	 * 条件查询邮件列表
	 * @param params
	 * @return
	 */
	public List<EmailContent> queryByParamList(Map<String,Object> params);
	
	/**
	 * 查询附件
	 * @param mailContentId
	 * @return
	 */
	public List<EmailAttachment> queryByContentId(Long mailContentId);
}
