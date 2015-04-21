package com.lvmama.comm.pet.po.fin;

import java.util.Date;

public class FinGLInterfaceDTO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5019401563916376334L;
	private String accountType;// 做账类型
	private Long productId;
	private String productType;
	private String subProductType;
	private Date visitTime;
	private String filialeName;
	private String glStatus;
	private Long refundedAmount;
	private Long paidAmount;
	private Long orderId;
	private String isForegin;
	private String regionName;
	private String physical;
	private Long refundmentId;
	private Long penaltyAmount;
	private Long amount;
	private Date refundTime;
	private Long ordItemProdId;
	private String branchName;
	private String productName;
	private Date cancelTime;
	private Long oriOrderId;
	private String paymentStatus;
	private Long actualPay;
	private String reconGlStatus;
	private Long reconResultId;

	private Date paymentFinishTime;
	private String travelGroupCode;
	public Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	public Long getOriOrderId() {
		return oriOrderId;
	}
	public void setOriOrderId(Long oriOrderId) {
		this.oriOrderId = oriOrderId;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}
	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	/**
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}
	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}
	/**
	 * @return the subProductType
	 */
	public String getSubProductType() {
		return subProductType;
	}
	/**
	 * @param subProductType the subProductType to set
	 */
	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}
	/**
	 * @return the visitTime
	 */
	public Date getVisitTime() {
		return visitTime;
	}
	/**
	 * @param visitTime the visitTime to set
	 */
	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}
	/**
	 * @return the filialeName
	 */
	public String getFilialeName() {
		return filialeName;
	}
	/**
	 * @param filialeName the filialeName to set
	 */
	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}
	/**
	 * @return the glStatus
	 */
	public String getGlStatus() {
		return glStatus;
	}
	/**
	 * @param glStatus the glStatus to set
	 */
	public void setGlStatus(String glStatus) {
		this.glStatus = glStatus;
	}
	/**
	 * @return the refundedAmount
	 */
	public Long getRefundedAmount() {
		return refundedAmount;
	}
	/**
	 * @param refundedAmount the refundedAmount to set
	 */
	public void setRefundedAmount(Long refundedAmount) {
		this.refundedAmount = refundedAmount;
	}
	/**
	 * @return the paidAmount
	 */
	public Long getPaidAmount() {
		return paidAmount;
	}
	/**
	 * @param paidAmount the paidAmount to set
	 */
	public void setPaidAmount(Long paidAmount) {
		this.paidAmount = paidAmount;
	}
	/**
	 * @return the orderId
	 */
	public Long getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the isForegin
	 */
	public String getIsForegin() {
		return isForegin;
	}
	/**
	 * @param isForegin the isForegin to set
	 */
	public void setIsForegin(String isForegin) {
		this.isForegin = isForegin;
	}
	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}
	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	/**
	 * @return the physical
	 */
	public String getPhysical() {
		return physical;
	}
	/**
	 * @param physical the physical to set
	 */
	public void setPhysical(String physical) {
		this.physical = physical;
	}
	/**
	 * @return the refundmentId
	 */
	public Long getRefundmentId() {
		return refundmentId;
	}
	/**
	 * @param refundmentId the refundmentId to set
	 */
	public void setRefundmentId(Long refundmentId) {
		this.refundmentId = refundmentId;
	}
	/**
	 * @return the penaltyAmount
	 */
	public Long getPenaltyAmount() {
		return penaltyAmount;
	}
	/**
	 * @param penaltyAmount the penaltyAmount to set
	 */
	public void setPenaltyAmount(Long penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}
	/**
	 * @return the amount
	 */
	public Long getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	/**
	 * @return the refundTime
	 */
	public Date getRefundTime() {
		return refundTime;
	}
	/**
	 * @param refundTime the refundTime to set
	 */
	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}
	/**
	 * @return the ordItemProdId
	 */
	public Long getOrdItemProdId() {
		return ordItemProdId;
	}
	/**
	 * @param ordItemProdId the ordItemProdId to set
	 */
	public void setOrdItemProdId(Long ordItemProdId) {
		this.ordItemProdId = ordItemProdId;
	}
	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}
	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Long getActualPay() {
		return actualPay;
	}
	public void setActualPay(Long actualPay) {
		this.actualPay = actualPay;
	}
	public String getReconGlStatus() {
		return reconGlStatus;
	}
	public void setReconGlStatus(String reconGlStatus) {
		this.reconGlStatus = reconGlStatus;
	}
	public Long getReconResultId() {
		return reconResultId;
	}
	public void setReconResultId(Long reconResultId) {
		this.reconResultId = reconResultId;
	}
	public Date getPaymentFinishTime() {
		return paymentFinishTime;
	}
	public void setPaymentFinishTime(Date paymentFinishTime) {
		this.paymentFinishTime = paymentFinishTime;
	}
	public String getTravelGroupCode() {
		return travelGroupCode;
	}
	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	}

}
