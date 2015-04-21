package com.lvmama.finance.group.ibatis.vo;

import com.google.zxing.common.StringUtils;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 固定成本明细
 * 
 * @author yanggan
 * 
 */
public class GroupBudgetFixed {
	
	private Long itemId;
	private Long costsItemId;
	private String costsItemName;
	private String branchName;
	private Double bgCosts;
	private Long quantity;
	private String currency;
	private String currencySymbol;
	private Double exchangeRate;
	private Double subTotalCosts;
	private Double subTotalCostsFc;
	private Long supplierId;
	private String supplierName;
	private Long targetId;
	private String targetName;
	private String paymentType;
	private Double payAmount;
	private String payStatus;
	private String remark;
	private String type;
	
	public String getPayStatusZH(){
		if(StringUtil.isEmptyString(payStatus)){
			return null;
		}
		return Constant.GROUP_PAY_STATUS.getCnName(payStatus);
	}
	public String getCostsItemName() {
		return costsItemName;
	}

	public void setCostsItemName(String costsItemName) {
		this.costsItemName = costsItemName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Double getBgCosts() {
		return bgCosts;
	}

	public void setBgCosts(Double bgCosts) {
		this.bgCosts = bgCosts;
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

	public Double getSubTotalCosts() {
		return subTotalCosts;
	}

	public void setSubTotalCosts(Double subTotalCosts) {
		this.subTotalCosts = subTotalCosts;
	}

	public Double getSubTotalCostsFc() {
		return subTotalCostsFc;
	}

	public void setSubTotalCostsFc(Double subTotalCostsFc) {
		this.subTotalCostsFc = subTotalCostsFc;
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

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public Long getCostaItemId() {
		return costsItemId;
	}

	public void setCostaItemId(Long costaItemId) {
		this.costsItemId = costaItemId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
