package com.lvmama.comm.pet.po.pay;

import java.io.Serializable;

/**
 * 支付 收款银行
 * @author ZHANG Nan
 *
 */
public class PayReceivingBank implements Serializable {

	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = 1060323294658184099L;
	/**
	 * 主键
	 */
	private long receivingBankId;
	/**
	 * 公司名、户名 
	 */
	private String receivingAccount;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 账户性质及用途
	 */
	private String receivingAccountType;
	/**
	 * 银行卡号
	 */
	private String bankCardNo;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 使用状态(禁用=FORBIDDEN、启动=ENABLE)
	 */
	private String status;
	public long getReceivingBankId() {
		return receivingBankId;
	}
	public void setReceivingBankId(long receivingBankId) {
		this.receivingBankId = receivingBankId;
	}
	public String getReceivingAccount() {
		return receivingAccount;
	}
	public void setReceivingAccount(String receivingAccount) {
		this.receivingAccount = receivingAccount;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getReceivingAccountType() {
		return receivingAccountType;
	}
	public void setReceivingAccountType(String receivingAccountType) {
		this.receivingAccountType = receivingAccountType;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
