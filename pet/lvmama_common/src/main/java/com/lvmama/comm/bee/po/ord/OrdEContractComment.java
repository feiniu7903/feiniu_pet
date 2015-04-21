package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

public class OrdEContractComment implements Serializable {

	private static final long serialVersionUID = 4784422470550848780L;
	private Long id;					//合同备注ID
	private String eContractId;			//合同编号
	private String contractComment;		//合同备注
	private String createdUser;			//创建人
	private Date createdDate;			//创建时间
	/**
	 * 合同状态.
	 */
	private String econtractStatus;     
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEContractId() {
		return eContractId;
	}
	public void setEcontractId(String eContractId) {
		this.eContractId = eContractId;
	}
	public String getContractComment() {
		return contractComment;
	}
	public void setContractComment(String contractComment) {
		this.contractComment = contractComment;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getEcontractStatus() {
		return econtractStatus;
	}
	public void setEcontractStatus(String econtractStatus) {
		this.econtractStatus = econtractStatus;
	}
	
}
