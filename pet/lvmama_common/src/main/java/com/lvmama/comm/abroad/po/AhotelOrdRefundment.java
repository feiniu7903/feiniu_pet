package com.lvmama.comm.abroad.po;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

public class AhotelOrdRefundment implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6662996189415361742L;
	private Long refundmentId;
	private Long orderId;
	private String refundType;
	private String refundChannel;
	private Long amount;
	private String account;
	private String accountType;
	private String memo;
	private String status;
	/**
	 * 创建时间.
	 */
	private Date createTime;
	/**
	 * 退款时间.
	 */
	private Date refundTime;
	/**
	 * 通过时间.
	 */
	private Date approveTime;
	private String operatorName;
	private Long saleServiceId;
	
	

	private String refundTypeName;
	private String accountTypeName;
	private String refundChannelName;
	private String statusName;
	//下单人
	private String userName;

	/**
	 * 退款查询中的退款处理,当退款状态为末审核时，才出现退款链接.
	 */
	private String visible= "false";
	
	/**
	 * 退款查询中的退款处理,当退款状态为末通过时，才出现通过链接.
	 */
	private String approveVisible = "true";
	
	/**
	 * 批次号.
	 */
	private Long refundmentBatchId;
	/**
	 * 金额-转换元.
	 */
	private Long amountYuan;

	private boolean visibleUpdate;

	public boolean getVisibleUpdate() {
		if (Constant.REFUNDMENT_STATUS.UNVERIFIED.toString().equals(status)
				|| Constant.REFUNDMENT_STATUS.REJECTED.toString()
						.equals(status)) {
			visibleUpdate = true;
		}
		return visibleUpdate;
	}

	public void setVisibleUpdate(boolean visibleUpdate) {
		this.visibleUpdate = visibleUpdate;
	}
	public Long getAmountYuan() {
		if(amount>1){
			return amount/100;
		}else{
			return amount;
		}
	}
	public Long getSaleServiceId() {
		return saleServiceId;
	}

	public void setSaleServiceId(Long saleServiceId) {
		this.saleServiceId = saleServiceId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Long getRefundmentId() {
		return refundmentId;
	}

	public void setRefundmentId(Long refundmentId) {
		this.refundmentId = refundmentId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getRefundChannel() {
		return refundChannel;
	}

	public void setRefundChannel(String refundChannel) {
		this.refundChannel = refundChannel;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public boolean isRefunded() {
		return Constant.REFUNDMENT_STATUS.REFUNDED.name().equalsIgnoreCase(status);
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public String getRefundTypeName() {
		return	Constant.REFUND_TYPE.getCnName(this.refundType);
	}

	public void setRefundTypeName(String refundTypeName) {
		this.refundTypeName = refundTypeName;
	}

	public String getAccountTypeName() {
		return Constant.ACCOUNT_TYPE.getCnName(this.accountType);
	}

	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}

	public String getRefundChannelName() {
		return Constant.REFUND_CHANNEL.getCnName(this.refundChannel);
	}

	public void setRefundChannelName(String refundChannelName) {
		this.refundChannelName = refundChannelName;
	}

	public String getVisible() {
		if (Constant.REFUNDMENT_STATUS.REFUND_VERIFIED.toString().equals(this.status)) {
			this.visible = "true";
		}
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getApproveVisible() {
		if (Constant.REFUNDMENT_STATUS.UNVERIFIED.toString().equals(this.status) ||
				Constant.REFUNDMENT_STATUS.REJECTED.toString().equals(this.status)) {
			this.approveVisible = "true";
		} else {
			this.approveVisible = "false";
		}
		return approveVisible;
	}
	public String getNotVisible() {
		if ("true".equals(visible)) {
			return "false";
		} else {
			return "true";
		}
	}

	public String getStatusName() {
		return Constant.REFUNDMENT_STATUS.getCnName(this.status);
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * getRefundmentBatchId.
	 *
	 * @return 批次号
	 */
	public Long getRefundmentBatchId() {
		return refundmentBatchId;
	}

	/**
	 * setRefundmentBatchId.
	 *
	 * @param refundmentBatchId
	 *            批次号
	 */
	public void setRefundmentBatchId(final Long refundmentBatchId) {
		this.refundmentBatchId = refundmentBatchId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

}