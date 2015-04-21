package com.lvmama.distribution.model.lv.common;

import java.util.Date;

import com.lvmama.comm.utils.StringUtil;

/**
 * 分销产品类型对象
 * 
 * @author lipengcheng
 * 
 */
public class ProductBranch {

	private Long branchId;
	private String branchName;
	private Date visitDate;
	private Date leaveDate;
	private Integer quantity;
	private String sellPrice;

	public String getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(String selltPrice) {
		this.sellPrice = selltPrice;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return StringUtil.replaceNullStr(branchName);
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
