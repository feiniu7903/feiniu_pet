package com.lvmama.comm.pet.po.sms;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SmsReceive implements Serializable {
	private static final long serialVersionUID = 386655741980493919L;
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Log LOG = LogFactory.getLog(SmsReceive.class);
	
	private Long id;  //标识
	private String channelNumber;  //通道号码
	private String mobileNumber;  //手机号码
	private String content;  //内容
	private Date sendDate;  //发送时间
	private Date createDate;  //创建时间

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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getChannelNumber() {
		return channelNumber;
	}
	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public void setSendDate(String sendDate) {
		try {
			this.sendDate = SDF.parse(sendDate);
		} catch (Exception e) {
			LOG.error(sendDate + "不能进行有效的转换");
		}
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
		
}
