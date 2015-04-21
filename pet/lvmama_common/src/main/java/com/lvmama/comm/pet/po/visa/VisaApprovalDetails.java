package com.lvmama.comm.pet.po.visa;

import java.io.Serializable;

public class VisaApprovalDetails implements Serializable {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -8182426914927699125L;
	private Long detailsId;
	private Long visaApprovalId;
	private String title;
	private String content;
	private String approvalStatus;
	private String memo;
	
	
	public Long getDetailsId() {
		return detailsId;
	}
	public void setDetailsId(Long detailsId) {
		this.detailsId = detailsId;
	}
	public Long getVisaApprovalId() {
		return visaApprovalId;
	}
	public void setVisaApprovalId(Long visaApprovalId) {
		this.visaApprovalId = visaApprovalId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
