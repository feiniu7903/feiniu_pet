package com.lvmama.comm.bee.po.op;

import java.io.Serializable;
import java.util.Date;

public class FinGroupSettlement implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 539841653597801351L;

	private Long groupSettlementId;

    private String travelGroupCode;

    private Long budgetItemId;

    private String budgetItemName;

    private String budgetItemType;

    private String prdBranchName;

    private Long supplierId;

    private Long targetId;

    private String paymentType;

    private String currency;

    private double exchangeRate;

    private double subtotalCosts;

    private Date settlementDate;

    private double payAmount;

    private String paymentStatus;

    private String remark;

    private Long creator;

    private Date createtime;
    
    private Double reqPayAmount;
    
    private String isUseAdvancedeposits;

    public Long getGroupSettlementId() {
        return groupSettlementId;
    }

    public void setGroupSettlementId(Long groupSettlementId) {
        this.groupSettlementId = groupSettlementId;
    }

    public String getTravelGroupCode() {
        return travelGroupCode;
    }

    public void setTravelGroupCode(String travelGroupCode) {
        this.travelGroupCode = travelGroupCode;
    }

    public Long getBudgetItemId() {
        return budgetItemId;
    }

    public void setBudgetItemId(Long budgetItemId) {
        this.budgetItemId = budgetItemId;
    }

    public String getBudgetItemName() {
        return budgetItemName;
    }

    public void setBudgetItemName(String budgetItemName) {
        this.budgetItemName = budgetItemName;
    }

    public String getBudgetItemType() {
        return budgetItemType;
    }

    public void setBudgetItemType(String budgetItemType) {
        this.budgetItemType = budgetItemType;
    }

    public String getPrdBranchName() {
        return prdBranchName;
    }

    public void setPrdBranchName(String prdBranchName) {
        this.prdBranchName = prdBranchName;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	

    public double getSubtotalCosts() {
		return subtotalCosts;
	}

	public void setSubtotalCosts(double subtotalCosts) {
		this.subtotalCosts = subtotalCosts;
	}

	public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}

	public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

	public Double getReqPayAmount() {
		return reqPayAmount;
	}

	public void setReqPayAmount(Double reqPayAmount) {
		this.reqPayAmount = reqPayAmount;
	}

	public String getIsUseAdvancedeposits() {
		return isUseAdvancedeposits;
	}

	public void setIsUseAdvancedeposits(String isUseAdvancedeposits) {
		this.isUseAdvancedeposits = isUseAdvancedeposits;
	}
    
    
}