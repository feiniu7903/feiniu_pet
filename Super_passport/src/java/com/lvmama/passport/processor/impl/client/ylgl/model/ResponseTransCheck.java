package com.lvmama.passport.processor.impl.client.ylgl.model;

/**
 * 交易验证信息的容器对象
 * @author lipengcheng
 * @date Nov 15, 2011
 */
public class ResponseTransCheck {
	private String spSeq;// 原委托请求流水号
	private String transType;// 交易类型
	private String pgoodsId;// 景区id
	private String pgoodsName;// 景区名称
	private String amt;// 交易金额
	private String residuaryAmt;// 剩余金额
	private String residuaryTimes;// 剩余次数
	private String transTime;// 交易时间
	private String status;// 条码当前状态
	private String phoneNo;// 手机号

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

	public String getPgoodsId() {
		return pgoodsId;
	}

	public void setPgoodsId(String pgoodsId) {
		this.pgoodsId = pgoodsId;
	}

	public String getPgoodsName() {
		return pgoodsName;
	}

	public void setPgoodsName(String pgoodsName) {
		this.pgoodsName = pgoodsName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

}
