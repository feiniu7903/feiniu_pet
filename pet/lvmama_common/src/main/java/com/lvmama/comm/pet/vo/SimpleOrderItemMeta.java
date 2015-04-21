package com.lvmama.comm.pet.vo;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;

public class SimpleOrderItemMeta implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 457181420397451273L;
	/**
	 * 订单子子项ID
	 */
	protected Long orderItemMetaId;
	/**
	 * 订单号
	 */
	protected Long orderId;
	/**
	 * 销售产品ID
	 */
	protected Long productId;
	/**
	 * 销售产品名称
	 */
	protected String productName;
	/**
	 * 采购产品ID
	 */
	protected Long metaProductId;
	/**
	 * 采购产品名称
	 */
	protected String metaProductName;
	/**
	 * 供应商ID
	 */
	protected Long supplierId;
	
	/**
	 * 供应商名称
	 */
	protected String supplierName;
	/**
	 * 我方结算主体
	 */
	protected String settlementCompanyName;
	/**
	 * 产品数（打包数量）
	 */
	protected Long productQuantity;
	/**
	 * 结算对象ID
	 */
	protected Long settlementTargetId;
	/**
	 * 结算对象名称
	 */
	protected String settlementTargetName;
	/**
	 * 购买数（即订单子项中的数量)/张数
	 */
	protected Long quantity;
	/**
	 * 取票人
	 */
	protected String pickTicketPerson;
	/**
	 * 游玩时间
	 */
	protected Date visitDate;
	/**
	 * 实际履行时间
	 */
	protected Date performTime;
	
	/**
	 * 采购产品的结算状态
	 */
	protected String settlementStatus;

    
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Long productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Long getSettlementTargetId() {
		return settlementTargetId;
	}

	public void setSettlementTargetId(Long settlementTargetId) {
		this.settlementTargetId = settlementTargetId;
	}

	public String getSettlementTargetName() {
		return settlementTargetName;
	}

	public void setSettlementTargetName(String settlementTargetName) {
		this.settlementTargetName = settlementTargetName;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getPickTicketPerson() {
		return pickTicketPerson;
	}

	public void setPickTicketPerson(String pickTicketPerson) {
		this.pickTicketPerson = pickTicketPerson;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public Date getPerformTime() {
		return performTime;
	}

	public void setPerformTime(Date performTime) {
		this.performTime = performTime;
	}

	public String getVisitDateStr() {
		if(visitDate == null){
			return null;
		}
		return DateUtil.getFormatDate(visitDate, "yyyy-MM-dd");
	}

	public Long getOrderItemMetaId() {
		return orderItemMetaId;
	}

	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}
	//格式化PerformTime
	public String getPerformTimeStr() {
		if(performTime == null){
			return null;
		}
		return DateUtil.getFormatDate(performTime, "yyyy-MM-dd hh:mm:ss");
	}

	public String getSettlementStatus() {
		return settlementStatus;
	}

	public String getSettlementStatusStr() {
		if("UNSETTLEMENTED".equals(settlementStatus)){
			return "未结算";
		}else if("SETTLEMENTING".equals(settlementStatus)){
			return "结算中";
		}else if("SETTLEMENTED".equals(settlementStatus)){
			return "已结算";
		}
		return settlementStatus;
	}
	
	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSettlementCompanyName() {
		return settlementCompanyName;
	}

	public void setSettlementCompanyName(String settlementCompanyName) {
		this.settlementCompanyName = settlementCompanyName;
	}
	

}
