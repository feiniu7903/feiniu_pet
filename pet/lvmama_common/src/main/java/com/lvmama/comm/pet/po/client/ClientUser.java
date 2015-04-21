package com.lvmama.comm.pet.po.client;

import java.io.Serializable;


public class ClientUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5102909786746052178L;

	private String userId;
	
	private String nickName;
	
	private String imageUrl;
	
	private String userName;
	
	private String realName;
	
	private String mobileNumber;

    private String email;
    
    private Long point=5L;
    
    private String awardBalance;
    
    private String withdraw;
    
    private String cashBalance;
    
    private String wrongMessage;

    private String level;
    
    private String couponInfo="";
    
    private String lastLoginTime;
    
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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

	public String getAwardBalance() {
		return awardBalance;
	}

	public void setAwardBalance(String awardBalance) {
		this.awardBalance = awardBalance;
	}

	public String getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(String withdraw) {
		this.withdraw = withdraw;
	}

	public String getCashBalance() {
		return cashBalance;
	}

	public void setCashBalance(String cashBalance) {
		this.cashBalance = cashBalance;
	}

	public String getWrongMessage() {
		return wrongMessage;
	}

	public void setWrongMessage(String wrongMessage) {
		this.wrongMessage = wrongMessage;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCouponInfo() {
		return couponInfo;
	}

	public void setCouponInfo(String couponInfo) {
		this.couponInfo = couponInfo;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	


}
