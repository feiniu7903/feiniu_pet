package com.lvmama.shholiday.vo.product;

import java.util.Date;
/**
 * 供应商产品价格
 * @author gaoxin
 *
 */
public class ProductPrice {
	/**供应商产品Id */
	private String supplierProdId;
	/**供应商类别Id */
	private String supplierBranchId;
	/**价格日期 */
	private Date priceDate;
	/** 结算价 */
	private Long settlementPrice;
	/**供应商指定市场价 */
	private Long individualPrice;
	/**供应商指定销售价 */
	private Long salePrice;
	/** 日库存量 */
	private Long dayStock;
	/** 供应商团id*/
	private String teamNo;
	/** 供应商团号*/
	private String teamName;
	
	
	public String getSupplierProdId() {
		return supplierProdId;
	}
	public void setSupplierProdId(String supplierProdId) {
		this.supplierProdId = supplierProdId;
	}
	public String getSupplierBranchId() {
		return supplierBranchId;
	}
	public void setSupplierBranchId(String supplierBranchId) {
		this.supplierBranchId = supplierBranchId;
	}
	public Date getPriceDate() {
		return priceDate;
	}
	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}
	public Long getSettlementPrice() {
		return settlementPrice;
	}
	public void setSettlementPrice(Long settlementPrice) {
		this.settlementPrice = settlementPrice;
	}
	public Long getIndividualPrice() {
		return individualPrice;
	}
	public void setIndividualPrice(Long individualPrice) {
		this.individualPrice = individualPrice;
	}
	public Long getDayStock() {
		return dayStock;
	}
	public void setDayStock(Long dayStock) {
		this.dayStock = dayStock;
	}
	public String getTeamNo() {
		return teamNo;
	}
	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public ProductPrice(String supplierProdId, String supplierBranchId,
			Date priceDate, Long settlementPrice, Long individualPrice,
			Long dayStock, String teamNo, String teamName) {
		super();
		this.supplierProdId = supplierProdId;
		this.supplierBranchId = supplierBranchId;
		this.priceDate = priceDate;
		this.settlementPrice = settlementPrice;
		this.individualPrice = individualPrice;
		this.dayStock = dayStock;
		this.teamNo = teamNo;
		this.teamName = teamName;
	}
	public Long getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Long salePrice) {
		this.salePrice = salePrice;
	}
}
