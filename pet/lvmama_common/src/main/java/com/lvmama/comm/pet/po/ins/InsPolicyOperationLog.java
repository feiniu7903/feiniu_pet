package com.lvmama.comm.pet.po.ins;

import java.io.Serializable;
import java.util.Date;

public class InsPolicyOperationLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3906732129688748699L;
	private Long operationId;
	private Long policyId;
	private String operator = "SYSTEM";
	private String type;
	private byte[] content;
	private Date createdDate;
	
	public Long getOperationId() {
		return operationId;
	}
	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}
	public Long getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}	
}
