package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.pet.po.sup.SupSupplier;

public class OrdSaleServiceDeal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4967984069303706597L;
	private Long serviceDealId;
	private Long saleServiceId;
	private String dealContent;
	private String operatorName;
	private Date createTime;
	private String supplierName;

	public Long getServiceDealId() {
		return serviceDealId;
	}

	public void setServiceDealId(Long serviceDealId) {
		this.serviceDealId = serviceDealId;
	}

	public Long getSaleServiceId() {
		return saleServiceId;
	}

	public void setSaleServiceId(Long saleServiceId) {
		this.saleServiceId = saleServiceId;
	}

	public String getDealContent() {
		return dealContent;
	}

	public void setDealContent(String dealContent) {
		this.dealContent = dealContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
}