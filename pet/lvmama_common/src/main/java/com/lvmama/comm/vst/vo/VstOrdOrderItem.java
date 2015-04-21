package com.lvmama.comm.vst.vo;

import java.io.Serializable;
import java.util.Date;

public class VstOrdOrderItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7931108218080321331L;

	private Long orderItemId;

	private Long orderId;

	private Long categoryId;

	private Long branchId;

	private Long contractId;

	private Long orderPackId;

	private Long suppGoodsId;

	private String mainItem;

	private String productName;

	private Long price;

	private Long actualSettlementPrice;

	private Long settlementPrice;

	private Long quantity;

	private Long totalSettlementPrice;

	private Date visitTime;

	private String needResourceConfirm;

	private String resourceStatus;

	private String infoStatus;

	private Long marketPrice;

	private String performStatus;

	private String performStatusCode;

	private String cancelStrategy;

	private String content;

	private String settlementStatus;
	
	private String suppGoodsName;
	
	private Long productId;
	
	private Long supplierId;
	
	private String deductType;
	
	// 退款明细中的金额类型
	private String amountType;
	// 退款明细中的金额
	private Long amountValue;
	// 实际损失
	private Long actualLoss;
	// 备注
	private String memo;
	//是否有退款申请("true"/"false").
	private String refund;

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public Long getOrderPackId() {
		return orderPackId;
	}

	public void setOrderPackId(Long orderPackId) {
		this.orderPackId = orderPackId;
	}

	public Long getSuppGoodsId() {
		return suppGoodsId;
	}

	public void setSuppGoodsId(Long suppGoodsId) {
		this.suppGoodsId = suppGoodsId;
	}

	public String getMainItem() {
		return mainItem;
	}

	public void setMainItem(String mainItem) {
		this.mainItem = mainItem;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName == null ? null : productName.trim();
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getActualSettlementPrice() {
		return actualSettlementPrice;
	}

	public void setActualSettlementPrice(Long actualSettlementPrice) {
		this.actualSettlementPrice = actualSettlementPrice;
	}

	public Long getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(Long settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getTotalSettlementPrice() {
		return totalSettlementPrice;
	}

	public void setTotalSettlementPrice(Long totalSettlementPrice) {
		this.totalSettlementPrice = totalSettlementPrice;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public String getNeedResourceConfirm() {
		return needResourceConfirm;
	}

	public void setNeedResourceConfirm(String needResourceConfirm) {
		this.needResourceConfirm = needResourceConfirm == null ? null : needResourceConfirm.trim();
	}

	public String getResourceStatus() {
		return resourceStatus;
	}

	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus == null ? null : resourceStatus.trim();
	}

	public String getInfoStatus() {
		return infoStatus;
	}

	public void setInfoStatus(String infoStatus) {
		this.infoStatus = infoStatus == null ? null : infoStatus.trim();
	}

	public Long getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getPerformStatus() {
		return performStatus;
	}

	public void setPerformStatus(String performStatus) {
		this.performStatus = performStatus == null ? null : performStatus.trim();
	}

	public String getPerformStatusCode() {
		return performStatusCode;
	}

	public void setPerformStatusCode(String performStatusCode) {
		this.performStatusCode = performStatusCode == null ? null : performStatusCode.trim();
	}

	public String getCancelStrategy() {
		return cancelStrategy;
	}

	public void setCancelStrategy(String cancelStrategy) {
		this.cancelStrategy = cancelStrategy == null ? null : cancelStrategy.trim();
	}

	public String getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus == null ? null : settlementStatus.trim();
	}

	public String getSuppGoodsName() {
		return suppGoodsName;
	}

	public void setSuppGoodsName(String suppGoodsName) {
		this.suppGoodsName = suppGoodsName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public boolean hasMainItem(){
		return "true".equalsIgnoreCase(mainItem);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDeductType() {
		return deductType;
	}

	public void setDeductType(String deductType) {
		this.deductType = deductType;
	}

	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public Long getAmountValue() {
		return amountValue;
	}

	public void setAmountValue(Long amountValue) {
		this.amountValue = amountValue;
	}

	public Long getActualLoss() {
		return actualLoss;
	}

	public void setActualLoss(Long actualLoss) {
		this.actualLoss = actualLoss;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRefund() {
		return refund;
	}

	public void setRefund(String refund) {
		this.refund = refund;
	}
	
	
}