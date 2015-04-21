package com.lvmama.comm.bee.po.distribution;

import java.io.Serializable;
import java.util.Date;

public class DistributorProductBlacklist implements Serializable {
	private static final long serialVersionUID = 7364520260903149465L;
	private Long distributorInfoId;
	private String distributorName;
	private Long prodBranchId;
	private String operatorName;
	private Date createTime;
	
	public Long getDistributorInfoId() {
		return distributorInfoId;
	}
	public void setDistributorInfoId(Long distributorInfoId) {
		this.distributorInfoId = distributorInfoId;
	}
	public Long getProdBranchId() {
		return prodBranchId;
	}
	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDistributorName() {
		return distributorName;
	}
	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
}
