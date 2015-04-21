package com.lvmama.finance.group.ibatis.po;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;

/**
 * 团结算信息
 * 
 * @author yanggan
 *
 */
public class FinGroupSettlement implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	private Long groupSettlementId;
	private String travelGroupCode;
	private Long budgetItemId;
	private String budgetItemName;
	private String budgetItemType;
	private String prdBranchName;
	private Long supplierId;
	private String supplierName;
	private Long targetId;
	private String targetName;
	private String paymentType;
	private String currency;
	private Double exchangeRate;
	private Double subtotalCosts;
	private Double subtotalCostsFc;
	private Date settlementDate;
	private Double payAmount;
	private String paymentStatus;
	private String remark;
	private Long creator;
	private Date createtime;
	private String settlementPeriod;
	private String currencyName;
	private String isUseAdvancedeposits;
	
	/**
	 * 团实际收入
	 */
	private Double actIncoming;
	/**
	 * 团实际利润
	 */
	private Double actProfit;
	
	/**
	 * 产品经理
	 */
	private String userName;
	/**
	 * 打款时间
	 */
	private Date paymentTime;
	/**
	 * 币种单位
	 */
	private String unit;
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public String getPaymentTimeStr() {
		if(null != paymentTime){
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(paymentTime);
		}
		return "";
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	// 销售产品子类型
	private String routeType;

	private Double payAmountTotal;

	private Double subTotalCostsTotal;
	
	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}
	public Double getPayAmountTotal() {
		if(null == payAmountTotal){
			return 0d;
		}
		return payAmountTotal;
	}

	public void setPayAmountTotal(Double payAmountTotal) {
		this.payAmountTotal = payAmountTotal;
	}

	public Double getSubTotalCostsTotal() {
		if(null == subTotalCostsTotal){
			return 0d;
		}
		return subTotalCostsTotal;
	}

	public void setSubTotalCostsTotal(Double subTotalCostsTotal) {
		this.subTotalCostsTotal = subTotalCostsTotal;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getGroupSettlementId() {
		return this.groupSettlementId;
	}

	public void setGroupSettlementId(Long groupSettlementId) {
		this.groupSettlementId = groupSettlementId;
	}

	public String getTravelGroupCode() {
		return this.travelGroupCode;
	}

	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	}

	public Long getBudgetItemId() {
		return this.budgetItemId;
	}

	public void setBudgetItemId(Long budgetItemId) {
		this.budgetItemId = budgetItemId;
	}

	public String getBudgetItemName() {
		return this.budgetItemName;
	}

	public void setBudgetItemName(String budgetItemName) {
		this.budgetItemName = budgetItemName;
	}

	public String getBudgetItemType() {
		return this.budgetItemType;
	}

	public void setBudgetItemType(String budgetItemType) {
		this.budgetItemType = budgetItemType;
	}

	public String getPrdBranchName() {
		return this.prdBranchName;
	}

	public void setPrdBranchName(String prdBranchName) {
		this.prdBranchName = prdBranchName;
	}

	public Long getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getTargetId() {
		return this.targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getPaymentType() {
		return this.paymentType;
	}

	public String getZhPaymentType() {
		if(null != paymentType){
			if(paymentType.equals("CASH")){
				return "现金";
			}else if(paymentType.equals("TRANSFER")){
				return "银行转账";
			}else{
				return "";
			}
		}
		return "";
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getExchangeRate() {
		return this.exchangeRate;
	}

	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}


	public Date getSettlementDate() {
		return this.settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}


	public String getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreator() {
		return this.creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getTypeStr(){
		if("FIXED".equals(budgetItemType)){
			return "固定成本";
		}else{
			return prdBranchName;
		}
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

	public String getSettlementPeriod() {
		return settlementPeriod;
	}

	public void setSettlementPeriod(String settlementPeriod) {
		this.settlementPeriod = settlementPeriod;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getSettlementDateStr(){
		if (settlementDate == null) {
			return "-";
		} else {
			return DateUtil.getFormatDate(settlementDate, "yyyy-MM-dd");
		}
	}
	public String getBudgetItemNameId(){
		return this.budgetItemName+"/"+budgetItemId;
	}

	public Double getSubtotalCosts() {
		return subtotalCosts;
	}

	public void setSubtotalCosts(Double subtotalCosts) {
		this.subtotalCosts = subtotalCosts;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public Double getSubtotalCostsFc() {
		return subtotalCostsFc;
	}

	public void setSubtotalCostsFc(Double subtotalCostsFc) {
		this.subtotalCostsFc = subtotalCostsFc;
	}

	public String getPaymentStatusStr(){
		if(null != subtotalCosts){
			if(this.subtotalCosts<0){
				if(this.paymentStatus.equals("NOPAY")){
					return "未使用";
				}else if(this.paymentStatus.equals("PAYED")){
					return "已使用";
				}else{
					return "未知";
				}
			}else{
				if(this.paymentStatus.equals("NOPAY")){
					return "未打款";
				}else if(this.paymentStatus.equals("PARTPAY")){
					return "部分打款";
				}else if(this.paymentStatus.equals("PAYED")){
					return "已打款";
				}else{
					return "未知";
				}
			}
			
		}else{
			return "未知";
		}
		
	}

	public Double getActIncoming() {
		return actIncoming;
	}

	public void setActIncoming(Double actIncoming) {
		this.actIncoming = actIncoming;
	}

	public Double getActProfit() {
		return actProfit;
	}

	public void setActProfit(Double actProfit) {
		this.actProfit = actProfit;
	}

	public String getIsUseAdvancedeposits() {
		return isUseAdvancedeposits;
	}

	public void setIsUseAdvancedeposits(String isUseAdvancedeposits) {
		this.isUseAdvancedeposits = isUseAdvancedeposits;
	}
}