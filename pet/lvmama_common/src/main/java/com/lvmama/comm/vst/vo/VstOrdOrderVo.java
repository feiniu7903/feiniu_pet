package com.lvmama.comm.vst.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class VstOrdOrderVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long orderId;

	private Long oriOrderId;

	private Long distributorId;
	
	private String distributorName;	//需要通过distributorId查询获得

	private String distributorCode;

	private String orderStatus;

	private String paymentStatus;

	private String resourceStatus;

	private String infoStatus;

	private Date lastCancelTime;

	private Date waitPaymentTime;

	private String cancelCode;

	private String reason;

	private String currencyCode;

	private Long oughtAmount;

	private Long actualAmount;

	private String userId;

	private String guarantee;

	private String paymentTarget;

	private Date createTime;

	private String paymentType;

	private String viewOrderStatus;

	private String invoiceStatus;

	private String remark;

	private String filialeName;

	private Date approveTime;

	private Date visitTime;

	private Date cancelTime;

	private String bookLimitType;

	private String clientIpAddress;

	/**
	 * 最后支付时间
	 */
	private Date paymentTime;

	/**
	 * 后台下单客服
	 */
	private String backUserId;

	/**
	 * 凭证确认状态
	 */
	private String certConfirmStatus;

	/**
	 * 取消凭证确认
	 */
	private String cancelCertConfirmStatus;

	/**
	 * 子项主记录
	 */
	private VstOrdOrderItem mainOrderItem;

	/**
	 * 订单子项
	 */
	List<VstOrdOrderItem> vstOrdOrderItems;

	public VstOrdOrderVo() {
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOriOrderId() {
		return oriOrderId;
	}

	public void setOriOrderId(Long oriOrderId) {
		this.oriOrderId = oriOrderId;
	}

	public Long getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(Long distributorId) {
		this.distributorId = distributorId;
	}

	public String getDistributorCode() {
		return distributorCode;
	}

	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getResourceStatus() {
		return resourceStatus;
	}

	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	public String getInfoStatus() {
		return infoStatus;
	}

	public void setInfoStatus(String infoStatus) {
		this.infoStatus = infoStatus;
	}

	public Date getLastCancelTime() {
		return lastCancelTime;
	}

	public void setLastCancelTime(Date lastCancelTime) {
		this.lastCancelTime = lastCancelTime;
	}

	public Date getWaitPaymentTime() {
		return waitPaymentTime;
	}

	public void setWaitPaymentTime(Date waitPaymentTime) {
		this.waitPaymentTime = waitPaymentTime;
	}

	public String getCancelCode() {
		return cancelCode;
	}

	public void setCancelCode(String cancelCode) {
		this.cancelCode = cancelCode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getOughtAmount() {
		return oughtAmount;
	}

	public void setOughtAmount(Long oughtAmount) {
		this.oughtAmount = oughtAmount;
	}

	public Long getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(Long actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}

	public String getPaymentTarget() {
		return paymentTarget;
	}

	public void setPaymentTarget(String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getViewOrderStatus() {
		return viewOrderStatus;
	}

	public void setViewOrderStatus(String viewOrderStatus) {
		this.viewOrderStatus = viewOrderStatus;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFilialeName() {
		return filialeName;
	}

	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getBookLimitType() {
		return bookLimitType;
	}

	public void setBookLimitType(String bookLimitType) {
		this.bookLimitType = bookLimitType;
	}

	public String getClientIpAddress() {
		return clientIpAddress;
	}

	public void setClientIpAddress(String clientIpAddress) {
		this.clientIpAddress = clientIpAddress;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getBackUserId() {
		return backUserId;
	}

	public void setBackUserId(String backUserId) {
		this.backUserId = backUserId;
	}

	public String getCertConfirmStatus() {
		return certConfirmStatus;
	}

	public void setCertConfirmStatus(String certConfirmStatus) {
		this.certConfirmStatus = certConfirmStatus;
	}

	public String getCancelCertConfirmStatus() {
		return cancelCertConfirmStatus;
	}

	public void setCancelCertConfirmStatus(String cancelCertConfirmStatus) {
		this.cancelCertConfirmStatus = cancelCertConfirmStatus;
	}

	public List<VstOrdOrderItem> getVstOrdOrderItems() {
		return vstOrdOrderItems;
	}

	public void setVstOrdOrderItems(List<VstOrdOrderItem> vstOrdOrderItems) {
		this.vstOrdOrderItems = vstOrdOrderItems;
	}

	public VstOrdOrderItem getMainOrderItem() {
		if (null != vstOrdOrderItems && vstOrdOrderItems.size() > 0) {
			for (VstOrdOrderItem vstOrdOrderItem : vstOrdOrderItems) {
				if (vstOrdOrderItem.hasMainItem()) {
					mainOrderItem = vstOrdOrderItem;
					break;
				}
			}
		}
		return mainOrderItem;
	}

	public void setMainOrderItem(VstOrdOrderItem mainOrderItem) {
		this.mainOrderItem = mainOrderItem;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}


}