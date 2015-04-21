package com.lvmama.comm.vst.vo;

import java.io.Serializable;

/**
 * 结算VO
 * @author ranlongfei 2013-12-18
 * @version
 */
public class VstSuppSupplierSettlementVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1258676021301729297L;

	private Long settleRuleId;

	private Long contractId;

	private Long supplierId;
	
	private String settleName;

	private String accountName;

	private String accountBank;

	private String accountNo;

	private String alipayNo;

	private String alipayName;

	private String lvAccSubject;

	private Long settleCycle;
	
	/**
	 * 结算周期 
	 * @see com.lvmama.comm.vo.Constant.SETTLEMENT_PERIOD
	 */
	private String settlePeriod;

	private String settleType;

	private String ruleType;

	private String payType;

	private String unionBankNo;

	private String settleDesc;

	public Long getSettleRuleId() {
		return settleRuleId;
	}

	public void setSettleRuleId(Long settleRuleId) {
		this.settleRuleId = settleRuleId;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAlipayNo() {
		return alipayNo;
	}

	public void setAlipayNo(String alipayNo) {
		this.alipayNo = alipayNo;
	}

	public String getAlipayName() {
		return alipayName;
	}

	public void setAlipayName(String alipayName) {
		this.alipayName = alipayName;
	}

	public String getLvAccSubject() {
		return lvAccSubject;
	}

	public void setLvAccSubject(String lvAccSubject) {
		this.lvAccSubject = lvAccSubject;
	}

	public Long getSettleCycle() {
		return settleCycle;
	}

	public void setSettleCycle(Long settleCycle) {
		this.settleCycle = settleCycle;
	}
	
	

	public String getSettleType() {
		return settleType;
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getUnionBankNo() {
		return unionBankNo;
	}

	public void setUnionBankNo(String unionBankNo) {
		this.unionBankNo = unionBankNo;
	}

	public String getSettleDesc() {
		return settleDesc;
	}

	public void setSettleDesc(String settleDesc) {
		this.settleDesc = settleDesc;
	}

	public String getSettleName() {
		return settleName;
	}

	public void setSettleName(String settleName) {
		this.settleName = settleName;
	}

	public String getSettlePeriod() {
		return settlePeriod;
	}

	public void setSettlePeriod(String settlePeriod) {
		this.settlePeriod = settlePeriod;
	}
	
	

}
