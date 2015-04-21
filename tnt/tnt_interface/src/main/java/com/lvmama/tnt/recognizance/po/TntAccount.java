package com.lvmama.tnt.recognizance.po;

import java.util.Date;

public class TntAccount implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	public static final Long LVMAMA_ID = 0l;

	private Long accountId;

	/**
	 * 分销商Id或驴妈妈Id,驴妈妈userId=0
	 */
	private Long userId;

	private String type;

	/**
	 * 开户人姓名
	 */
	private String accountName;

	/**
	 * 银行名称或支付宝
	 */
	private String bankName;

	/**
	 * 帐户号码
	 */
	private String bankAccount;

	/**
	 * 财务联系电话
	 */
	private String telephone;

	private Date createTime;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static TntAccount newDefaultLvmamaAccount() {
		TntAccount t = new TntAccount();
		t.setUserId(0l);
		t.setAccountName("洪清华");
		t.setBankName("中国银行徐家汇分行");
		t.setBankAccount("6202101012031204");
		t.setTelephone("021-8855771");
		return t;
	}

}
