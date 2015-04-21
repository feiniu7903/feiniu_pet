package com.lvmama.clutter.model;

import java.io.Serializable;

/**
 * 移动端专用 v3- 用户信息. 
 * @author 
 *
 */
public class MobileUser implements Serializable{

	private String userId; // 用户id 
	private String nickName; // 别名
	private String imageUrl; // 图片地址
	private String userName;// 用户名
	private String realName;// 真实姓名
	private String mobileNumber;// 手机号
    private String email;// 邮箱
    private Long point=5L;//积分
    private String awardBalance;//奖励
    private String withdraw;// 
    private String cashBalance;//现金
    private String level;// 等级
    private String loginChannel;
    private boolean saveCreditCard;
    
    /**************** V5.0 **********************/
    private boolean mobileCanChecked ;// 手机是否验证  StringUtils.isNotBlank(user.getMobileNumber())&& "Y".equals(user.getIsMobileChecked())
	private boolean nameCanUpdate;// 用户名是否修改过 ，只能修改一次。 user.getNameIsUpdate()
    private String gener;// 性别
    public String getGener() {
		return gener;
	}

	public void setGener(String gener) {
		this.gener = gener;
	}

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

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLoginChannel() {
		return loginChannel;
	}

	public void setLoginChannel(String loginChannel) {
		this.loginChannel = loginChannel;
	}

	public boolean isSaveCreditCard() {
		return saveCreditCard;
	}

	public void setSaveCreditCard(boolean saveCreditCard) {
		this.saveCreditCard = saveCreditCard;
	}
	public boolean isMobileCanChecked() {
		return mobileCanChecked;
	}

	public void setMobileCanChecked(boolean mobileCanChecked) {
		this.mobileCanChecked = mobileCanChecked;
	}

	public boolean isNameCanUpdate() {
		return nameCanUpdate;
	}

	public void setNameCanUpdate(boolean nameCanUpdate) {
		this.nameCanUpdate = nameCanUpdate;
	}

}
