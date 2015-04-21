package com.lvmama.comm.vo;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.Constant.ComeFrom;

/**
 * 现金账户变更日志POJO.
 *
 * @author tom
 * @version Super二期 10/12/10
 * @since Super二期
 */
public final class CashAccountChangeLogVO implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2996473161563445160L;
	
	private String businessId;
	private String rechargeBalance;
	private String refundBalance;
	/**
	 * 奖金余额
	 */
	private String bonusBalance;
	
	/**
	 * 收入，以元为单位.
	 */
	private String income;
	/**
	 * 支出，以元为单位.
	 */
	private String expenditure;
	/**
	 * 时间.
	 */
	private Date createTime;
	/**
	 * 订单ID，无订单ID时显示空字符串.
	 */
	private String orderId;
	/**
	 * 类型，取值范围DRAW提现，PAY付款，RECHARGE充值，REFUND退款.
	 *
	 * @see com.lvmama.comm.vo.Constant.MoneyAccountChangeType
	 */
	private String type;
	/**
	 * 当时余额，以元为单位.
	 */
	private String balance;
	/**
	 * 显示赔偿类型.
	 */
	private String type1;
	/**
	 * 产品.
	 */
	private String productName;
	/**
	 * getIncome.
	 *
	 * @return 收入，以元为单位
	 */
	public String getIncome() {
		return income;
	}

	/**
	 * setIncome.
	 *
	 * @param income
	 *            收入，以元为单位
	 */
	public void setIncome(final String income) {
		this.income = income;
	}

	/**
	 * .
	 *
	 * @return 支出，以元为单位
	 */
	public String getExpenditure() {
		return expenditure;
	}

	/**
	 * setExpenditure.
	 *
	 * @param expenditure
	 *            支出，以元为单位
	 */
	public void setExpenditure(final String expenditure) {
		this.expenditure = expenditure;
	}

	/**
	 * getCreateTime.
	 *
	 * @return 时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * setCreateTime.
	 *
	 * @param createTime
	 *            时间
	 */
	public void setCreateTime(final Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * getOrderId.
	 *
	 * @return 订单ID，无订单ID时显示空字符串
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * setOrderId.
	 *
	 * @param orderId
	 *            订单ID，无订单ID时显示空字符串
	 */
	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	/**
	 * getType.
	 *
	 * @see com.lvmama.comm.vo.Constant.MoneyAccountChangeType
	 * @return 类型，取值范围DRAW提现，PAY付款，RECHARGE充值，REFUND退款
	 */
	public String getType() {
		return type;
	}

	/**
	 * setType.
	 *
	 * @see com.lvmama.comm.vo.Constant.MoneyAccountChangeType
	 * @param type
	 *            类型，取值范围DRAW提现，PAY付款，RECHARGE充值，REFUND退款
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * getBalance.
	 *
	 * @return 当时余额，以元为单位
	 */
	public String getBalance() {
		return balance;
	}

	/**
	 * setBalance.
	 *
	 * @param balance
	 *            当时余额，以元为单位
	 */
	public void setBalance(final String balance) {
		this.balance = balance;
	}
	
	

	/**
	 * getType1.
	 *
	 * @return 显示赔偿类型
	 */
	public String getType1() {
		return type1;
	}

	/**
	 * setType1.
	 *
	 * @param type1
	 *            显示赔偿类型
	 */
	public void setType1(final String type1) {
		this.type1 = type1;
	}
	public String getTypeName(){
		return ComeFrom.getCnName(this.type);
	}

	public float getBalance2(){
		float balance2=new Float(this.balance).floatValue();
		if (this.income!=null &&!this.income.equals("")){
			balance2 = balance2 + new Float(this.income).floatValue();
		}
		if (this.expenditure!=null &&!this.expenditure.equals("")){
			balance2 = balance2 - new Float(this.expenditure).floatValue();
		}
		return balance2;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getRechargeBalance() {
		return rechargeBalance;
	}

	public void setRechargeBalance(String rechargeBalance) {
		this.rechargeBalance = rechargeBalance;
	}

	public String getRefundBalance() {
		return refundBalance;
	}

	public void setRefundBalance(String refundBalance) {
		this.refundBalance = refundBalance;
	}
	
	/**
	 * 获取奖金余额
	 * @return
	 */
	public String getBonusBalance() {
		return bonusBalance;
	}

	/**
	 * 设置奖金余额
	 * @param bonusBalance 奖金余额
	 */
	public void setBonusBalance(String bonusBalance) {
		this.bonusBalance = bonusBalance;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
}
