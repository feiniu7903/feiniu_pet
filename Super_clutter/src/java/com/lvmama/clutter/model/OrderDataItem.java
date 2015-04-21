package com.lvmama.clutter.model;

import java.io.Serializable;

public class OrderDataItem implements Serializable{
	String shortName;
	String sellPriceYuan;
	Long branchNum;
	Long branchId;
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getSellPriceYuan() {
		return sellPriceYuan;
	}
	public void setSellPriceYuan(String sellPriceYuan) {
		this.sellPriceYuan = sellPriceYuan;
	}
	public Long getBranchNum() {
		return branchNum;
	}
	public void setBranchNum(Long branchNum) {
		this.branchNum = branchNum;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
}
