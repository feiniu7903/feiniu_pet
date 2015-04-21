package com.lvmama.tnt.user.po;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.comm.vo.TntConstant.PAY_TYPE;

/**
 * 用户详情
 * 
 * @author gaoxin
 * 
 */
public class TntUserDetail implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long userDetailId;

	private Long userId;

	private String gender;

	private String duties;

	private String department;

	private String mobileNumber;

	private String phoneNumber;

	private String faxNumber;

	private String address;

	private String email;

	private String zipCode;

	private String qqAccount;

	private String imageUrl;

	private String cityId;

	private String companyName;

	private Long companyTypeId;

	private String chargePhone;

	private String chargeName;

	private String serviceTel;

	private String legalRepresentative;

	private String companyProfile;

	private String employeeNum;

	private String isEmailChecked;

	private String isMobileChecked;

	private Date createdDate;

	private Date updatedDate;

	private String finalStatus;

	private String materialStatus;

	private String infoStatus;

	private String failReason;

	private String payType;// 支付方式

	private String netUrl;

	// 省份Id
	private String provinceId;

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	private String memo;

	private Date contractStartDate;

	private Date contractEndDate;

	public String getContractEndDateStr() {
		return TntUtil.formatDate(contractEndDate);
	}

	public void setContractEndDateStr(String contractEndDateStr) {
		setContractEndDate(TntUtil.stringToDate(contractEndDateStr));
	}

	public String getContractStartDateStr() {
		return TntUtil.formatDate(contractStartDate);
	}

	public void setContractStartDateStr(String contractStartDateStr) {
		setContractStartDate(TntUtil.stringToDate(contractStartDateStr));
	}

	public Date getContractStartDate() {
		return contractStartDate;
	}

	public String getStrContractStartDate() {
		Date date = getContractStartDate();
		if (date != null) {
			SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
			return SDF.format(date);
		}
		return "";
	}

	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public Date getContractEndDate() {
		return contractEndDate;
	}

	public String getStrContractEndDate() {
		Date date = getContractEndDate();
		if (date != null) {
			SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
			return SDF.format(date);
		}
		return "";
	}

	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	/**
	 * 临时字段
	 * 
	 * 拥有company_type_id的汇总数
	 */
	private int total;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public TntUserDetail() {
	}

	public TntUserDetail(Long userDetailId) {
		this.userDetailId = userDetailId;
	}

	public Long getUserDetailId() {
		return userDetailId;
	}

	public void setUserDetailId(Long userDetailId) {
		this.userDetailId = userDetailId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDuties() {
		return duties;
	}

	public void setDuties(String duties) {
		this.duties = duties;
	}

	public String getNetUrl() {
		return netUrl;
	}

	public void setNetUrl(String netUrl) {
		this.netUrl = netUrl;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getQqAccount() {
		return qqAccount;
	}

	public void setQqAccount(String qqAccount) {
		this.qqAccount = qqAccount;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCompanyName() {
		return companyName;
	}
	
	public String getCompanyNameOrPerson() {
		if(StringUtil.isEmptyString(this.companyName)){
			return "个人";
		}
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getCompanyTypeId() {
		return companyTypeId;
	}

	public void setCompanyTypeId(Long companyTypeId) {
		this.companyTypeId = companyTypeId;
	}

	public String getChargePhone() {
		return chargePhone;
	}

	public void setChargePhone(String chargePhone) {
		this.chargePhone = chargePhone;
	}

	public String getServiceTel() {
		return serviceTel;
	}

	public void setServiceTel(String serviceTel) {
		this.serviceTel = serviceTel;
	}

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getCompanyProfile() {
		return companyProfile;
	}

	public void setCompanyProfile(String companyProfile) {
		this.companyProfile = companyProfile;
	}

	public String getEmployeeNum() {
		return employeeNum;
	}

	public void setEmployeeNum(String employeeNum) {
		this.employeeNum = employeeNum;
	}

	public String getIsEmailChecked() {
		return isEmailChecked;
	}

	public void setIsEmailChecked(String isEmailChecked) {
		this.isEmailChecked = isEmailChecked;
	}

	public String getIsMobileChecked() {
		return isMobileChecked;
	}

	public void setIsMobileChecked(String isMobileChecked) {
		this.isMobileChecked = isMobileChecked;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getFinalStatus() {
		return finalStatus;
	}

	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}

	public String getMaterialStatus() {
		return materialStatus;
	}

	public String getChargeName() {
		return chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	public void setMaterialStatus(String materialStatus) {
		this.materialStatus = materialStatus;
	}

	public String getInfoStatus() {
		return infoStatus;
	}

	public void setInfoStatus(String infoStatus) {
		this.infoStatus = infoStatus;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public boolean isMonthPay() {
		return PAY_TYPE.isMonthPay(this.getPayType());
	}

	public String toString() {
		return new StringBuffer().append("UserDetailId:" + getUserDetailId())
				.append("UserId:" + getUserId())
				.append("Gender:" + getGender())
				.append("Duties:" + getDuties())
				.append("Department:" + getDepartment())
				.append("MobileNumber:" + getMobileNumber())
				.append("PhoneNumber:" + getPhoneNumber())
				.append("FaxNumber:" + getFaxNumber())
				.append("Address:" + getAddress())
				.append("Email:" + getEmail())
				.append("QqAccount:" + getQqAccount())
				.append("ImageUrl:" + getImageUrl())
				.append("CityId:" + getCityId())
				.append("CompanyTypeId:" + getCompanyTypeId())
				.append("ChargePhone:" + getChargePhone())
				.append("ServiceTel:" + getServiceTel())
				.append("LegalRepresentative:" + getLegalRepresentative())
				.append("CompanyProfile:" + getCompanyProfile())
				.append("EmployeeNum:" + getEmployeeNum())
				.append("IsEmailChecked:" + getIsEmailChecked())
				.append("IsMobileChecked:" + getIsMobileChecked())
				.append("CreatedDate:" + getCreatedDate())
				.append("UpdatedDate:" + getUpdatedDate())
				.append("FinalStatus:" + getFinalStatus())
				.append("MaterialStatus:" + getMaterialStatus())
				.append("InfoStatus:" + getInfoStatus())
				.append("FailReason:" + getFailReason())
				.append("Memo:" + getMemo()).toString();
	}
}
