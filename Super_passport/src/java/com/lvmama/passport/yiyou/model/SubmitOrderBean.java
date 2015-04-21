package com.lvmama.passport.yiyou.model;

public final class SubmitOrderBean extends HeadBean {
	private String timeStamp;
	private String piaoId;
	private String buyNum;
	private String mobile;
	private String partnerOrderNo;
	
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getPiaoId() {
		return piaoId;
	}
	public void setPiaoId(String piaoId) {
		this.piaoId = piaoId;
	}
	public String getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(String buyNum) {
		this.buyNum = buyNum;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPartnerOrderNo() {
		return partnerOrderNo;
	}
	public void setPartnerOrderNo(String partnerOrderNo) {
		this.partnerOrderNo = partnerOrderNo;
	}
	@Override
	public String toString() {
		return "SubmitOrderBean [sequenceId=" + sequenceId + ", partnerCode="
				+ partnerCode + ", signed=" + signed + ", timeStamp="
				+ timeStamp + ", piaoId=" + piaoId + ", buyNum=" + buyNum
				+ ", mobile=" + mobile + ", partnerOrderNo=" + partnerOrderNo
				+ "]";
	}
}
