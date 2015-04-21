package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class OrdOrderItemMetaPrice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long recordId;
	private Long orderId;
	private Long orderItemMetaId;
	private String settlementStatus;
	private Long supplierId;
	private String supplierName;
	private Long productId;
	private String productName;
	private Long metaProductId;
	private String metaProductName;
	private Long metaBranchId;
	private String branchName;
	private Long productQuantity;
	private Long quantity;
	private Long totalQuantity;
	private String pickTicketPerson;
	private Date visitTime;
	private Long settlementPrice;
	private Long actualSettlementPrice;
	private Long totalSettlementPrice;
	private Date modifyDate;
	private String operatorName;
	private Long modifyActualSettlementPrice;
	private Long modifyTotalSettlementPrice;
	private String changeResult;
	private String changeType;
	private String reason;
	private String remark;
	private String status;
	private String verifiedOperator;
	private Long sellPrice;
	
	public Long getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getVerifiedOperator() {
		return verifiedOperator;
	}
	public void setVerifiedOperator(String verifiedOperator) {
		this.verifiedOperator = verifiedOperator;
	}
	public Long getRecordId() {
		return recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	public String getStatus() {
		return status;
	}
	public String getZhStatus() {
		return Constant.ORD_SETTLEMENT_PRICE_RECORD_STATUS.getCnName(status);
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getChangeResult() {
		return changeResult;
	}
	public String getZhChangeResult() {
		return Constant.ORD_SETTLEMENT_PRICE_CHANGE_RESULT.getCnName(changeResult);
	}
	public void setChangeResult(String changeResult) {
		this.changeResult = changeResult;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public String getModifyDateStr() {
		if(null != modifyDate){
			return DateUtil.formatDate(modifyDate, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Long getModifyActualSettlementPrice() {
		return modifyActualSettlementPrice;
	}
	public float getModifyActualSettlementPriceYuan() { 
		if(null != modifyActualSettlementPrice){
			return PriceUtil.convertToYuan(modifyActualSettlementPrice);
		}
		return 0;
	}
	public String getModifyActualSettlementPriceYuanStr() { 
		DecimalFormat df=new DecimalFormat("0.00");
		return df.format(PriceUtil.convertToYuan(modifyActualSettlementPrice));
	}
	public void setModifyActualSettlementPrice(Long modifyActualSettlementPrice) {
		this.modifyActualSettlementPrice = modifyActualSettlementPrice;
	}
	public Long getModifyTotalSettlementPrice() {
		return modifyTotalSettlementPrice;
	}
	public float getModifyTotalSettlementPriceYuan() {
		if(null != modifyActualSettlementPrice){
			float result = PriceUtil.convertToYuan(modifyTotalSettlementPrice);
			return result;
		}
		return 0;
	}
	public String getModifyTotalSettlementPriceYuanStr() { 
		DecimalFormat df=new DecimalFormat("0.00");
		return df.format(PriceUtil.convertToYuan(modifyTotalSettlementPrice));
	}
	public void setModifyTotalSettlementPrice(Long modifyTotalSettlementPrice) {
		this.modifyTotalSettlementPrice = modifyTotalSettlementPrice;
	}
	public String getChangeType() {
		return changeType;
	}
	public String getZhChangeType() {
		return Constant.ORD_SETTLEMENT_PRICE_CHANGE_TYPE.getCnName(changeType);
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public String getReason() {
		return reason;
	}
	public String getZhReason() {
		return Constant.ORD_SETTLEMENT_PRICE_REASON.getCnName(reason);
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getRemark() {
		return remark+" ";
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getOrderId() {
		return orderId;
	}
	public Long getOrderItemMetaId() {
		return orderItemMetaId;
	}
	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getSettlementStatus() {
		return settlementStatus;
	}
	public String getZhSettlementStatus() {
		return Constant.SETTLEMENT_STATUS.getCnName(settlementStatus);
	}
	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getMetaProductId() {
		return metaProductId;
	}
	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}
	public String getMetaProductName() {
		return metaProductName;
	}
	public void setMetaProductName(String metaProductName) {
		this.metaProductName = metaProductName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public Long getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(Long productQuantity) {
		this.productQuantity = productQuantity;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Long getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(Long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public String getPickTicketPerson() {
		return pickTicketPerson;
	}
	public void setPickTicketPerson(String pickTicketPerson) {
		this.pickTicketPerson = pickTicketPerson;
	}
	public Date getVisitTime() {
		return visitTime;
	}
	public String getVisitTimeStr() {
		if(null != visitTime){
			return DateUtil.formatDate(visitTime, "yyyy-MM-dd");
		}
		return "";
	}
	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}
	public Long getSettlementPrice() {
		return settlementPrice;
	}
	public float getSettlementPriceYuan() {
		if(null != settlementPrice){
			return PriceUtil.convertToYuan(settlementPrice);
		}
		return 0;
	}
	public String getSettlementPriceYuanStr() { 
		DecimalFormat df=new DecimalFormat("0.00");
		return df.format(PriceUtil.convertToYuan(settlementPrice));
	}
	public void setSettlementPrice(Long settlementPrice) {
		this.settlementPrice = settlementPrice;
	}
	public Long getActualSettlementPrice() {
		return actualSettlementPrice;
	}
	public float getActualSettlementPriceYuan() {
		if(null != actualSettlementPrice){
			return PriceUtil.convertToYuan(actualSettlementPrice);
		}
		return 0;
	}
	public String getActualSettlementPriceYuanStr() { 
		DecimalFormat df=new DecimalFormat("0.00");
		return df.format(PriceUtil.convertToYuan(actualSettlementPrice));
	}
	public void setActualSettlementPrice(Long actualSettlementPrice) {
		this.actualSettlementPrice = actualSettlementPrice;
	}
	public Long getTotalSettlementPrice() {
		return totalSettlementPrice;
	}
	public float getTotalSettlementPriceYuan() {
		if(null != totalSettlementPrice){
			return PriceUtil.convertToYuan(totalSettlementPrice);
		}
		return 0;
	}
	public String getTotalSettlementPriceYuanStr() { 
		DecimalFormat df=new DecimalFormat("0.00");
		return df.format(PriceUtil.convertToYuan(totalSettlementPrice));
	}
	public void setTotalSettlementPrice(Long totalSettlementPrice) {
		this.totalSettlementPrice = totalSettlementPrice;
	}
	public Long getMetaBranchId() {
		return metaBranchId;
	}
	public void setMetaBranchId(Long metaBranchId) {
		this.metaBranchId = metaBranchId;
	}
	
}
