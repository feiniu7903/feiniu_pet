package com.lvmama.comm.bee.vo.report;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class EbkProductVisitorReportVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8567607205360137078L;

	private String supplierName;
	
	private String metaProductName;
	private String metaProductId;
	private String prodProductName;
	private String prodProductId;
	
	private Long price;  
	//结算价.
	private Long settlementPrice;  
	//结算总价
	private Long totalSettlementPrice;  	
	
	private String placeName;
	private String travelTime;
	private Long days;
	private Date onlineTime;
	private Date offlineTime;
	
	private String journeyHotel;
	private String journeyFeature;
	private String journeyRecommend;
	private String productPlace;

	private String manager;
	private Date visitTime;
	private Long payedAdultQuantity;
	private Long payedChildQuantity;
	private Long unpayAdultQuantity;
	private Long unpayChildQuantity;
	
	private Long orderId;
	private Long quantity;
	private String payStatus;
	
	private String contact;
	private String mobile;
	private String memo;
	private String userMemo;
	private Date payTime;
	
	private String filialeName;
	
	public String getZhFilialeName(){
		return Constant.FILIALE_NAME.getCnName(filialeName);
	}
	public String getZhPayStatus(){
		return Constant.PAYMENT_STATUS.getCnName(payStatus);
	}
	public Float getTotalSettlementPriceYuan(){
		if(totalSettlementPrice == null) {
			return null;
		}
		return PriceUtil.convertToYuan(totalSettlementPrice);
	}
	public Float getSettlementPriceYuan(){
		if(settlementPrice == null) {
			return null;
		}
		return PriceUtil.convertToYuan(settlementPrice);
	}
	public Float getPriceYuan(){
		if(price == null) {
			return null;
		}
		return PriceUtil.convertToYuan(price);
	}
	public String getZhOnlineTime(){
		if(this.onlineTime!=null){
			return DateFormatUtils.format(this.onlineTime, "yyyy-MM-dd");
		}
		return "";
	}
	public String getZhOfflineTime(){
		if(this.offlineTime!=null){
			return DateFormatUtils.format(this.offlineTime, "yyyy-MM-dd");
		}
		return "";
	}
	public String getZhVisitTime(){
		if(this.visitTime!=null){
			return DateFormatUtils.format(this.visitTime, "yyyy-MM-dd");
		}
		return "";
	}
	public String getZhPayTime(){
		if(this.payTime!=null){
			return DateFormatUtils.format(this.payTime, "yyyy-MM-dd HH:mm");
		}
		return "";
	}
	
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getMetaProductName() {
		return metaProductName;
	}

	public void setMetaProductName(String metaProductName) {
		this.metaProductName = metaProductName;
	}

	public String getMetaProductId() {
		return metaProductId;
	}

	public void setMetaProductId(String metaProductId) {
		this.metaProductId = metaProductId;
	}

	public String getProdProductName() {
		return prodProductName;
	}

	public void setProdProductName(String prodProductName) {
		this.prodProductName = prodProductName;
	}

	public String getProdProductId() {
		return prodProductId;
	}

	public void setProdProductId(String prodProductId) {
		this.prodProductId = prodProductId;
	}

	public Long getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(Long settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public Long getTotalSettlementPrice() {
		return totalSettlementPrice;
	}

	public void setTotalSettlementPrice(Long totalSettlementPrice) {
		this.totalSettlementPrice = totalSettlementPrice;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(String travelTime) {
		this.travelTime = travelTime;
	}

	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
		this.days = days;
	}

	public Date getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Date getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(Date offlineTime) {
		this.offlineTime = offlineTime;
	}

	public String getJourneyHotel() {
		return journeyHotel;
	}

	public void setJourneyHotel(String journeyHotel) {
		this.journeyHotel = journeyHotel;
	}

	public String getJourneyFeature() {
		return journeyFeature;
	}

	public void setJourneyFeature(String journeyFeature) {
		this.journeyFeature = journeyFeature;
	}

	public String getJourneyRecommend() {
		return journeyRecommend;
	}

	public void setJourneyRecommend(String journeyRecommend) {
		this.journeyRecommend = journeyRecommend;
	}

	public String getProductPlace() {
		return productPlace;
	}

	public void setProductPlace(String productPlace) {
		this.productPlace = productPlace;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public Long getPayedAdultQuantity() {
		return payedAdultQuantity;
	}

	public void setPayedAdultQuantity(Long payedAdultQuantity) {
		this.payedAdultQuantity = payedAdultQuantity;
	}

	public Long getPayedChildQuantity() {
		return payedChildQuantity;
	}

	public void setPayedChildQuantity(Long payedChildQuantity) {
		this.payedChildQuantity = payedChildQuantity;
	}

	public Long getUnpayAdultQuantity() {
		return unpayAdultQuantity;
	}

	public void setUnpayAdultQuantity(Long unpayAdultQuantity) {
		this.unpayAdultQuantity = unpayAdultQuantity;
	}

	public Long getUnpayChildQuantity() {
		return unpayChildQuantity;
	}

	public void setUnpayChildQuantity(Long unpayChildQuantity) {
		this.unpayChildQuantity = unpayChildQuantity;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getUserMemo() {
		return userMemo;
	}

	public void setUserMemo(String userMemo) {
		this.userMemo = userMemo;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getFilialeName() {
		return filialeName;
	}

	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}
}
