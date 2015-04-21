package com.lvmama.comm.vo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;

/**
 * 现金账户POJO.
 * 
 * <pre>
 * 现金账户由三部分组成，分别是充值金额，订单退款金额和冻结金额
 * 充值金额可以被支付，但不能被提现
 * 订单退款金额可以被支付，也可以被提现
 * 冻结金额是处于提现状态的金额
 * 可支付金额=充值金额+订单退款金额-冻结金额
 * 可提现金额=订单退款金额-冻结金额
 * 现金账户金额=充值金额+订单退款金额
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/12/10
 * @since Super二期
 */
public final class CashAccountVO implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5529757631607763330L;
	/**
	 * 充值余额，以分为单位.
	 */
	private Long rechargeBalance = Long.valueOf(0);
	/**
	 * 退款余额，以分为单位.
	 */
	private Long refundBalance = Long.valueOf(0);
	
	/**
	 * 奖金余额，以分为单位.
	 */
	private Long bonusBalance= Long.valueOf(0);
	
	
	/**
	 * 新奖金余额，以分为单位.
	 */
	private Long newBonusBalance= Long.valueOf(0);
	
	/**
	 * 可支付余额.
	 * 
	 * <pre>
	 * 可支付余额 = 充值余额 + 订单退款余额 - 冻结余额，以分为单位
	 * </pre>
	 */
	private Long maxPayMoney = Long.valueOf(0);
	/**
	 * 现金账户总余额.
	 * 
	 * <pre>
	 * 现金账户总余额 = 充值余额 + 订单退款余额，以分为单位
	 * </pre>
	 */
	private Long totalMoney = Long.valueOf(0);
	/**
	 * 可提现余额.
	 * 
	 * <pre>
	 * 可提现余额 = 订单退款余额 - 冻结余额，以分为单位
	 * </pre>
	 */
	private Long maxDrawMoney = Long.valueOf(0);
	
	/**
	 * 手机号码.
	 */
	private String mobileNumber;
	/**
	 * 支付密码.
	 */
	private String paymentPassword;
	/**
	 * 格式化手机号码.
	 */
	private String mobileFormat;
	/**
	 * 存在标识.<br>
	 * true代表存在，false代表不存在
	 */
	private boolean existFlag = true;

	/**
	 * 现金账户主键
	 */
	private Long cashAccountId;
	
	private Long userId;
	/**
	 * 是否可用
	 */
	private String valid;
	
	/**
	 * 最后一次支付邮箱安全校验时间
	 */
	private Date lastPayValidateTime;
	
	/**
	 * 构造函数.
	 */
	public CashAccountVO() {
	}

	/**
	 * 构造函数.
	 * 
	 * @param existFlag
	 *            存在标识 <br>
	 *            true代表存在，false代表不存在
	 */
	public CashAccountVO(final boolean existFlag) {
		this.setExistFlag(false);
	}

	/**
	 * getRechargeBalance.
	 * 
	 * @return 充值余额，以分为单位
	 */
	public Long getRechargeBalance() {
		return rechargeBalance;
	}

	/**
	 * setRechargeBalance.
	 * 
	 * @param rechargeBalance
	 *            ，以分为单位 充值余额
	 */
	public void setRechargeBalance(final Long rechargeBalance) {
		this.rechargeBalance = rechargeBalance;
	}

	/**
	 * getRefundBalance.
	 * 
	 * @return 退款余额，以分为单位
	 */
	public Long getRefundBalance() {
		return refundBalance;
	}

	/**
	 * setRefundBalance.
	 * 
	 * @param refundBalance
	 *            退款余额，以分为单位
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
	 * getMaxPayMoney.
	 * 
	 * @return 可支付余额 = 充值余额 + 订单退款余额 - 冻结余额，以分为单位
	 */
	public Long getMaxPayMoney() {
		return maxPayMoney;
	}

	/**
	 * setMaxPayMoney.
	 * 
	 * @param maxPayMoney
	 *            可支付余额 = 充值余额 + 订单退款余额 - 冻结余额，以分为单位
	 */
	public void setMaxPayMoney(final Long maxPayMoney) {
		this.maxPayMoney = maxPayMoney;
	}

	/**
	 * getTotalMoney.
	 * 
	 * @return 现金账户总余额 = 充值余额 + 订单退款余额，以分为单位
	 */
	public Long getTotalMoney() {
		return totalMoney;
	}

	/**
	 * setTotalMoney.
	 * 
	 * @param totalMoney
	 *            现金账户总余额 = 充值余额 + 订单退款余额，以分为单位
	 */
	public void setTotalMoney(final Long totalMoney) {
		this.totalMoney = totalMoney;
	}

	/**
	 * getMaxDrawMoney.
	 * 
	 * @return 可提现余额 = 订单退款余额 - 冻结余额，以分为单位
	 */
	public Long getMaxDrawMoney() {
		return maxDrawMoney;
	}

	/**
	 * setMaxDrawMoney.
	 * 
	 * @param maxDrawMoney
	 *            可提现余额 = 订单退款余额 - 冻结余额，以分为单位
	 */
	public void setMaxDrawMoney(final Long maxDrawMoney) {
		this.maxDrawMoney = maxDrawMoney;
	}

	public float getMaxDrawMoneyYuan() {
		return PriceUtil.convertToYuan(maxDrawMoney);
	}
	public float getTotalMoneyYuan() {
		return PriceUtil.convertToYuan(totalMoney);
	}

	public float getRefundBalanceYuan() {
		return PriceUtil.convertToYuan(refundBalance);
	}

	public float getMaxPayMoneyYuan() {
		return PriceUtil.convertToYuan(maxPayMoney);
	}

	public float getRechargeBalanceYuan() {
		return PriceUtil.convertToYuan(rechargeBalance);
	}

	/**
	 * 返回奖金余额元
	 * @return
	 */
	public float getBonusBalanceYuan() {
		return PriceUtil.convertToYuan(this.bonusBalance);
	}
	
	
	/**
	 * 获取新奖金余额，单位：元
	 * @return
	 */
	public float getNewBonusBalanceYuan() {
		if(newBonusBalance!=null){
			return PriceUtil.convertToYuan(newBonusBalance);
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
	 * 获取奖金账户余额，单位：分（老的加新的）
	 * @return
	 */
	public long getTotalBonusBalance() {
		if(this.bonusBalance!=null && this.newBonusBalance!=null){
			return bonusBalance.longValue() + newBonusBalance.longValue();
		}else{
			return 0L;
		}
	}
	
	
	/**
	 * getMobileNumber.
	 * 
	 * @return 手机号码
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * setMobileNumber.
	 * 
	 * @param mobileNumber
	 *            手机号码
	 */
	public void setMobileNumber(final String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * isExistFlag.
	 * 
	 * @return 存在标识<br>
	 *         true代表存在，false代表不存在
	 */
	public boolean isExistFlag() {
		return existFlag;
	}

	/**
	 * setExistFlag.
	 * 
	 * @param existFlag
	 *            存在标识<br>
	 *            true代表存在，false代表不存在
	 */
	public void setExistFlag(final boolean existFlag) {
		this.existFlag = existFlag;
	}
	/**
	 * 获取支付密码.
	 * @return 支付密码
	 */
	public String getPaymentPassword() {
		return paymentPassword;
	}
	/**
	 * 设置支付密码.
	 * @param paymentPassword 支付密码
	 */
	public void setPaymentPassword(String paymentPassword) {
		this.paymentPassword = paymentPassword;
	}

	public String getMobileFormat() {
		if(StringUtil.validMobileNumber(mobileNumber)){
			mobileFormat = mobileNumber.subSequence(0, 3) + "****" + mobileNumber.substring(7);
		}
		return mobileFormat;
	}

	public Long getCashAccountId() {
		return cashAccountId;
	}

	public void setCashAccountId(Long cashAccountId) {
		this.cashAccountId = cashAccountId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public Date getLastPayValidateTime() {
		return lastPayValidateTime;
	}

	public void setLastPayValidateTime(Date lastPayValidateTime) {
		this.lastPayValidateTime = lastPayValidateTime;
	}
	
	/**
	 * 判断是否需要支付邮箱校验
	 * @return
	 */
	public boolean isNeedsEmailCheck(){
		boolean needsEmailCheck=false;
		Date lastPayValidateTime=this.getLastPayValidateTime();
		if(null==lastPayValidateTime){
			needsEmailCheck=true;
		}else{
			//首次验证时间三个月之内，不需要验证邮箱
			Calendar cal =Calendar.getInstance();
			cal.setTime(lastPayValidateTime);
			cal.add(Calendar.MONTH, 3);
			if(cal.getTime().after(new Date())){
				needsEmailCheck=false;
			}else{
				needsEmailCheck=true;
			}
		}
		return needsEmailCheck;
	}

	public Long getNewBonusBalance() {
		return newBonusBalance;
	}

	public void setNewBonusBalance(Long newBonusBalance) {
		this.newBonusBalance = newBonusBalance;
	}
	
	
	
	
	
}
