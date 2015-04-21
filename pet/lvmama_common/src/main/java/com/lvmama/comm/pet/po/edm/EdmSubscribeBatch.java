package com.lvmama.comm.pet.po.edm;
/**
 * EDM邮件发送批次类
 * @author shangzhengyuan
 */
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.utils.DateUtil;

public class EdmSubscribeBatch implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6150027285303597965L;
	/**
	 * 批次ID号
	 */
	private Long batchId;
	/**
	 * 邮件标题
	 */
	private String emailSubject;
	/**
	 * 发件人
	 */
	private String sendUserId;
	/**
	 * 发件人邮箱
	 */
	private String sendUserEmail;
	/**
	 * 收件人邮箱列表
	 */
	private String receiveEmailContent;
	/**
	 * 收件人邮箱TXT url
	 */
	private String receiveEmailUrl;
	/**
	 * 任务编号
	 */
	private String taskId;
	/**
	 * 邮件内容HTML url
	 */
	private String emailContentUrl;
	/**
	 * 邮件附件
	 */
	private String emailAttachmentUrl;
	/**
	 * 邮件类型
	 */
	private String emailType;
	/**
	 * 营销类型，用于后台配置通道
	 */
	private String sendType;
	/**
	 * 发送类型
	 */
	private String emailSendType;
	/**
	 * 发送时间
	 */
	private Date  emailSendTime;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 发送成功状态
	 */
	private String success;
	
	private boolean sendEmailSuccess = false;
	/**
	 * 返回消息
	 */
	private String msg;
	/**
	 * 成功数
	 */
	private Integer count;
	/**
	 * 详细状态信息
	 */
	private List<Object> resultList;
	
	/**
	 * email内容
	 */
	private String emailContent;
	/**
	 * 组编号
	 */
	private String groupId;
	/**
	 * 通道名称，汉启|锐致
	 */
	private String channel;
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	public String getEmailSubject() {
		return emailSubject;
	}
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	public String getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}
	public String getSendUserEmail() {
		return sendUserEmail;
	}
	public void setSendUserEmail(String sendUserEmail) {
		this.sendUserEmail = sendUserEmail;
	}
	public String getReceiveEmailContent() {
		return receiveEmailContent;
	}
	public void setReceiveEmailContent(String receiveEmailContent) {
		this.receiveEmailContent = receiveEmailContent;
	}
	public String getReceiveEmailUrl() {
		return receiveEmailUrl;
	}
	public void setReceiveEmailUrl(String receiveEmailUrl) {
		this.receiveEmailUrl = receiveEmailUrl;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getEmailContentUrl() {
		return emailContentUrl;
	}
	public void setEmailContentUrl(String emailContentUrl) {
		this.emailContentUrl = emailContentUrl;
	}
	public String getEmailAttachmentUrl() {
		return emailAttachmentUrl;
	}
	public void setEmailAttachmentUrl(String emailAttachmentUrl) {
		this.emailAttachmentUrl = emailAttachmentUrl;
	}
	public String getEmailType() {
		return emailType;
	}
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
	public String getEmailSendType() {
		return emailSendType;
	}
	public void setEmailSendType(String emailSendType) {
		this.emailSendType = emailSendType;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public Date getEmailSendTime() {
		return emailSendTime;
	}
	public void setEmailSendTime(Date emailSendTime) {
		this.emailSendTime = emailSendTime;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<Object> getResultList() {
		return resultList;
	}
	public void setResultList(List<Object> resultList) {
		this.resultList = resultList;
	}
	
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	public String getFormatSendTime(){
		if(emailSendTime!=null){
			return DateUtil.getFormatDate(emailSendTime,"yyyy-MM-dd HH:mm:ss");
		}
		return null;
	}
	public boolean isSendEmailSuccess() {
		return sendEmailSuccess;
	}
	public void setSendEmailSuccess(boolean sendEmailSuccess) {
		this.sendEmailSuccess = sendEmailSuccess;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	
	
}
