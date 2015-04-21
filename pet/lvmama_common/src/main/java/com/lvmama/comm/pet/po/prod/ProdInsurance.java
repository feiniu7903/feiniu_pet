package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;

import com.lvmama.comm.pet.vo.product.RelatedProduct;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class ProdInsurance implements RelatedProduct, Serializable {
	private static final long serialVersionUID = -991699361408950230L;
	
	private Long insuranceId;
	private Long productId;
	private String productName;
	private Long marketPrice;
	private Long sellPrice;
	private Long settlementPrice;
	private Integer days;
	private Long supplierId;
	private String productIdSupplier;
	private String valid = "Y";
	private String productTypeSupplier;
	private String description;
	private Long managerId = 1708L; //默认为崔磊
	private String filialeName = "SH_FILIALE"; //默认为上海总部

	public String getZhSupplierName() {
		if (21L == supplierId) {
			return "平安保险";
		}
		if (6054L == supplierId) {
			return "大众保险";
		}
		return "";
	}
	
	@Override
	public String getZhProductType() {
		return Constant.PRODUCT_TYPE.OTHER.getCnName();
	}
	
	@Override
	public String getZhSubProductType() {
		return Constant.SUB_PRODUCT_TYPE.INSURANCE.getCnName();
	}	
	
	public float getSellPriceYuan() {
		return PriceUtil.convertToYuan(this.sellPrice);
	}
	
	public void setSellPriceYuan(float sellPriceYuan) {
		this.sellPrice = PriceUtil.convertToFen(sellPriceYuan);
	}
	
	public float getSettlementPriceYuan() {
		return PriceUtil.convertToYuan(this.settlementPrice);
	}
	
	public void setSettlementPriceYuan(float settlementPriceYuan) {
		this.settlementPrice = PriceUtil.convertToFen(settlementPriceYuan);
	}
	
	public float getMarketPriceYuan() {
		return PriceUtil.convertToYuan(this.marketPrice);
	}
	
	public void setMarketPriceYuan(float marketPriceYuan) {
		this.marketPrice = PriceUtil.convertToFen(marketPriceYuan);
	}
	
	
	public Long getInsuranceId() {
		return insuranceId;
	}
	public void setInsuranceId(Long insuranceId) {
		this.insuranceId = insuranceId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}
	public Long getSettlementPrice() {
		return settlementPrice;
	}
	public void setSettlementPrice(Long settlementPrice) {
		this.settlementPrice = settlementPrice;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public String getProductIdSupplier() {
		return productIdSupplier;
	}
	public void setProductIdSupplier(String productIdSupplier) {
		this.productIdSupplier = productIdSupplier;
	}
	public String getProductTypeSupplier() {
		return productTypeSupplier;
	}
	public void setProductTypeSupplier(String productTypeSupplier) {
		this.productTypeSupplier = productTypeSupplier;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getManagerId() {
		return managerId;
	}
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	public String getFilialeName() {
		return filialeName;
	}
	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}

	public Long getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}
}
