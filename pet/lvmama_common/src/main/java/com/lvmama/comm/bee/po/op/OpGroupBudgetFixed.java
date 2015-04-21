package com.lvmama.comm.bee.po.op;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

/**
 * 团预算固定成本明细
 * @author zhaojindong
 *
 */
public class OpGroupBudgetFixed implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7513347854068781574L;

	private Long itemId;
    
    private Long budgetId;
    
    private String travelGroupCode;
    
    private Long prdBranchId;
    
    private String prdBranchName;;
    
    private Long costsItem;
    
    private String costsItemName;

    private double bgCosts;

    private String costsUnit;

    private Long quantity;

    private String currency;
    
    private String currencyName;
    
    private String currencySymbol;

    private Double exchangeRate;

    private double subtotalCosts;

    private double subtotalCostsFc;

    private Long supplierId;
    
    private String supplierName;

    private Long targetId;
    
    private String targetName;

    private String paymentType;

    private double payAmount;

    private String payStatus;

    private String remark;

    private String status;

    private Long creator;

    private Date createtime;
    
    private String type;
    
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }


    public String getCostsUnit() {
        return costsUnit;
    }

    public void setCostsUnit(String costsUnit) {
        this.costsUnit = costsUnit;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
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

  
    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public Long getCostsItem() {
		return costsItem;
	}

	public void setCostsItem(Long costsItem) {
		this.costsItem = costsItem;
	}

	public Long getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(Long budgetId) {
		this.budgetId = budgetId;
	}

	public String getTravelGroupCode() {
		return travelGroupCode;
	}

	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	}

	public String getCostsItemName() {
		return costsItemName;
	}

	public void setCostsItemName(String costsItemName) {
		this.costsItemName = costsItemName;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}
	
	public double getBgCosts() {
		return bgCosts;
	}

	public void setBgCosts(double bgCosts) {
		this.bgCosts = bgCosts;
	}

	public double getSubtotalCosts() {
		return subtotalCosts;
	}

	public void setSubtotalCosts(double subtotalCosts) {
		this.subtotalCosts = subtotalCosts;
	}

	public double getSubtotalCostsFc() {
		return subtotalCostsFc;
	}

	public void setSubtotalCostsFc(double subtotalCostsFc) {
		this.subtotalCostsFc = subtotalCostsFc;
	}

	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}

	public String getSubtotalCostsFcStr() {
		return subtotalCostsFc + currencySymbol;
	}
	public String getSupplierStr(){
		return supplierName + "/" + supplierId;
	}
	public String getTargetStr(){
		return targetName + "/" + targetId;
	}
	
	public String getPaymentTypeName(){
		if("CASH".equals(paymentType)){
			return "现金";
		}else if("TRANSFER".equals(paymentType)){
			return "银行转账";
		}
		return "";
	}
	public String getPayStatusName(){
		return Constant.GROUP_PAY_STATUS.getCnName(payStatus);
	}

	public Long getPrdBranchId() {
		return prdBranchId;
	}

	public void setPrdBranchId(Long prdBranchId) {
		this.prdBranchId = prdBranchId;
	}

	public String getPrdBranchName() {
		return prdBranchName;
	}

	public void setPrdBranchName(String prdBranchName) {
		this.prdBranchName = prdBranchName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}