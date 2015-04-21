package com.lvmama.report.po;


public class OrderSaleServiceBasicMV {

	private Long prodProductId;

	private String createTime;

	private String prodProductName;

	private String subProductType;

	private Long refundAmount;

	private Long compensationAmount;

	private Long  normalQuantity;
	
	private Long  complaintQuantity;
	
	private Long  urgencyQuantity;
	
	public Long getNormalQuantity() {
		return normalQuantity;
	}

	public void setNormalQuantity(Long normalQuantity) {
		this.normalQuantity = normalQuantity;
	}

	public Long getComplaintQuantity() {
		return complaintQuantity;
	}

	public void setComplaintQuantity(Long complaintQuantity) {
		this.complaintQuantity = complaintQuantity;
	}

	public Long getUrgencyQuantity() {
		return urgencyQuantity;
	}

	public void setUrgencyQuantity(Long urgencyQuantity) {
		this.urgencyQuantity = urgencyQuantity;
	}

	public Long getProdProductId() {
		return prodProductId;
	}

	public void setProdProductId(Long prodProductId) {
		this.prodProductId = prodProductId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getProdProductName() {
		return prodProductName;
	}

	public void setProdProductName(String prodProductName) {
		this.prodProductName = prodProductName;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Long getCompensationAmount() {
		return compensationAmount;
	}

	public void setCompensationAmount(Long compensationAmount) {
		this.compensationAmount = compensationAmount;
	}
	
	public Long getCompensationAmountYuan() {
		if(compensationAmount!=null){
			return compensationAmount/100;
		}
		return 0l;
	}
	
	public Long getRefundAmountYuan() {
		if(refundAmount!=null){
			return refundAmount/100;
		}
		return 0l;
	}
}
