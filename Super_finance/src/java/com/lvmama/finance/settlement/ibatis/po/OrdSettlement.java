package com.lvmama.finance.settlement.ibatis.po;

import java.util.Date;

import com.lvmama.comm.utils.DateUtil;

/**
 * 结算单
 * 
 * @author yanggan
 *
 */
public class OrdSettlement {


	private Long settlementId;
	private Date settlementTime;
	private Long targetId;
	private String targetName;
	private Double payAmount;
	private Double receiveAmount;
	private String status;
	private Date confirmTime;
	private String operatorName;
	private Date beginTime;
	private Date endTime;
	private String memo;
	private Date createTime;
	private String paymentTarget;
	private Double payedAmount;
	private String createTimeStr;
	
	//固化结算对象字段
	private String settlementPeriod;
	private Long advancedDays;
	private String bankAccountName;
	private String bankName;
	private String bankAccount;
	private String alipayAccount;
	private String alipayName;
	private String targetType;
	private String bankLines;
	private String companyId;
	
	private Long syncVersion;
	//用户ID
	private Long userId;
	//结算单类型
	private String settlementType;

	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 供应商的预存款余额
	 */
	private Double advancedepositsBal;
	
	private String advCurrency;
	/**
	 * 供应商名称
	 */
	private String supplierName;
	/**
	 * 供应商ID
	 */
	private Long supplierId;
	
	/**
	 * 结算对象
	 */
	private SupSettlementTarget target;
	
	// Constructors

	public String getCreateTimeStr() {
		createTimeStr = DateUtil.getFormatDate(createTime, "yyyy-MM-dd HH:mm");
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	/** default constructor */
	public OrdSettlement() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/** minimal constructor */
	public OrdSettlement(Long settlementId) {
		this.settlementId = settlementId;
	}

	/** full constructor */
	public OrdSettlement(Long settlementId, Date settlementTime, Long targetId, Double payAmount, Double receiveAmount, String status, Date confirmTime, String operatorName, Date beginTime, Date endTime, String memo, Date createTime, String paymentTarget, Double payedAmount) {
		this.settlementId = settlementId;
		this.settlementTime = settlementTime;
		this.targetId = targetId;
		this.payAmount = payAmount;
		this.receiveAmount = receiveAmount;
		this.status = status;
		this.confirmTime = confirmTime;
		this.operatorName = operatorName;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.memo = memo;
		this.createTime = createTime;
		this.paymentTarget = paymentTarget;
		this.payedAmount = payedAmount;
	}

	// Property accessors

	public Long getSettlementId() {
		return this.settlementId;
	}

	public void setSettlementId(Long settlementId) {
		this.settlementId = settlementId;
	}

	public Date getSettlementTime() {
		return this.settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}

	public Long getTargetId() {
		return this.targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Double getPayAmount() {
		if(payAmount == null){
			return 0.00d;
		}
		return this.payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Double getReceiveAmount() {
		return this.receiveAmount;
	}

	public void setReceiveAmount(Double receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getConfirmTime() {
		return this.confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPaymentTarget() {
		return this.paymentTarget;
	}

	public void setPaymentTarget(String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}

	public Double getPayedAmount() {
		if(payedAmount == null){
			return 0.00D;
		}
		return this.payedAmount;
	}

	public void setPayedAmount(Double payedAmount) {
		this.payedAmount = payedAmount;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
	public Integer getPayStatus(){
		if(this.payedAmount!= null && this.payedAmount>0){
			return 1;
		}else{
			return 0;
		}
	}

	public Double getAdvancedepositsBal() {
		return advancedepositsBal;
	}

	public Double getAdvancedepositsBalCNY() {
		if("CNY".equals(advCurrency)){
			return advancedepositsBal;
		}else{
			return 0d;
		}
	}

	public void setAdvancedepositsBal(Double advancedepositsBal) {
		this.advancedepositsBal = advancedepositsBal;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public SupSettlementTarget getTarget() {
		return target;
	}

	public void setTarget(SupSettlementTarget target) {
		this.target = target;
	}

	public String getSettlementPeriod() {
		return settlementPeriod;
	}

	public void setSettlementPeriod(String settlementPeriod) {
		this.settlementPeriod = settlementPeriod;
	}

	public Long getAdvancedDays() {
		return advancedDays;
	}

	public void setAdvancedDays(Long advancedDays) {
		this.advancedDays = advancedDays;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
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

	public String getAlipayAccount() {
		return alipayAccount;
	}

	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}

	public String getAlipayName() {
		return alipayName;
	}

	public void setAlipayName(String alipayName) {
		this.alipayName = alipayName;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getBankLines() {
		return bankLines;
	}

	public void setBankLines(String bankLines) {
		this.bankLines = bankLines;
	}

	public Long getSyncVersion() {
		return syncVersion;
	}

	public void setSyncVersion(Long syncVersion) {
		this.syncVersion = syncVersion;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSettlementType() {
		return settlementType;
	}

	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}

	public String getAdvCurrency() {
		return advCurrency;
	}

	public void setAdvCurrency(String advCurrency) {
		this.advCurrency = advCurrency;
	}
}