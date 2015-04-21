package com.lvmama.finance.settlement.ibatis.po;

import java.math.BigDecimal;
import java.util.Date;

public class OrdRefundment {
    private Long refundmentId;
    
    private Long refundmentItemId;

    private Long orderId;

    private Date applyTime;

    private BigDecimal fee;

    private Long saleServiceId;

    private String refundType;

    private String refundChannel;

    private BigDecimal amount;

    private String account;

    private String accountType;

    private String memo;

    private String status;

    private Date createTime;

    private Date refundTime;

    private String operatorName;

    private Date approveTime;

    private String refundBankSerialNo;
    
    private Long orderItemMetaId;

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

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Long getSaleServiceId() {
        return saleServiceId;
    }

    public void setSaleServiceId(Long saleServiceId) {
        this.saleServiceId = saleServiceId;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getRefundBankSerialNo() {
        return refundBankSerialNo;
    }

    public void setRefundBankSerialNo(String refundBankSerialNo) {
        this.refundBankSerialNo = refundBankSerialNo;
    }

	public Long getOrderItemMetaId() {
		return orderItemMetaId;
	}

	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}

	public Long getRefundmentItemId() {
		return refundmentItemId;
	}

	public void setRefundmentItemId(Long refundmentItemId) {
		this.refundmentItemId = refundmentItemId;
	}
	
	
    
}