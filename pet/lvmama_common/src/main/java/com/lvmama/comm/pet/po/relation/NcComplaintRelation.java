package com.lvmama.comm.pet.po.relation;

import java.io.Serializable;

/**
 * 关系表
 * @author zhushuying
 *
 */
public class NcComplaintRelation implements Serializable {

    private static final long serialVersionUID = 7922175272046311612L;

    private Long relationId;
    private Long complaintId;
    private Long smsId;
    private Long emailId;
	public Long getRelationId() {
		return relationId;
	}
	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}
	public Long getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(Long complaintId) {
		this.complaintId = complaintId;
	}
	public Long getSmsId() {
		return smsId;
	}
	public void setSmsId(Long smsId) {
		this.smsId = smsId;
	}
	public Long getEmailId() {
		return emailId;
	}
	public void setEmailId(Long emailId) {
		this.emailId = emailId;
	}
}
