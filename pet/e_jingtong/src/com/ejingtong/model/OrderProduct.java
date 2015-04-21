package com.ejingtong.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="order_product")
public class OrderProduct implements Serializable {
	
	public OrderProduct(){
		
	}

	private static final long serialVersionUID = -3240689143808476477L;
	
	private String Additional; // "false",
    private int AheadHour; // -20,
    private int AmountYuan; // 2,
    private String BranchType; // "ADULT",
    private String DateRange; // "2013-03-06",
    private int Days; // 1,
    private String HotelQuantity; // "2",
    private String IsDefault; // "true",
    private int LastCancelHour; // -20,
    private int MarketAmountYuan; // 2,
    private int MarketPrice; // 100,
    private int MarketPriceYuan; // 1,
    
    @DatabaseField(columnName="order_id")
    private long OrderId; // 1310905,
    private int OrderItemProdId; // 1232637,
    private int Price; // 100,
    private int PriceYuan; // 1,
    private long ProdBranchId; // 88000,
    private long ProductId; // 66057,
    private String ProductName; // "wxwE景通集合产品(成人票)",
    private String ProductType; // "TICKET",
    
    @DatabaseField(columnName="quantity")
    private int Quantity; // 2,
    private String SendSms; // "true",
    private String ShortName; // "成人票",
    private String SubProductType; // "SINGLE",
    private String ZhAdditional; // "无",
    private String ZhProductType; // "门票",
    private String ZhVisitTime; // "2013-03-06"
    
	public String getAdditional() {
		return Additional;
	}
	public void setAdditional(String additional) {
		Additional = additional;
	}
	public int getAheadHour() {
		return AheadHour;
	}
	public void setAheadHour(int aheadHour) {
		AheadHour = aheadHour;
	}
	public int getAmountYuan() {
		return AmountYuan;
	}
	public void setAmountYuan(int amountYuan) {
		AmountYuan = amountYuan;
	}
	public String getBranchType() {
		return BranchType;
	}
	public void setBranchType(String branchType) {
		BranchType = branchType;
	}
	public String getDateRange() {
		return DateRange;
	}
	public void setDateRange(String dateRange) {
		DateRange = dateRange;
	}
	public int getDays() {
		return Days;
	}
	public void setDays(int days) {
		Days = days;
	}
	public String getHotelQuantity() {
		return HotelQuantity;
	}
	public void setHotelQuantity(String hotelQuantity) {
		HotelQuantity = hotelQuantity;
	}
	public String getIsDefault() {
		return IsDefault;
	}
	public void setIsDefault(String isDefault) {
		IsDefault = isDefault;
	}
	public int getLastCancelHour() {
		return LastCancelHour;
	}
	public void setLastCancelHour(int lastCancelHour) {
		LastCancelHour = lastCancelHour;
	}
	public int getMarketAmountYuan() {
		return MarketAmountYuan;
	}
	public void setMarketAmountYuan(int marketAmountYuan) {
		MarketAmountYuan = marketAmountYuan;
	}
	public int getMarketPrice() {
		return MarketPrice;
	}
	public void setMarketPrice(int marketPrice) {
		MarketPrice = marketPrice;
	}
	public int getMarketPriceYuan() {
		return MarketPriceYuan;
	}
	public void setMarketPriceYuan(int marketPriceYuan) {
		MarketPriceYuan = marketPriceYuan;
	}
	public long getOrderId() {
		return OrderId;
	}
	public void setOrderId(long orderId) {
		OrderId = orderId;
	}
	public int getOrderItemProdId() {
		return OrderItemProdId;
	}
	public void setOrderItemProdId(int orderItemProdId) {
		OrderItemProdId = orderItemProdId;
	}
	public int getPrice() {
		return Price;
	}
	public void setPrice(int price) {
		Price = price;
	}
	public int getPriceYuan() {
		return PriceYuan;
	}
	public void setPriceYuan(int priceYuan) {
		PriceYuan = priceYuan;
	}
	public long getProdBranchId() {
		return ProdBranchId;
	}
	public void setProdBranchId(long prodBranchId) {
		ProdBranchId = prodBranchId;
	}
	public long getProductId() {
		return ProductId;
	}
	public void setProductId(long productId) {
		ProductId = productId;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getProductType() {
		return ProductType;
	}
	public void setProductType(String productType) {
		ProductType = productType;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public String getSendSms() {
		return SendSms;
	}
	public void setSendSms(String sendSms) {
		SendSms = sendSms;
	}
	public String getShortName() {
		return ShortName;
	}
	public void setShortName(String shortName) {
		ShortName = shortName;
	}
	public String getSubProductType() {
		return SubProductType;
	}
	public void setSubProductType(String subProductType) {
		SubProductType = subProductType;
	}
	public String getZhAdditional() {
		return ZhAdditional;
	}
	public void setZhAdditional(String zhAdditional) {
		ZhAdditional = zhAdditional;
	}
	public String getZhProductType() {
		return ZhProductType;
	}
	public void setZhProductType(String zhProductType) {
		ZhProductType = zhProductType;
	}
	public String getZhVisitTime() {
		return ZhVisitTime;
	}
	public void setZhVisitTime(String zhVisitTime) {
		ZhVisitTime = zhVisitTime;
	}
    
}
