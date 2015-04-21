package com.lvmama.comm.pet.po.search;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import com.lvmama.comm.utils.PriceUtil;

/**
 * 门票,线路,酒店类别产品相关搜索表
 * @author huangzhi
 *
 */
public class ProdBranchSearchInfo implements Serializable {

	private static final long serialVersionUID = 7521403858145539662L;
	
	private Long prodBranchId;
	private String productName;
	private Long productSeq;
	private String payToLvmama;
	private String payToSupplier;
	private Date createTime;

	private String branchType;

	private Date updateTime;

	private String branchName;

	private String bedType;

	private String description;

	private String broadband;

	private String cashRefund;

	private String breakfast;

	private Long marketPrice;

	private Long sellPrice;

	private String icon;

	private String additional;

	private String onLine;

	private String valid;

	private String productAllPlaceIds;

	private String productAlltoPlaceContent;

	private Long productId;

	private String visible;

	private String defaultBranch;

	private String channel;

	private String subProductType;

	private Long adultQuantity;

	private Long childQuantity;

	private Long minimum;

	private Long maximum;

	private String priceUnit;

	private String online;

	private String extraBedAble;

	private String branchSerialNumber;

	private String orderToknown;
	
	private Date validBeginTime;
	
	private Date validEndTime;
	
	private Date todayOrderAbleTime;
	
	private String invalidDateMemo;

	private String shareWeiXin;
	
	private Date todayOrderLastTime;//新今日票最晚可定时间
	
	public Long getProdBranchId() {
		return prodBranchId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getProductSeq() {
		return productSeq;
	}

	public void setProductSeq(Long productSeq) {
		this.productSeq = productSeq;
	}

	public String getPayToLvmama() {
		return payToLvmama;
	}

	public void setPayToLvmama(String payToLvmama) {
		this.payToLvmama = payToLvmama;
	}

	public String getPayToSupplier() {
		return payToSupplier;
	}

	public void setPayToSupplier(String payToSupplier) {
		this.payToSupplier = payToSupplier;
	}

	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public String getDescription() {
		return description;
	}
	/**
	 * 把\n替换成</br>
	 * @return
	 */
	public String getDescriptionWithTag(){
		if(StringUtils.isNotEmpty(description)){
			return description.replace("\n", "</br>");
		}else {
			return description;
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBroadband() {
		return broadband;
	}

	public void setBroadband(String broadband) {
		this.broadband = broadband;
	}
	
	public String getBroadbandStr() {
		String broadbandStr = "无";
		if("free".equals(broadband)) {
			broadbandStr = "免费";
		} else if("fee".equals(broadband)) {
			broadbandStr = "收费";
		}
		return broadbandStr;
	}

	public String getCashRefund() {
		return cashRefund;
	}

	public void setCashRefund(String cashRefund) {
		this.cashRefund = cashRefund;
	}

	public String getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}

	public Long getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Long getSellPrice() {
		return this.sellPrice;
	}
	public Object getSellPriceYuan() {
		return sellPrice == null ? 0 : PriceUtil.convertToYuan(sellPrice);
	}

	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}
	
	public Integer getMarketPriceInteger() {
		Integer price = 0;
		if (this.marketPrice != null) {
			price = Integer.valueOf(marketPrice.toString());
		}
		return price / 100;
	}

	public Integer getSellPriceInteger() {
		Integer price = 0;
		if (this.sellPrice != null) {
			price = Integer.valueOf(sellPrice.toString());
		}
		return price / 100;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getAdditional() {
		return additional;
	}

	public void setAdditional(String additional) {
		this.additional = additional;
	}

	public String getOnLine() {
		return onLine;
	}

	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getProductAllPlaceIds() {
		return productAllPlaceIds;
	}

	public void setProductAllPlaceIds(String productAllPlaceIds) {
		this.productAllPlaceIds = productAllPlaceIds;
	}

	public String getProductAlltoPlaceContent() {
		return productAlltoPlaceContent;
	}

	public void setProductAlltoPlaceContent(String productAlltoPlaceContent) {
		this.productAlltoPlaceContent = productAlltoPlaceContent;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getDefaultBranch() {
		return defaultBranch;
	}

	public void setDefaultBranch(String defaultBranch) {
		this.defaultBranch = defaultBranch;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getBranchType() {
		return branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}

	public Long getAdultQuantity() {
		return adultQuantity;
	}

	public void setAdultQuantity(Long adultQuantity) {
		this.adultQuantity = adultQuantity;
	}

	public Long getChildQuantity() {
		return childQuantity;
	}

	public void setChildQuantity(Long childQuantity) {
		this.childQuantity = childQuantity;
	}

	public Long getMinimum() {
		return minimum;
	}

	public void setMinimum(Long minimum) {
		this.minimum = minimum;
	}

	public Long getMaximum() {
		return maximum;
	}

	public void setMaximum(Long maximum) {
		this.maximum = maximum;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public String getExtraBedAble() {
		return extraBedAble;
	}

	public void setExtraBedAble(String extraBedAble) {
		this.extraBedAble = extraBedAble;
	}

	public String getBranchSerialNumber() {
		return branchSerialNumber;
	}

	public void setBranchSerialNumber(String branchSerialNumber) {
		this.branchSerialNumber = branchSerialNumber;
	}

	public String getOrderToknown() {
		return orderToknown;
	}

	public void setOrderToknown(String orderToknown) {
		this.orderToknown = orderToknown;
	}

	public Date getValidBeginTime() {
		return validBeginTime;
	}

	public void setValidBeginTime(Date validBeginTime) {
		this.validBeginTime = validBeginTime;
	}

	public Date getValidEndTime() {
		return validEndTime;
	}

	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}

	public Date getTodayOrderAbleTime() {
		return todayOrderAbleTime;
	}

	public void setTodayOrderAbleTime(Date todayOrderAbleTime) {
		this.todayOrderAbleTime = todayOrderAbleTime;
	}
	
	public boolean todayOrderAble(){
		if(this.getTodayOrderAbleTime()==null){
			return false;
		}
		
		if(!DateUtils.isSameDay(new Date(), todayOrderAbleTime)){
			return false;
		}
		return true;
	}
	
	public boolean canOrderTodayCurrentTime(){
		
		if(this.getTodayOrderAbleTime()==null){
			return false;
		}
		return new Date().before(todayOrderAbleTime);
	}

	public String getInvalidDateMemo() {
		return invalidDateMemo;
	}

	public void setInvalidDateMemo(String invalidDateMemo) {
		this.invalidDateMemo = invalidDateMemo;
	}

	public String getShareWeiXin() {
		return shareWeiXin;
	}

	public void setShareWeiXin(String shareWeiXin) {
		this.shareWeiXin = shareWeiXin;
	}

	public Date getTodayOrderLastTime() {
		return todayOrderLastTime;
	}

	public void setTodayOrderLastTime(Date todayOrderLastTime) {
		this.todayOrderLastTime = todayOrderLastTime;
	}
		
}
