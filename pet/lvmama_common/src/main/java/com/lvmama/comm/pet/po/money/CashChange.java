package com.lvmama.comm.pet.po.money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 现金账户变化日志POJO.
 *
 * @author tom
 * @version Super二期 10/12/10
 * @since Super二期
 */
public class CashChange implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -780726843778847334L;
	/**
	 * 主键
	 */
	private Long changeId;
	/**
	 * 现金账户ID.
	 */
	private Long cashAccountId;
	/**
	 * 充值金额.
	 */
	private BigDecimal amount;
	/**
	 * 先前的充值余额.
	 */
	private BigDecimal rechargeBalanceBeforeChange;
	/**
	 * 先前的退款金额.
	 */
	private BigDecimal refundBalanceBeforeChange;
	
	/**
	 * 先前的奖金金额
	 */
	private BigDecimal bonusBalanceBeforeChange;
	
	/**
	 * 类型.
	 */
	private String type;
	/**
	 * 创建时间.
	 */
	private Date createTime;
	/**
	 * 业务ID.
	 */
	private String businessId;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRechargeBalanceBeforeChange() {
		return rechargeBalanceBeforeChange;
	}

	public void setRechargeBalanceBeforeChange(
			BigDecimal rechargeBalanceBeforeChange) {
		this.rechargeBalanceBeforeChange = rechargeBalanceBeforeChange;
	}

	public BigDecimal getRefundBalanceBeforeChange() {
		return refundBalanceBeforeChange;
	}

	public void setRefundBalanceBeforeChange(BigDecimal refundBalanceBeforeChange) {
		this.refundBalanceBeforeChange = refundBalanceBeforeChange;
	}
	
	/**
	 * 获取先前的奖金金额
	 * @return
	 */
	public BigDecimal getBonusBalanceBeforeChange() {
		return bonusBalanceBeforeChange;
	}
	
	/**
	 * 设置先前的奖金金额
	 * @param bonusBalanceBeforeChange 先前的奖金金额
	 */
	public void setBonusBalanceBeforeChange(BigDecimal bonusBalanceBeforeChange) {
		this.bonusBalanceBeforeChange = bonusBalanceBeforeChange;
	}

	/**
	 * getCreateTime.
	 *
	 * @return 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * setCreateTime.
	 *
	 * @param createTime
	 *            创建时间
	 */
	public void setCreateTime(final Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * getBusinessId.
	 *
	 * @return 业务ID
	 */
	public String getBusinessId() {
		return businessId;
	}

	/**
	 * setBusinessId.
	 *
	 * @param businessId
	 *            业务ID
	 */
	public void setBusinessId(final String businessId) {
		this.businessId = businessId;
	}

	public Long getChangeId() {
		return changeId;
	}

	public void setChangeId(Long changeId) {
		this.changeId = changeId;
	}

	public Long getCashAccountId() {
		return cashAccountId;
	}

	public void setCashAccountId(Long cashAccountId) {
		this.cashAccountId = cashAccountId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}