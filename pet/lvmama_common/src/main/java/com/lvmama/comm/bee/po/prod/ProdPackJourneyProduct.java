package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

public class ProdPackJourneyProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6496827831162579140L;
	
	private Long prodPackJourneyId;
	private Long prodJourneyPackId;
	private Long journeyProductId;
	private Long prodBranchId;
	
	
	
	public Long getProdPackJourneyId() {
		return prodPackJourneyId;
	}
	public void setProdPackJourneyId(Long prodPackJourneyId) {
		this.prodPackJourneyId = prodPackJourneyId;
	}
	public Long getProdJourneyPackId() {
		return prodJourneyPackId;
	}
	public void setProdJourneyPackId(Long prodJourneyPackId) {
		this.prodJourneyPackId = prodJourneyPackId;
	}
	public Long getJourneyProductId() {
		return journeyProductId;
	}
	public void setJourneyProductId(Long journeyProductId) {
		this.journeyProductId = journeyProductId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getProdBranchId() {
		return prodBranchId;
	}
	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}

	
	
}
