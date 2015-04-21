package com.lvmama.comm.vo;

import java.io.Serializable;
import java.util.Date;


public class ComBatchUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -500639148899148032L;
	private Long batchUserId;
	private Long batchRegisterId;
	private String mobileNumber;
	private String email;
	private String realName;
	private String gender;
	private Date createDate;
	private String registerStatus;
	private String reply;
	private String userId;
	private Date visitDate;
	private String visitResult;
	private String operator;
	private String channelName;		//渠道名称
	private String remark;
	private Date registerDate;
	private String cityId;
	
	public Long getBatchUserId() {
		return batchUserId;
	}
	public void setBatchUserId(Long batchUserId) {
		this.batchUserId = batchUserId;
	}
	public Long getBatchRegisterId() {
		return batchRegisterId;
	}
	public void setBatchRegisterId(Long batchRegisterId) {
		this.batchRegisterId = batchRegisterId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getRegisterStatus() {
		return registerStatus;
	}
	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	public String getVisitResult() {
		return visitResult;
	}
	public void setVisitResult(String visitResult) {
		this.visitResult = visitResult;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public String getChGender() {
		if ("M".equalsIgnoreCase(gender)) {
			return "男";
		}
		if ("F".equalsIgnoreCase(gender)) {
			return "女";
		}
		return "";
	}
	public String getChRegisterStatus() {
		if (Constant.REGISTER_TYPE.REGISTER_SUCCESS.name().equalsIgnoreCase(registerStatus)) {
			return "注册成功";
		}
		if (Constant.REGISTER_TYPE.REGISTER_FAILURE.name().equalsIgnoreCase(registerStatus)) {
			return "注册失败";
		}
		return "";
	}
	public String getChVisitResult() {
		if (Constant.VISIT_RESULT.REGISTER.name().equalsIgnoreCase(visitResult)) {
			return Constant.VISIT_RESULT.REGISTER.getChName();
		}
		if (Constant.VISIT_RESULT.HASREGISTER.name().equalsIgnoreCase(visitResult)) {
			return Constant.VISIT_RESULT.HASREGISTER.getChName();
		}		
		if (Constant.VISIT_RESULT.NONE_RESPONSE.name().equalsIgnoreCase(visitResult)) {
			return Constant.VISIT_RESULT.NONE_RESPONSE.getChName();
		}
		if (Constant.VISIT_RESULT.BUSY.name().equalsIgnoreCase(visitResult)) {
			return Constant.VISIT_RESULT.BUSY.getChName();
		}
		if (Constant.VISIT_RESULT.POWER_OFF.name().equalsIgnoreCase(visitResult)) {
			return Constant.VISIT_RESULT.POWER_OFF.getChName();
		}
		if (Constant.VISIT_RESULT.STOP.name().equalsIgnoreCase(visitResult)) {
			return Constant.VISIT_RESULT.STOP.getChName();
		}
		if (Constant.VISIT_RESULT.BAD_DATA.name().equalsIgnoreCase(visitResult)) {
			return Constant.VISIT_RESULT.BAD_DATA.getChName();
		}
		if (Constant.VISIT_RESULT.ERROR_DATA.name().equalsIgnoreCase(visitResult)) {
			return Constant.VISIT_RESULT.ERROR_DATA.getChName();
		}
		if (Constant.VISIT_RESULT.EMPTY_NUMBER.name().equalsIgnoreCase(visitResult)) {
			return Constant.VISIT_RESULT.EMPTY_NUMBER.getChName();
		}
		return visitResult;
	}
	
	public String getChReply() {
		if ("Y".equalsIgnoreCase(reply)) {
			return "已回复";
		}
		return "";
	}
	
	public String getNeedVisit() {
		if ("N".equalsIgnoreCase(reply) && null == this.operator) {
			return "true";
		} else {
			return "false";
		}
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
}
