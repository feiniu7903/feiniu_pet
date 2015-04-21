package com.lvmama.finance.settlement.ibatis.po;

import java.util.Date;

public class FinSupplierAllot {
	private Long id;
	private Long supplierId;
	private String userName;
	private String creatorName;
	private Date createTime;
	
	private Long supplierIdAdd;
	private String userNameAdd;

	private String supplierIds;
	private String userNameBatch;
	
	public String getUserNameBatch() {
		return userNameBatch;
	}
	public void setUserNameBatch(String userNameBatch) {
		this.userNameBatch = userNameBatch;
	}
	public String getSupplierIds() {
		return supplierIds;
	}
	public void setSupplierIds(String supplierIds) {
		this.supplierIds = supplierIds;
	}
	public Long getSupplierIdAdd() {
		return supplierIdAdd;
	}
	public void setSupplierIdAdd(Long supplierIdAdd) {
		this.supplierIdAdd = supplierIdAdd;
	}
	public String getUserNameAdd() {
		return userNameAdd;
	}
	public void setUserNameAdd(String userNameAdd) {
		this.userNameAdd = userNameAdd;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
