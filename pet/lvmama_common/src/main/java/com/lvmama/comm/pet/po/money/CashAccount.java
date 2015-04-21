package com.lvmama.comm.pet.po.money;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.PriceUtil;

/**
 * 现金账户POJO.
 *
 * @author tom
 * @version Super二期 10/12/10
 * @since Super二期
 */
public class CashAccount implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5452882630214322709L;
	/**
	 * 主键
	 */
	private Long cashAccountId;
	/**
	 * 用户ID.
	 */
	private Long userId;
	/**
	 * 充值余额.
	 */
	private Long rechargeBalance;
	/**
	 * 退款余额.
	 */
	private Long refundBalance;
	
	/**
	 * 奖金余额
	 */
	private Long bonusBalance=0L;
	
	/**
	 * 新奖金余额
	 */
	private Long newBonusBalance=0L;
	
	/**
	 * 奖金余额，单位：元
	 */
	private float bonusBalanceFloat;
	
	/** 变量 mobileNumber . */
	private String mobileNumber;
	
	/** 变量 paymentPassword . */
	private String paymentPassword;
	/**
	 * 是否有效.
	 */
	private String valid;
	
	/**
	 * 最后一次支付邮箱安全校验时间
	 */
	private Date lastPayValidateTime;
	
	private String memo;//冻结备注

	/*
	 * 查看账户是否有效或被锁
	 */
	public boolean isAccountValid() {
		return "Y".equalsIgnoreCase(valid);
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * getRechargeBalance.
	 *
	 * @return 充值余额
	 */
	public Long getRechargeBalance() {
		return rechargeBalance;
	}

	/**
	 * setRechargeBalance.
	 *
	 * @param rechargeBalance
	 *            充值余额
	 */
	public void setRechargeBalance(final Long rechargeBalance) {
		this.rechargeBalance = rechargeBalance;
	}

	/**
	 * getRefundBalance.
	 *
	 * @return 退款余额
	 */
	public Long getRefundBalance() {
		return refundBalance;
	}

	/**
	 * setRefundBalance.
	 *
	 * @param refundBalance
	 *            退款余额
	 */
	public void setRefundBalance(final Long refundBalance) {
		this.refundBalance = refundBalance;
	}
	
	/**
	 * 获取奖金余额
	 * @return
	 */
	public Long getBonusBalance() {
		return bonusBalance;
	}

	/**
	 * 设置奖金余额
	 * @param bonusBalance 奖金余额
	 */
	public void setBonusBalance(Long bonusBalance) {
		this.bonusBalance = bonusBalance;
	}
	/**
	 * 获取奖金余额，单位：元
	 * @return
	 */
	public float getBonusBalanceFloat() {
		if(bonusBalance!=null){
			this.bonusBalanceFloat=PriceUtil.convertToYuan(bonusBalance.longValue());
		}
		return bonusBalanceFloat;
	}
	
	/**
	 * 获取新奖金余额，单位：元
	 * @return
	 */
	public float getNewBonusBalanceFloat() {
		if(newBonusBalance!=null){
			return PriceUtil.convertToYuan(newBonusBalance.longValue());
		}
		return newBonusBalance;
	}
	
	
	/**
	 * 获取奖金账户余额，单位：元（老的加新的）
	 * @return
	 */
	public float getTotalBonusBalanceYuan() {
		if(this.bonusBalance!=null && this.newBonusBalance!=null){
			return PriceUtil.convertToYuan(bonusBalance.longValue() + newBonusBalance.longValue());
		}else{
			return 0f;
		}
	}
	
	/**
	 * 获取现金账户余额，单位：元
	 * @return
	 */
	public float getTotalCashBalanceYuan() {
		if(this.refundBalance!=null && this.rechargeBalance!=null){
			return PriceUtil.convertToYuan(refundBalance.longValue() + rechargeBalance.longValue());
		}else{
			return 0f;
		}
	}
	
	
	
	/**
	 * 设置奖金余额单位：元
	 * @param bonusBalanceFloat 奖金余额
	 */
	public void setBonusBalanceFloat(float bonusBalanceFloat) {
		this.bonusBalanceFloat = bonusBalanceFloat;
	}

	/**
	 * getValid.
	 *
	 * @return 是否有效
	 */
	public String getValid() {
		return valid;
	}

	/**
	 * setValid.
	 *
	 * @param valid
	 *            是否有效
	 */
	public void setValid(final String valid) {
		this.valid = valid;
	}

	public Long getCashAccountId() {
		return cashAccountId;
	}

	public void setCashAccountId(Long cashAccountId) {
		this.cashAccountId = cashAccountId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPaymentPassword() {
		return paymentPassword;
	}

	public void setPaymentPassword(String paymentPassword) {
		this.paymentPassword = paymentPassword;
	}

	public Date getLastPayValidateTime() {
		return lastPayValidateTime;
	}

	public void setLastPayValidateTime(Date lastPayValidateTime) {
		this.lastPayValidateTime = lastPayValidateTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getNewBonusBalance() {
		return newBonusBalance;
	}

	public void setNewBonusBalance(Long newBonusBalance) {
		this.newBonusBalance = newBonusBalance;
	}
	
	
}