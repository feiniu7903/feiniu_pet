package com.lvmama.report.vo;

/**
 * 供应商排行分析表
 * 
 * @author yangchen
 * 
 */
public class SupportRankAnalysisVo {
	private String supplierName;
	private Long orderQuantity;
	private Long personQuantity;
	private Long roomQuantity;
	private Long suitQuantity;

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Long getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Long orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public Long getPersonQuantity() {
		return personQuantity;
	}

	public void setPersonQuantity(Long personQuantity) {
		this.personQuantity = personQuantity;
	}

	public Long getRoomQuantity() {
		return roomQuantity;
	}

	public void setRoomQuantity(Long roomQuantity) {
		this.roomQuantity = roomQuantity;
	}

	public Long getSuitQuantity() {
		return suitQuantity;
	}

	public void setSuitQuantity(Long suitQuantity) {
		this.suitQuantity = suitQuantity;
	}

}
