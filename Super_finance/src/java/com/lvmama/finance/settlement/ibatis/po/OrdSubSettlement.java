package com.lvmama.finance.settlement.ibatis.po;

import java.sql.Date;

/**
 * 结算子单
 * 
 * @author yanggan
 * 
 */
public class OrdSubSettlement {

	private Long subSettlementId;
	private Long settlementId;
	private Long metaProductId;
	private String metaProductName;
	private Double payAmount;
	private Double receiveAmount;
	private Date beginDate;
	private Date endDate;
	private String memo;
	private String operatorName;
	private Date createTime;
	private String status;
	private Long metaBranchId;
	private String branchName;
	
	public OrdSubSettlement() {
	}

	public OrdSubSettlement(Long subSettlementId) {
		this.subSettlementId = subSettlementId;
	}

	public OrdSubSettlement(Long subSettlementId, Long settlementId, Long metaProductId, Double payAmount, Double receiveAmount, Date beginDate, Date endDate, String memo, String operatorName, Date createTime) {
		this.subSettlementId = subSettlementId;
		this.settlementId = settlementId;
		this.metaProductId = metaProductId;
		this.payAmount = payAmount;
		this.receiveAmount = receiveAmount;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.memo = memo;
		this.operatorName = operatorName;
		this.createTime = createTime;
	}

	public Long getSubSettlementId() {
		return this.subSettlementId;
	}

	public void setSubSettlementId(Long subSettlementId) {
		this.subSettlementId = subSettlementId;
	}

	public Long getSettlementId() {
		return this.settlementId;
	}

	public void setSettlementId(Long settlementId) {
		this.settlementId = settlementId;
	}

	public Long getMetaProductId() {
		return this.metaProductId;
	}

	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}

	public Double getPayAmount() {
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

	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMetaProductName() {
		return metaProductName;
	}

	public void setMetaProductName(String metaProductName) {
		this.metaProductName = metaProductName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getMetaBranchId() {
		return metaBranchId;
	}

	public void setMetaBranchId(Long metaBranchId) {
		this.metaBranchId = metaBranchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	
}