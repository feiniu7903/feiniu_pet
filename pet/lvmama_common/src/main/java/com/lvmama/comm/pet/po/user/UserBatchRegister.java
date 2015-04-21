package com.lvmama.comm.pet.po.user;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

public class UserBatchRegister implements Serializable{
	private static final long serialVersionUID = 1873444733492898967L;

	private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	
	private Long batchId;
	private Long channelId;
	private String registerType;
	private String remark;
	private String confirmSMS;
	private String customerSMS;
	private String smsTemplate;
	private String customerMail;
	private String mailTemplate;
	private Date createDate;
	private Date registerDate;
	private String cityId;
	private String coupon;
	
	private String channelName;		//渠道名称
	private Integer registerNumber;	//注册总数
	private Integer successNumber;	//成功总数
	private Integer failNumber;		//失败总数
	private Integer replyNumber;  //回复总数
	private Integer visitNumber;  //客服回访总数
	private String oldCustomerSMS;//老会员自定义短信
	private String oldSmsTemplate;//老会员短信模版
	private String oldCoupon;//老会员赠送优惠批次号
	
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	public String getRegisterType() {
		return registerType;
	}
	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCustomerSMS() {
		return customerSMS;
	}
	public void setCustomerSMS(String customerSMS) {
		this.customerSMS = customerSMS;
	}
	public String getSmsTemplate() {
		return smsTemplate;
	}
	public void setSmsTemplate(String smsTemplate) {
		this.smsTemplate = smsTemplate;
	}
	public String getCustomerMail() {
		return customerMail;
	}
	public void setCustomerMail(String customerMail) {
		this.customerMail = customerMail;
	}
	public String getMailTemplate() {
		return mailTemplate;
	}
	public void setMailTemplate(String mailTemplate) {
		this.mailTemplate = mailTemplate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}	
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public Integer getRegisterNumber() {
		return registerNumber;
	}
	public void setRegisterNumber(Integer registerNumber) {
		this.registerNumber = registerNumber;
	}
	public Integer getSuccessNumber() {
		return successNumber;
	}
	public void setSuccessNumber(Integer successNumber) {
		this.successNumber = successNumber;
	}
	public Integer getFailNumber() {
		return failNumber;
	}
	public void setFailNumber(Integer failNumber) {
		this.failNumber = failNumber;
	}
	public Integer getReplyNumber() {
		return replyNumber;
	}
	public void setReplyNumber(Integer replyNumber) {
		this.replyNumber = replyNumber;
	}
	
	public Integer getVisitNumber() {
		return visitNumber;
	}
	public void setVisitNumber(Integer visitNumber) {
		this.visitNumber = visitNumber;
	}
	public String getConfirmSMS() {
		return confirmSMS;
	}
	public void setConfirmSMS(String confirmSMS) {
		this.confirmSMS = confirmSMS;
	}
	
	
	public String getStrRegisterDate() {
		if (null == this.registerDate) {
			return "";
		} else {
			return SDF.format(registerDate);
		}
	}
	
	public String getStrCreateDate() {
		if (null == this.createDate) {
			return "";
		} else {
			return SDF.format(createDate);
		}
	}
	public String getChRegisterType() {
		if (Constant.IMPORT_TYPE.REGISTER_IMMEDIATELY.name().equalsIgnoreCase(this.registerType)) {
			return "立即注册";
		} 
		if (Constant.IMPORT_TYPE.REGISTER_NEED_CONFIRM.name().equalsIgnoreCase(this.registerType)) {
			return "二次注册";
		} 
		return null;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCoupon() {
		return coupon;
	}
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
	
	public String getOldSmsTemplate() {
		return oldSmsTemplate;
	}
	public void setOldSmsTemplate(String oldSmsTemplate) {
		this.oldSmsTemplate = oldSmsTemplate;
	}
	public String getOldCoupon() {
		return oldCoupon;
	}
	public void setOldCoupon(String oldCoupon) {
		this.oldCoupon = oldCoupon;
	}
	public String getOldCustomerSMS() {
		return oldCustomerSMS;
	}
	public void setOldCustomerSMS(String oldCustomerSMS) {
		this.oldCustomerSMS = oldCustomerSMS;
	}

	
	
}
