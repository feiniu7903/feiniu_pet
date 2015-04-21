package com.lvmama.passport.processor.impl.client.ylgl.model;

/**
 * 交易送达的主要容器对象
 * @author lipengcheng
 * @date Nov 15, 2011
 */
public class ErDeliverReportSyncRequest {

	private String organization;// 机构码
	private String spSeq;// 原委托请求流水号
	private String deliverTime;// 递送时间yyyyMMddHHmmss
	private String deliverStatus;// 递送代码,短彩信网关返回的递送代码，0000为递送成功，其它为递送失败
	private String deliverDetail;// 递送代码说明

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getSpSeq() {
		return spSeq;
	}

	public void setSpSeq(String spSeq) {
		this.spSeq = spSeq;
	}

	public String getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(String deliverTime) {
		this.deliverTime = deliverTime;
	}

	public String getDeliverStatus() {
		return deliverStatus;
	}

	public void setDeliverStatus(String deliverStatus) {
		this.deliverStatus = deliverStatus;
	}

	public String getDeliverDetail() {
		return deliverDetail;
	}

	public void setDeliverDetail(String deliverDetail) {
		this.deliverDetail = deliverDetail;
	}

}
