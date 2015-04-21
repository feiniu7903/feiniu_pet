package com.lvmama.shholiday.vo.order;

import com.lvmama.comm.utils.PriceUtil;

public class OrderModifyInfo {

	private String orderPackageId;
	private String modifyType;
	private String guestRequest;
	private String importantLevel;
	private String IsSelfWill;
	private String refundAmountType;
	private Long   factRefundAmount;
	private String refundReason;
	
	
	public String getOrderPackageId() {
		return orderPackageId;
	}
	public void setOrderPackageId(String orderPackageId) {
		this.orderPackageId = orderPackageId;
	}
	public String getModifyType() {
		return modifyType;
	}
	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}
	public String getGuestRequest() {
		return guestRequest;
	}
	public void setGuestRequest(String guestRequest) {
		this.guestRequest = guestRequest;
	}
	public String getImportantLevel() {
		return importantLevel;
	}
	public void setImportantLevel(String importantLevel) {
		this.importantLevel = importantLevel;
	}
	public String getIsSelfWill() {
		return IsSelfWill;
	}
	public void setIsSelfWill(String isSelfWill) {
		IsSelfWill = isSelfWill;
	}
	public String getRefundAmountType() {
		return refundAmountType;
	}
	public void setRefundAmountType(String refundAmountType) {
		this.refundAmountType = refundAmountType;
	}
	public String getFactRefundAmount() {
		return ""+PriceUtil.convertToYuan(factRefundAmount);
	}
	public void setFactRefundAmount(Long factRefundAmount) {
		this.factRefundAmount = factRefundAmount;
	}
	public String getRefundReason() {
		return refundReason;
	}
	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
	public OrderModifyInfo getModifyInfo(String orderPackageId, Long factRefundAmount) {
		this.orderPackageId = orderPackageId;
		this.factRefundAmount = factRefundAmount;
		this.importantLevel = "IMPORTANT";
		this.modifyType="REFUND";
		this.IsSelfWill="Y";
		this.refundAmountType="R";
		return this;
	}
	
	
}
