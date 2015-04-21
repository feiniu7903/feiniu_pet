package com.lvmama.comm.abroad.po;

import java.io.Serializable;
import java.util.Date;

public class AhotelOrdSaleServiceDeal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1357342651801717176L;
	private Long serviceDealId;
	private Long saleServiceId;
	private String dealContent;
	private String operatorName;
	private Date createTime;

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

}