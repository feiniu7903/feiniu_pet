package com.lvmama.comm.bee.po.tmall;

import java.io.Serializable;

public class TaobaoTravelComboType implements Serializable {
	private static final long serialVersionUID = 5711311663986369453L;
	private Long id;
	private Long travelComboId;
	private String comboType;
	private Long prodBranchId;
	private String branchName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTravelComboId() {
		return travelComboId;
	}
	public void setTravelComboId(Long travelComboId) {
		this.travelComboId = travelComboId;
	}
	public String getComboType() {
		return comboType;
	}
	public void setComboType(String comboType) {
		this.comboType = comboType;
	}
	public Long getProdBranchId() {
		return prodBranchId;
	}
	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

}
