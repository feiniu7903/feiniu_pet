package com.lvmama.comm.vo;

import java.io.Serializable;

public class CallUserInfo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7024765097004502264L;
	private Long id;
	private String userId;

    private String userName;
    
    private String email;
    
    private String mobileNumber;
    
    private String realName;
    
    private String gender;
    
    private String cityId;
    
    private String address;
    
    private String zipCode;
    
    private String isUsers;
    
    private String provinceId;
    
    private String createdUserId;
    
    private String userPassword;
    
    private String isLocked;
    
    private String isEmailChecked;
    
    private String isValid;
    
    private String nameIsUpdate;
    
    private Long awardBalance;//返现余额
    
    private String idNumber;//身份证号码.
    
    private String createDateStr;//注册时间.
    
    private String memo;//备注.
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getIsUsers() {
		return isUsers;
	}

	public void setIsUsers(String isUsers) {
		this.isUsers = isUsers;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}

	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	public String getIsEmailChecked() {
		return isEmailChecked;
	}

	public void setIsEmailChecked(String isEmailChecked) {
		this.isEmailChecked = isEmailChecked;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getNameIsUpdate() {
		return nameIsUpdate;
	}

	public void setNameIsUpdate(String nameIsUpdate) {
		this.nameIsUpdate = nameIsUpdate;
	}

	public Float getAwardBalanceYuan() {
		if (awardBalance==null) {
			return 0f;
		}
		return com.lvmama.comm.utils.PriceUtil.convertToYuan(awardBalance);
	}
	
	public Long getAwardBalance() {
		return awardBalance;
	}

	public void setAwardBalance(Long awardBalance) {
		this.awardBalance = awardBalance;
	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}