package com.lvmama.comm.pet.po.user;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class UserUser implements Serializable{

	private static final long serialVersionUID = 8290356086399689311L;
	protected Long userId;
	
	protected String userNo;

    protected String groupId;
    protected String cityId;

    protected String userName;

    protected String userPassword;

    protected String passwordPrompt;

    protected String passwordAnswer;

    protected String realName;

    protected String address;

    protected String isLocked;

    protected Date createdDate;

    protected Date updatedDate;

    protected String isValid;

    protected String mobileNumber;

    protected String email;

    protected String gender;

    protected String idNumber;

    protected Long point;

    protected String nickName;

    protected String memo;

    protected Date birthday;


    protected String qqAccount;
    protected String msnAccount;

    protected String imageUrl;
    protected String spaceUrl;
    protected String registStepId;
    protected String isEmailChecked;
    protected String realPass;
    protected String cardId;
    protected String phoneNumber;
    protected String isAcceptEdm;
    
    protected String isMobileChecked;
    
    protected String zipCode;

    protected String memberShipCard;	//会员卡卡号


    protected Long isCashRegister = 1L; //返现注册标识，0代表不需要返现的用户，1代表新注册的需要返现的用户，2代表新注册的已经返现的用户.
	protected Date activeMscardDate;	//会员卡绑定时间

    protected String homeCity;
    
    protected Long awardBalance;
    protected Long withdraw;
    protected String channel;

    protected String grade = Constant.USER_MEMBER_GRADE.NORMAL.name(); //会员等级
    protected Date levelValidityDate;//会员等级有效期
    
    private Date lastLoginDate;
    
    private String nameIsUpdate;//登录用户名是否更新过
    protected Long cashBalance;//现金账户
    protected Long bonusBalance;//奖金余额
    protected String cancellationReason;//注销原因
    
    private String registerIp;//这个属性暂时用于存user action collection LOG
    private Long registerPort;//用户注册源端口号用于存user action collection LOG
    protected String wechatId;//微信id
    protected String subScribe;
    private boolean cashFrozen = false;
    //是否保存信用卡信息
    private String saveCreditCard;
    protected String loginType;
    public UserUser() {}
    
    public Long getId(){
    	return this.userId;
    }
    public void setId(Long id){
    	this.userId = id;
    }
    public final String getUserId() {
		return this.getUserNo();
	}

	public final void setUserId(String userId) {
		this.userNo = userId;
	}

	public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    
    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getPasswordPrompt() {
        return passwordPrompt;
    }

    public void setPasswordPrompt(String passwordPrompt) {
        this.passwordPrompt = passwordPrompt;
    }

    public String getPasswordAnswer() {
        return passwordAnswer;
    }

    public void setPasswordAnswer(String passwordAnswer) {
        this.passwordAnswer = passwordAnswer;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
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

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getQqAccount() {
        return qqAccount;
    }

    public void setQqAccount(String qqAccount) {
        this.qqAccount = qqAccount;
    }

    public String getMsnAccount() {
        return msnAccount;
    }

    public void setMsnAccount(String msnAccount) {
        this.msnAccount = msnAccount;
    }

    public String getSpaceUrl() {
        return spaceUrl;
    }

    public void setSpaceUrl(String spaceUrl) {
        this.spaceUrl = spaceUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRegistStepId() {
        return registStepId;
    }

    public void setRegistStepId(String registStepId) {
        this.registStepId = registStepId;
    }

    public String getIsEmailChecked() {
        return isEmailChecked;
    }

    public void setIsEmailChecked(String isEmailChecked) {
        this.isEmailChecked = isEmailChecked;
    }

    public String getRealPass() {
        return realPass;
    }

    public void setRealPass(String realPass) {
        this.realPass = realPass;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIsAcceptEdm() {
        return isAcceptEdm;
    }

    public void setIsAcceptEdm(String isAcceptEdm) {
        this.isAcceptEdm = isAcceptEdm;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

	public String getIsMobileChecked() {
		return isMobileChecked;
	}

	public void setIsMobileChecked(String isMobileChecked) {
		this.isMobileChecked = isMobileChecked;
	}

	public String getMemberShipCard() {
		return memberShipCard;
	}

	public void setMemberShipCard(String memberShipCard) {
		this.memberShipCard = memberShipCard;
	}

	public Date getActiveMscardDate() {
		return activeMscardDate;
	}

	public void setActiveMscardDate(Date activeMscardDate) {
		this.activeMscardDate = activeMscardDate;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Long getAwardBalance() {
		return awardBalance;
	}

	public void setAwardBalance(Long awardBalance) {
		this.awardBalance = awardBalance;
	}

	public Long getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(Long withdraw) {
		this.withdraw = withdraw;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Date getLevelValidityDate() {
		return levelValidityDate;
	}

	public void setLevelValidityDate(Date levelValidityDate) {
		this.levelValidityDate = levelValidityDate;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getNameIsUpdate() {
		return nameIsUpdate;
	}

	public void setNameIsUpdate(String nameIsUpdate) {
		this.nameIsUpdate = nameIsUpdate;
	}


	public String getCancellationReason() {
		return cancellationReason;
	}

	public void setCancellationReason(String cancellationReason) {
		this.cancellationReason = cancellationReason;
	}

	public Long getCashBalance() {
		return cashBalance;
	}

	public void setCashBalance(Long cashBalance) {
		this.cashBalance = cashBalance;
	}

	public Long getBonusBalance() {
		return bonusBalance;
	}

	public void setBonusBalance(Long bonusBalance) {
		this.bonusBalance = bonusBalance;
	}

	public float getCashBalanceFloat() {
		if (cashBalance != null) {
			return PriceUtil.convertToYuan(cashBalance.longValue());
		}
		return 0;
	}

	public float getBonusBalanceFloat() {
		if (bonusBalance != null) {
			return PriceUtil.convertToYuan(bonusBalance.longValue());
		}
		return 0;
	}

	/**
	 * 这个属性暂时用于存user action collection LOG
	 * @return
	 */
	public String getRegisterIp() {
		return registerIp;
	}

	/**
	 * 这个属性暂时用于存user action collection LOG
	 * @param registerIp
	 */
	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getSubScribe() {
		return subScribe;
	}

	public void setSubScribe(String subScribe) {
		this.subScribe = subScribe;
	}

	public Long getRegisterPort() {
		return registerPort;
	}

	public void setRegisterPort(Long registerPort) {
		this.registerPort = registerPort;
	}

	public String getSaveCreditCard() {
		return saveCreditCard;
	}

	public void setSaveCreditCard(String saveCreditCard) {
		this.saveCreditCard = saveCreditCard;
	}

	
	public boolean isCashNotFrozen() {
		return !isCashFrozen();
	}

	public boolean isCashFrozen() {
		return cashFrozen;
	}

	public void setCashFrozen(boolean cashFrozen) {
		this.cashFrozen = cashFrozen;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
}