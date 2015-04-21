package com.lvmama.clutter.model;

import java.io.Serializable;

public class MobileBranchItem implements Serializable{
	Long branchId;
	Long quantity;
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
}
