package com.lvmama.comm.bee.po.op;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

/**
 * 团预算产品成本明细
 * @author zhaojindong
 *
 */
public class OpGroupBudgetProd implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4595082888714037368L;

	private Long itemId;
    
    private String costsItem;
    
    private Long prdBranchId;
    
    private String prdBranchName;
    
    private String productName;

    private double bgCosts;

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
    
    //成人数
    private Long audltQuantity;
    
    //儿童数
    private Long childQuantity;
    
    //销售价
    private Long marketPrice;
    			   
    private String travelGroupCode;
    
    private Long budgetId; 
    
    private String type;
    
    //销售产品子产品类型
    private String subProductType;
    
    private String isInCost;	//是否计入总成本
    
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getCostsItem() {
        return costsItem;
    }

    public void setCostsItem(String costsItem) {
        this.costsItem = costsItem;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

  
    public double getBgCosts() {
		return isSelfHelpBus()?0:bgCosts;
	}

	public void setBgCosts(double bgCosts) {
		this.bgCosts = bgCosts;
	}

	public double getSubtotalCosts() {
		return isSelfHelpBus()?0:subtotalCosts;
	}

	public void setSubtotalCosts(double subtotalCosts) {
		this.subtotalCosts = subtotalCosts;
	}

	public double getSubtotalCostsFc() {
		return isSelfHelpBus()?0:subtotalCostsFc;
	}

	public void setSubtotalCostsFc(double subtotalCostsFc) {
		this.subtotalCostsFc = subtotalCostsFc;
	}

	public double getPayAmount() {
		return isSelfHelpBus()?0:payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
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

	public Long getAudltQuantity() {
		return audltQuantity;
	}

	public void setAudltQuantity(Long audltQuantity) {
		this.audltQuantity = audltQuantity;
	}

	public Long getChildQuantity() {
		return childQuantity;
	}

	public void setChildQuantity(Long childQuantity) {
		this.childQuantity = childQuantity;
	}

	public Long getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	

	public String getTravelGroupCode() {
		return travelGroupCode;
	}

	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	}

	public Long getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(Long budgetId) {
		this.budgetId = budgetId;
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
	
	public String getSubtotalCostsFcStr() {
		return String.valueOf(subtotalCostsFc);
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}
	
	private boolean isSelfHelpBus(){
		return Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.name().equals(subProductType);
	}

	public String getIsInCost() {
		return isInCost;
	}

	public void setIsInCost(String isInCost) {
		this.isInCost = isInCost;
	}
	
	public String getIsInCostStr(){
		if("Y".equals(isInCost)){
			return "是";
		}else if("N".equals(isInCost)){
			return "否";
		}
		return "";
	}
	
	
}