package com.lvmama.tnt.front.user.form;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.MD5;
import com.lvmama.tnt.comm.vo.ResultMessage;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.po.TntUserDetail;

public class UserForm {

	private java.lang.String userName;
	private java.lang.String realName;
	private java.lang.String loginPassword;
	private java.lang.String payPassword;
	private java.lang.String isCompany;
	//性别
	private java.lang.String gender;
	//职务
	private java.lang.String duties;
	//部门
	private java.lang.String department;
	//手机号
	private java.lang.String mobileNumber;
	//座机号码
	private java.lang.String phoneNumber;
	//传真号
	private java.lang.String faxNumber;
	//地址
	private java.lang.String address;
	//email
	private java.lang.String email;
	//QQ帐号
	private java.lang.String qqAccount;
	//邮编
	private String zipCode;
	//网址
	private String netUrl;
	//头像URL
	private java.lang.String imageUrl;
	//企业名称
	private java.lang.String companyName;
	//城市Id
	private String cityId="310000";
	//省
	private String provinceId="310000";
	//分销商类型Id
	private java.lang.Long companyTypeId;
	//负责人姓名
	private java.lang.String chargeName;
	//负责人电话
	private java.lang.String chargePhone;
	//客服电话
	private java.lang.String serviceTel;
	//法人代表
	private java.lang.String legalRepresentative;
	//公司简介
	private java.lang.String companyProfile;
	//员工人数
	private java.lang.String employeeNum;
	//EMAIL是否已验证
	private java.lang.String isEmailChecked;
	//手机号是否已验证
	private java.lang.String isMobileChecked;
	//创建时间
	private java.util.Date createdDate;
	//更新时间
	private java.util.Date updatedDate;
	//终审核状态
	private java.lang.String finalStatus;
	//资料审核状态
	private java.lang.String materialStatus;
	//信息审核状态
	private java.lang.String infoStatus;
	//审核失败原因
	private java.lang.String failReason;
	//备注
	private java.lang.String memo;
	
	private String imageCode;

	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	public java.lang.String getRealName() {
		return realName;
	}

	public void setRealName(java.lang.String realName) {
		this.realName = realName;
	}

	public java.lang.String getLoginPassword() {
		if(StringUtils.isNotEmpty(this.loginPassword)){
			loginPassword = MD5.code(loginPassword, MD5.KEY_TNT_USER_PASSWORD);
		}
		return loginPassword;
	}

	public void setLoginPassword(java.lang.String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public java.lang.String getPayPassword() {
		if(StringUtils.isNotEmpty(this.payPassword)){
			try {
				payPassword = MD5.encode(this.payPassword);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return payPassword;
	}

	public void setPayPassword(java.lang.String payPassword) {
		this.payPassword = payPassword;
	}

	

	public java.lang.String getGender() {
		return gender;
	}

	public void setGender(java.lang.String gender) {
		this.gender = gender;
	}

	public java.lang.String getDuties() {
		return duties;
	}

	public void setDuties(java.lang.String duties) {
		this.duties = duties;
	}

	public java.lang.String getDepartment() {
		return department;
	}

	public void setDepartment(java.lang.String department) {
		this.department = department;
	}

	public java.lang.String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(java.lang.String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public java.lang.String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(java.lang.String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public java.lang.String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(java.lang.String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public java.lang.String getEmail() {
		return email== null?"":email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getQqAccount() {
		return qqAccount;
	}

	public void setQqAccount(java.lang.String qqAccount) {
		this.qqAccount = qqAccount;
	}

	public java.lang.String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(java.lang.String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public java.lang.String getCityId() {
		return cityId;
	}

	public void setCityId(java.lang.String cityId) {
		this.cityId = cityId;
	}

	public java.lang.String getIsCompany() {
		return isCompany;
	}

	public void setIsCompany(java.lang.String isCompany) {
		this.isCompany = isCompany;
	}

	public java.lang.Long getCompanyTypeId() {
		return companyTypeId;
	}

	public void setCompanyTypeId(java.lang.Long companyTypeId) {
		this.companyTypeId = companyTypeId;
	}

	public java.lang.String getChargePhone() {
		return chargePhone;
	}

	public void setChargePhone(java.lang.String chargePhone) {
		this.chargePhone = chargePhone;
	}

	public java.lang.String getServiceTel() {
		return serviceTel;
	}

	public void setServiceTel(java.lang.String serviceTel) {
		this.serviceTel = serviceTel;
	}

	public java.lang.String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(java.lang.String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public java.lang.String getCompanyProfile() {
		return companyProfile;
	}

	public void setCompanyProfile(java.lang.String companyProfile) {
		this.companyProfile = companyProfile;
	}

	public java.lang.String getEmployeeNum() {
		return employeeNum;
	}

	public void setEmployeeNum(java.lang.String employeeNum) {
		this.employeeNum = employeeNum;
	}

	public java.lang.String getIsEmailChecked() {
		return isEmailChecked;
	}

	public void setIsEmailChecked(java.lang.String isEmailChecked) {
		this.isEmailChecked = isEmailChecked;
	}

	public java.lang.String getIsMobileChecked() {
		return isMobileChecked;
	}

	public void setIsMobileChecked(java.lang.String isMobileChecked) {
		this.isMobileChecked = isMobileChecked;
	}

	public java.util.Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}

	public java.util.Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(java.util.Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public java.lang.String getFinalStatus() {
		return finalStatus;
	}

	public void setFinalStatus(java.lang.String finalStatus) {
		this.finalStatus = finalStatus;
	}

	public java.lang.String getMaterialStatus() {
		return materialStatus;
	}

	public void setMaterialStatus(java.lang.String materialStatus) {
		this.materialStatus = materialStatus;
	}

	public java.lang.String getInfoStatus() {
		return infoStatus;
	}

	public void setInfoStatus(java.lang.String infoStatus) {
		this.infoStatus = infoStatus;
	}

	public java.lang.String getFailReason() {
		return failReason;
	}

	public void setFailReason(java.lang.String failReason) {
		this.failReason = failReason;
	}

	public java.lang.String getMemo() {
		return memo;
	}

	public void setMemo(java.lang.String memo) {
		this.memo = memo;
	}
	
	

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	
	public String getNetUrl() {
		return netUrl;
	}

	public void setNetUrl(String netUrl) {
		this.netUrl = netUrl;
	}

	public java.lang.String getChargeName() {
		return chargeName;
	}

	public void setChargeName(java.lang.String chargeName) {
		this.chargeName = chargeName;
	}
	

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}
	
	public boolean isCompany(){
		return "true".equalsIgnoreCase(this.getIsCompany())?true:false;
	}

	
	public java.lang.String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}

	public TntUser toTntUser() {
		TntUser user = new TntUser();
		user.setUserName(this.getUserName());
		user.setRealName(this.getRealName());
		user.setLoginPassword(this.getLoginPassword());
		user.setIsCompany(this.getIsCompany());
		
		user.getDetail().setEmail(this.getEmail().toLowerCase());
		user.getDetail().setMobileNumber(this.getMobileNumber());
		user.getDetail().setAddress(this.getAddress());
		user.getDetail().setChargePhone(this.getChargePhone());
		user.getDetail().setCompanyName(this.getCompanyName());
		user.getDetail().setCompanyProfile(this.getCompanyProfile());
		user.getDetail().setCompanyTypeId(this.getCompanyTypeId());
		user.getDetail().setChargeName(this.getChargeName());
		user.getDetail().setCityId(this.getCityId());
		user.getDetail().setDepartment(this.getDepartment());
		user.getDetail().setDuties(this.getDuties());
		user.getDetail().setEmail(this.getEmail());
		user.getDetail().setZipCode(this.getZipCode());
		user.getDetail().setEmployeeNum(this.getEmployeeNum());
		user.getDetail().setFaxNumber(this.getFaxNumber());
		user.getDetail().setGender(this.getGender());
		user.getDetail().setImageUrl(this.getImageUrl());
		user.getDetail().setLegalRepresentative(this.getLegalRepresentative());
		user.getDetail().setPhoneNumber(this.getPhoneNumber());
		user.getDetail().setQqAccount(this.getQqAccount());
		user.getDetail().setServiceTel(this.getServiceTel());
		user.getDetail().setProvinceId(this.getProvinceId());
		user.getDetail().setNetUrl(this.getNetUrl());
		
		user.getDetail().setCreatedDate(new Date());
		user.getDetail().setPayType(TntConstant.PAY_TYPE.SINGLE.name());
		user.getDetail().setFinalStatus(com.lvmama.tnt.comm.vo.TntConstant.USER_FINAL_STATUS.WAITING.name());
		user.getDetail().setInfoStatus(com.lvmama.tnt.comm.vo.TntConstant.USER_INFO_STATUS.WAITING.name());
		
		return user;
	}
	
	public TntUser toUpdateTntUser(TntUser tuser) {
		TntUser user = new TntUser();
		user.setUserName(this.getUserName());
		user.setRealName(this.getRealName());
		user.setLoginPassword(this.getLoginPassword());
		user.setIsCompany(this.getIsCompany());
		
		user.getDetail().setEmail(this.getEmail().toLowerCase());
		user.getDetail().setMobileNumber(this.getMobileNumber());
		user.getDetail().setAddress(this.getAddress());
		user.getDetail().setChargePhone(this.getChargePhone());
		user.getDetail().setCompanyName(this.getCompanyName());
		user.getDetail().setCompanyProfile(this.getCompanyProfile());
		user.getDetail().setCompanyTypeId(this.getCompanyTypeId());
		user.getDetail().setChargeName(this.getChargeName());
		user.getDetail().setCityId(this.getCityId());
		user.getDetail().setDepartment(this.getDepartment());
		user.getDetail().setDuties(this.getDuties());
		user.getDetail().setEmail(this.getEmail());
		user.getDetail().setZipCode(this.getZipCode());
		user.getDetail().setEmployeeNum(this.getEmployeeNum());
		user.getDetail().setFaxNumber(this.getFaxNumber());
		user.getDetail().setGender(this.getGender());
		user.getDetail().setImageUrl(this.getImageUrl());
		user.getDetail().setLegalRepresentative(this.getLegalRepresentative());
		user.getDetail().setPhoneNumber(this.getPhoneNumber());
		user.getDetail().setQqAccount(this.getQqAccount());
		user.getDetail().setServiceTel(this.getServiceTel());
		user.getDetail().setProvinceId(this.getProvinceId());
		user.getDetail().setNetUrl(this.getNetUrl());
		user.getDetail().setUpdatedDate(new Date());
		if(isNeedInfoAudit(tuser)){
			user.getDetail().setInfoStatus(com.lvmama.tnt.comm.vo.TntConstant.USER_INFO_STATUS.REWAITING.name());
			user.getDetail().setFinalStatus(com.lvmama.tnt.comm.vo.TntConstant.USER_FINAL_STATUS.WAITING.name());
		}
		if(isEmailChange(tuser)){
			user.getDetail().setInfoStatus(com.lvmama.tnt.comm.vo.TntConstant.USER_INFO_STATUS.WAITING.name());
		}
		return user;
	}
	public UserForm(TntUser user){
		this.setUserName(user.getUserName());
		this.setLoginPassword(user.getLoginPassword());
		this.setRealName(user.getRealName());
		this.setIsCompany(user.getIsCompany());
		
		this.setMobileNumber(user.getDetail().getMobileNumber());
		this.setAddress(user.getDetail().getAddress());
		this.setChargePhone(user.getDetail().getChargePhone());
		this.setCompanyName(user.getDetail().getCompanyName());
		this.setChargeName(user.getDetail().getChargeName());
		this.setCompanyProfile(user.getDetail().getCompanyProfile());
		this.setCompanyTypeId(user.getDetail().getCompanyTypeId());
		this.setCityId(user.getDetail().getCityId());
		this.setDepartment(user.getDetail().getDepartment());
		this.setDuties(user.getDetail().getDuties());
		this.setEmail(user.getDetail().getEmail());
		this.setZipCode(user.getDetail().getZipCode());
		this.setEmployeeNum(user.getDetail().getEmployeeNum());
		this.setFaxNumber(user.getDetail().getFaxNumber());
		this.setGender(user.getDetail().getGender());
		this.setImageUrl(user.getDetail().getImageUrl());
		this.setLegalRepresentative(user.getDetail().getLegalRepresentative());
		this.setPhoneNumber(user.getDetail().getPhoneNumber());
		this.setQqAccount(user.getDetail().getQqAccount());
		this.setServiceTel(user.getDetail().getServiceTel());
		this.setProvinceId(user.getDetail().getProvinceId());
		this.setNetUrl(user.getDetail().getNetUrl());
		
		this.setFinalStatus(user.getDetail().getFinalStatus());
		this.setFinalStatus(user.getDetail().getInfoStatus());
		this.setCreatedDate(user.getDetail().getCreatedDate());
		
	}
	public UserForm(){
		
	}
	public ResultMessage validate() {
		ResultMessage result = new ResultMessage();
		return result;
	}

	public boolean isNeedInfoAudit(TntUser user) {
		if(user!=null && user.getDetail()!=null){
			TntUserDetail detail = user.getDetail();
			if(this.getCompanyName()!=null && !this.getCompanyName().equalsIgnoreCase(detail.getCompanyName())){
				return true;
			}
			if(this.getMobileNumber()!=null && !this.getMobileNumber().equalsIgnoreCase(detail.getMobileNumber())){
				return true;
			}
			if(this.getEmail()!=null && !this.getEmail().equalsIgnoreCase(detail.getEmail())){
				return true;
			}
			if(this.getServiceTel()!=null && !this.getServiceTel().equalsIgnoreCase(detail.getServiceTel())){
				return true;
			}
		}
		return false;
	}
	public boolean isEmailChange(TntUser user) {
		if(user!=null && user.getDetail()!=null){
			TntUserDetail detail = user.getDetail();
			if(this.getEmail()!=null && !this.getEmail().equalsIgnoreCase(detail.getEmail())){
				return true;
			}
		}
		return false;
	}
	
	
}
