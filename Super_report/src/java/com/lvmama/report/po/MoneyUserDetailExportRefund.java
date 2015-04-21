package com.lvmama.report.po;

import java.io.Serializable;
import java.util.Date;

public class MoneyUserDetailExportRefund implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 团号
	 */
	private String travelGroupCode;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 游玩时间
	 */
	private Date visitTime;
	/**
	 * 下单时间
	 */
	private Date orderCreateTime;
	/**
	 * 退款金额
	 */
	private Integer amount;
	/**
	 * 退款时间
	 */
	private Date refundCreateTime;
	/**
	 * 供应商ID
	 */
	private Integer supplierId;
	/**
	 * 供应商名称
	 */
	private String supplierName;
	/**
	 * 订购数量
	 */
	private Integer quantity;
	/**
	 * 销售产品ID
	 */
	private Long productId;
	/**
	 * 销售产品名称
	 */
	private String productName;

	/**
	 * 采购产品ID
	 */
	private Integer metaProductId;
	/**
	 * 采购产品名称
	 */
	private String metaProductName;

	/**
	 * 打包数量
	 */
	private Integer productQuantity;
	/**
	 * 采购产品结算单价
	 */
	private Integer settlementPrice;
	/**
	 * 销售产品所属公司
	 */
	private String filialeName;
	
	private String bonusRefund;

	public String getBonusRefund() {
		return bonusRefund;
	}

	public void setBonusRefund(String bonusRefund) {
		this.bonusRefund = bonusRefund;
	}
	
	
	public String getRefundTypeName(){
		if("Y".equals(bonusRefund)){
			return "奖金";
		}else if("N".equals(bonusRefund)){
			return "现金";
		}
		return null;
	}
	

	public String getTravelGroupCode() {
		return travelGroupCode;
	}

	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public Integer getAmount() {
		return amount;
	}

	public double getAmountYuan() {
		return amount/100d;
	}

	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getRefundCreateTime() {
		return refundCreateTime;
	}

	public void setRefundCreateTime(Date refundCreateTime) {
		this.refundCreateTime = refundCreateTime;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getFilialeName() {
		return filialeName;
	}

	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}

	public Integer getMetaProductId() {
		return metaProductId;
	}

	public void setMetaProductId(Integer metaProductId) {
		this.metaProductId = metaProductId;
	}

	public String getMetaProductName() {
		return metaProductName;
	}

	public void setMetaProductName(String metaProductName) {
		this.metaProductName = metaProductName;
	}

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Integer getSettlementPrice() {
		return settlementPrice;
	}
	public double getSettlementPriceYuan() {
		return settlementPrice/100d;
	}
	public void setSettlementPrice(Integer settlementPrice) {
		this.settlementPrice = settlementPrice;
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
	
}