package com.lvmama.passport.processor.impl.client.ylgl.model;

/**
 * 交易验证信息的主要容器对象
 * @author lipengcheng
 * @date Nov 15, 2011
 */
public class ErVerifyInfoSyncRequest {

	private String spSeq;// 原委托请求流水号
	private String transType;// 交易类型
	private String servCode;//
	private String amt;// 交易金额
	private String residuaryAmt;// 剩余金额
	private String residuaryTimes;// 剩余次数
	private String transTime;// 交易时间
	private String seqStatus;// 条码当前状态
	private String phoneNo;// 手机号
	private String merchantId;// 使用商户号
	private String reqSeq;// 系统流水号
	private String organization;// 机构码
	private String terminalId;// 终端号
	private String terminalSeq;// 终端流水号
	private String batchNo;// 活动号
	

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getTerminalSeq() {
		return terminalSeq;
	}

	public void setTerminalSeq(String terminalSeq) {
		this.terminalSeq = terminalSeq;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getReqSeq() {
		return reqSeq;
	}

	public void setReqSeq(String reqSeq) {
		this.reqSeq = reqSeq;
	}

	public String getSpSeq() {
		return spSeq;
	}

	public void setSpSeq(String spSeq) {
		this.spSeq = spSeq;
	}


	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}


	public String getServCode() {
		return servCode;
	}

	public void setServCode(String servCode) {
		this.servCode = servCode;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getResiduaryAmt() {
		return residuaryAmt;
	}

	public void setResiduaryAmt(String residuaryAmt) {
		this.residuaryAmt = residuaryAmt;
	}

	public String getResiduaryTimes() {
		return residuaryTimes;
	}

	public void setResiduaryTimes(String residuaryTimes) {
		this.residuaryTimes = residuaryTimes;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getSeqStatus() {
		return seqStatus;
	}

	public void setSeqStatus(String seqStatus) {
		this.seqStatus = seqStatus;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

}
