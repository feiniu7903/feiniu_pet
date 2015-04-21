package com.lvmama.tnt.order.vo;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.tnt.comm.util.PriceUtil;
import com.lvmama.tnt.comm.util.TntUtil;

public class TntOrderItemProd implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 7456723495866897877L;

	private Long orderItemProdId;
	private Long orderId;
	private Long productId;
	private Date visitTime;

	private String productName;

	private String productType;

	private Long marketPrice;

	private Long price;

	/**
	 * 分销价
	 */
	private Long distPrice;

	private Long quantity;

	private Long prodBranchId;

	public Long getOrderItemProdId() {
		return orderItemProdId;
	}

	public void setOrderItemProdId(Long orderItemProdId) {
		this.orderItemProdId = orderItemProdId;
	}

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

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public String getProductName() {
		return productName;
	}

	public java.lang.String getShorProductName() {
		if(this.productName!=null && this.productName.length()>10){
			return this.productName.substring(0, 10)+"...";
		}
		return this.productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Long getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getProdBranchId() {
		return prodBranchId;
	}

	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}

	public Long getDistPrice() {
		return distPrice;
	}

	public void setDistPrice(Long distPrice) {
		this.distPrice = distPrice;
	}

	public int getDistPriceYuan() {
		if (this.getDistPrice() != null) {
			return PriceUtil.convertToYuanInt(this.distPrice);
		}
		return 0;
	}

	public int getMarketPriceYuan() {
		if (this.getMarketPrice() != null) {
			return PriceUtil.convertToYuanInt(this.getMarketPrice());
		}
		return 0;
	}

	public int getPriceYuan() {
		if (this.getPrice() != null) {
			return PriceUtil.convertToYuanInt(this.getPrice());
		}
		return 0;
	}

	public int getTotalPriceYuan() {
		if(this.getDistPrice()!=null && this.getQuantity()!=null){
			return PriceUtil.convertToYuanInt(this.getDistPrice()*this.getQuantity());
		}
		return 0;
	}

	public String getCnVisitTime() {
		if (this.visitTime != null) {
			return TntUtil.formatDate(getVisitTime());
		}
		return "";
	}
}