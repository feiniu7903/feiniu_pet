package com.lvmama.comm.pet.po.sms;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SmsContentLog extends SmsContent {
	private static final long serialVersionUID = -2358059871141190938L;
	
	//短信发送成功
	public static final Integer REPORT_FOR_SUCCESS = 0;
	//短信发送失败
	public static final Integer REPORT_FOR_ERROR = 1;
	//一定时间内未能收到短信报告
	public static final Integer REPORT_FOR_TIMEOUT = 9;
	
	
	//原smsContent表的标示
	private Long smsId;
	//短信分拆的条数
	private Integer numbers;
	//发送日期
	private Date actualSendDate;
	//状态，0:发送成功,1:发送失败,2:待发送,3:推送失败,4:已发送等待状态报告,5:发送失败已重发,6:推送失败已重发,无:已发送等待状态报告
	private Integer reportStatus;
	//用户接受时间
	private Date receiveDate;
	private String statusGB;
	/**
	 * 被重发的短信的id，对应于sms_content表的id
	 */
	protected Long resendSmsId;
	
	protected SmsContentLog(){	
	}

	/**
	 * Constructor
	 * @param content 内容
	 * @param mobile 目标手机号
	 * @throws InvalidSmsException
	 */
	public SmsContentLog(final String content, final String mobile) throws Exception {
		super(content, mobile);
		numbers = 1;
		actualSendDate = new Date();
	}
	
	/**
	 * 默认的将短信转换成短信日志
	 * @param smsContent 短信
	 * @return
	 * @throws InvalidSmsException
	 */
	public static SmsContentLog convertToSmsContentLog(final SmsContent smsContent) throws Exception {
		if (null == smsContent) {
			throw new Exception("null值无法进行转换");
		}
		SmsContentLog log = new SmsContentLog(smsContent.getContent(), smsContent.getMobile());
		log.setSmsId(smsContent.getId());
		log.setSerialId(smsContent.getSerialId());
		log.setPriority(smsContent.getPriority());
		log.setType(smsContent.getType());
		log.setSendDate(smsContent.getSendDate());
		log.setFailure(smsContent.getFailure());
		log.setNumbers(1);
		log.setActualSendDate(new Date());
		log.setData(smsContent.getData());
		log.setUserId(smsContent.getUserId());
		log.setMemo(smsContent.getMemo());
		log.setChannel(smsContent.getChannel());
		log.setReportStatus(smsContent.getStatus());
		log.setResendSmsId(smsContent.getResendSmsId());
		return log;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}
	
	//setter and getter
	public Long getSmsId() {
		return smsId;
	}
	public void setSmsId(Long smsId) {
		this.smsId = smsId;
	}
	public Integer getNumbers() {
		return numbers;
	}
	public void setNumbers(Integer numbers) {
		this.numbers = numbers;
	}
	public Date getActualSendDate() {
		return actualSendDate;
	}
	public void setActualSendDate(Date actualSendDate) {
		this.actualSendDate = actualSendDate;
	}
	public Integer getReportStatus() {
		return reportStatus;
	}
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public void setReportStatus(Integer reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String getStatusGB() {
		return statusGB;
	}

	public void setStatusGB(String statusGB) {
		this.statusGB = statusGB;
	}

	public Long getResendSmsId() {
		return resendSmsId;
	}

	public void setResendSmsId(Long resendSmsId) {
		this.resendSmsId = resendSmsId;
	}
	
}
