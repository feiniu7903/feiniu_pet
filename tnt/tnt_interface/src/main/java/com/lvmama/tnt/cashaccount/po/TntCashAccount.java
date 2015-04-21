package com.lvmama.tnt.cashaccount.po;

import com.lvmama.tnt.comm.util.PriceUtil;
import com.lvmama.tnt.user.po.TntUser;

/**
 * 现金账户
 * @author gaoxin
 * @version 1.0
 */
public class TntCashAccount implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2702942347646983443L;
	/**
	 *  主键，与用户表USER_ID相同
	 */
	private java.lang.Long cashAccountId;
	/**
	 *  用户ID
	 */
	private java.lang.Long userId;
	/**
	 *  可用余额，以分为单位
	 */
	private Long balance=0L;
	
	/**
	 * 现金账户总金额 以分为单位
	 */
	private Long totalMoney=0L;
	
	/**
	 * 现金账户冻结金额  以分为单位
	 */
	private Long freezeMoney=0L;
	
	/**
	 *  是否有效，Y为有效，N为无效
	 */
	private java.lang.String valid;
	/**
	 *  绑定的手机号
	 */
	private java.lang.String mobileNumber;
	/**
	 *  支付密码
	 */
	private java.lang.String paymentPassword;
	//columns END
	
	private String userName;
	
	private String realName;
	
	private TntUser tntUser = new TntUser();

	public TntCashAccount(){
	}

	public TntCashAccount(
		java.lang.Long cashAccountId
	){
		this.cashAccountId = cashAccountId;
	}

	public void setCashAccountId(java.lang.Long value) {
		this.cashAccountId = value;
	}
	
	public java.lang.Long getCashAccountId() {
		return this.cashAccountId;
	}
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	public void setBalance(Long value) {
		this.balance = value;
	}
	
	public Long getBalance() {
		return this.balance;
	}
	public void setValid(java.lang.String value) {
		this.valid = value;
	}
	
	public java.lang.String getValid() {
		return this.valid;
	}
	public void setMobileNumber(java.lang.String value) {
		this.mobileNumber = value;
	}
	
	public java.lang.String getMobileNumber() {
		return this.mobileNumber;
	}
	
	public String getReplaceMobile(){
		return "ssssssss";
	}
	public void setPaymentPassword(java.lang.String value) {
		this.paymentPassword = value;
	}
	
	public java.lang.String getPaymentPassword() {
		return this.paymentPassword;
	}

	
	
	public Long getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Long totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Long getFreezeMoney() {
		return freezeMoney;
	}

	public void setFreezeMoney(Long freezeMoney) {
		this.freezeMoney = freezeMoney;
	}

	public String getUserName() {
		if(userName!=null){
			return userName.trim();
		}
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		if(realName!=null){
			return realName.trim();
		}
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	
	public void setBalanceY(String balanceY) {
		this.setBalance(PriceUtil.convertToFen(balanceY));
	}
	public Float getBalanceToYuan() {
		return PriceUtil.convertToYuan(this.getBalance());
	}
	public void setTotalMoneyY(String TotalMoneyY) {
		this.setTotalMoney(PriceUtil.convertToFen(TotalMoneyY));
	}
	public Float getTotalMoneyToYuan() {
		return PriceUtil.convertToYuan(this.getTotalMoney());
	}
	public void setfreezeMoneyY(String freezeMoneyY) {
		this.setFreezeMoney(PriceUtil.convertToFen(freezeMoneyY));
	}
	public Float getFreezeMoneyToYuan() {
		return PriceUtil.convertToYuan(this.getFreezeMoney());
	}
	
	
	public TntUser getTntUser() {
		return tntUser;
	}

	public void setTntUser(TntUser tntUser) {
		this.tntUser = tntUser;
	}

	@Override
	public String toString() {
		return "TntCashAccount [cashAccountId=" + cashAccountId + ", userId=" + userId + ", balance=" + balance + ", valid=" + valid + ", mobileNumber=" + mobileNumber + ", paymentPassword=" + paymentPassword + "]";
	}


}

