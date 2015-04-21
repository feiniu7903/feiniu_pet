package com.lvmama.tnt.user.po;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.tnt.recognizance.po.TntRecognizance;

/**
 * 分销商
 * 
 * @author gaoxin
 * 
 */
public class TntUser implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	private Long userId;
	private String userName;
	private String realName;
	private String loginPassword;
	private String payPassword;
	private String isOnline;

	private java.util.Date lastLoginDate;
	private String isCompany;

	protected String mobileNumber;

	protected String email;

	/**
	 * 与detail关联
	 */
	private TntUserDetail detail = new TntUserDetail();
	/**
	 * 与保证金账户关联
	 */
	private TntRecognizance recognizance;

	public TntRecognizance getRecognizance() {
		return recognizance;
	}

	public void setRecognizance(TntRecognizance recognizance) {
		this.recognizance = recognizance;
	}

	/** 临时字段 ------------------------- **/

	private String queryStartDate;// 申请日期查询开始时间
	private String queryEndDate;// 申请日期查询结束时间

	private String queryStartContractStartDate;// 合同开始日期查询开始时间
	private String queryEndContractStartDate;// 合同开始日期查询结束时间
	private String queryStartContractEndDate;// 合同结束日期查询开始时间
	private String queryEndContractEndDate;// 合同结束日期查询结束时间

	public String getQueryStartContractStartDate() {
		return queryStartContractStartDate;
	}

	public void setQueryStartContractStartDate(
			String queryStartContractStartDate) {
		this.queryStartContractStartDate = queryStartContractStartDate;
	}

	public String getQueryEndContractStartDate() {
		return queryEndContractStartDate;
	}

	public void setQueryEndContractStartDate(String queryEndContractStartDate) {
		this.queryEndContractStartDate = queryEndContractStartDate;
	}

	public String getQueryStartContractEndDate() {
		return queryStartContractEndDate;
	}

	public void setQueryStartContractEndDate(String queryStartContractEndDate) {
		this.queryStartContractEndDate = queryStartContractEndDate;
	}

	public String getQueryEndContractEndDate() {
		return queryEndContractEndDate;
	}

	public void setQueryEndContractEndDate(String queryEndContractEndDate) {
		this.queryEndContractEndDate = queryEndContractEndDate;
	}

	public TntUserDetail getDetail() {
		return detail;
	}

	public String getQueryStartDate() {
		return queryStartDate;
	}

	public void setQueryStartDate(String queryStartDate) {
		this.queryStartDate = queryStartDate;
	}

	public String getQueryEndDate() {
		return queryEndDate;
	}

	public void setQueryEndDate(String queryEndDate) {
		this.queryEndDate = queryEndDate;
	}

	public void setDetail(TntUserDetail detail) {
		this.detail = detail;
	}

	public TntUser() {
	}

	public TntUser(Long userId) {
		this.userId = userId;
	}

	public TntUser(TntUserDetail detail) {
		this.detail = detail;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserName(String value) {
		this.userName = value;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setRealName(String value) {
		this.realName = value;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setLoginPassword(String value) {
		this.loginPassword = value;
	}

	public String getLoginPassword() {
		return this.loginPassword;
	}

	public void setPayPassword(String value) {
		this.payPassword = value;
	}

	public String getPayPassword() {
		return this.payPassword;
	}

	public void setIsOnline(String value) {
		this.isOnline = value;
	}

	public String getIsOnline() {
		return this.isOnline;
	}

	public void setLastLoginDate(java.util.Date value) {
		this.lastLoginDate = value;
	}

	public java.util.Date getLastLoginDate() {
		return this.lastLoginDate;
	}

	public void setIsCompany(String value) {
		this.isCompany = value;
	}

	public String getIsCompany() {
		return this.isCompany;
	}

	public String toString() {
		return new StringBuilder().append("UserId" + getUserId())
				.append("UserName" + getUserName())
				.append("RealName" + getRealName())
				.append("LoginPassword" + getLoginPassword())
				.append("PayPassword" + getPayPassword())
				.append("IsOnline" + getIsOnline())
				.append("LastLoginDate" + getLastLoginDate())
				.append("IsCompany" + getIsCompany()).toString();
	}

	public String getMobileNumber() {
		if (StringUtils.isEmpty(mobileNumber)) {
			return detail.getMobileNumber();
		}
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		if (StringUtils.isEmpty(email)) {
			return detail.getEmail();
		}
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean validate() {
		return true;
	}

	public void trim() {
		if (realName != null)
			setRealName(realName.trim());
	}
}
