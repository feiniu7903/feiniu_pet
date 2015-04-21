package com.lvmama.comm.bee.po.market;

import java.io.Serializable;
import java.util.Date;

public class TaobaoProductDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long tbProductDetailsId;
	private Long tbProductInterfaceId;
	private TaobaoProduct taobaoProduct;
	private ApplyCity applyCity;
	private Long applyCityId;
	private Long platFormId; //平台id
	private String isStorage; //是否入仓
	private byte[] mainPic;
	private String shortName;
	private String longName;
	private float originalPrice; //宝贝原价
	private float activityPrice; //团购价格
	private float discount; //折扣
	private int itemCount;//团购数量
	private int limitNum; //限购数量
	private String locCity; //所在城市
	private String tripFormType; //旅游类型
	private String startArea; //出发地
	private String destinyArea; //目的地
	private String optinion;
	private String multiCityReason;//申请多城原因
	private String scheduleAdvice; //排期建议原因
	private String tgHistory; //历史团购网址
	private String strength; //商家亮点优势
	private Long activityEnterId; //商家活动报名id
	private Date createDate;
	private Date modifyDate;
	private Long tbJhsReturnId;
	
	public Long getTbProductDetailsId() {
		return tbProductDetailsId;
	}
	public void setTbProductDetailsId(Long tbProductDetailsId) {
		this.tbProductDetailsId = tbProductDetailsId;
	}
	public Long getTbProductInterfaceId() {
		return tbProductInterfaceId;
	}
	public void setTbProductInterfaceId(Long tbProductInterfaceId) {
		this.tbProductInterfaceId = tbProductInterfaceId;
	}
	public TaobaoProduct getTaobaoProduct() {
		return taobaoProduct;
	}
	public void setTaobaoProduct(TaobaoProduct taobaoProduct) {
		this.taobaoProduct = taobaoProduct;
	}
	public Long getApplyCityId() {
		return applyCityId;
	}
	public void setApplyCityId(Long applyCityId) {
		this.applyCityId = applyCityId;
	}
	public Long getPlatFormId() {
		return platFormId;
	}
	public void setPlatFormId(Long platFormId) {
		this.platFormId = platFormId;
	}
	public String getIsStorage() {
		return isStorage;
	}
	public void setIsStorage(String isStorage) {
		this.isStorage = isStorage;
	}
	public byte[] getMainPic() {
		return mainPic;
	}
	public void setMainPic(byte[] mainPic) {
		this.mainPic = mainPic;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getLongName() {
		return longName;
	}
	public void setLongName(String longName) {
		this.longName = longName;
	}
	public float getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(float originalPrice) {
		this.originalPrice = originalPrice;
	}
	public float getActivityPrice() {
		return activityPrice;
	}
	public void setActivityPrice(float activityPrice) {
		this.activityPrice = activityPrice;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	public int getLimitNum() {
		return limitNum;
	}
	public void setLimitNum(int limitNum) {
		this.limitNum = limitNum;
	}
	public String getLocCity() {
		return locCity;
	}
	public void setLocCity(String locCity) {
		this.locCity = locCity;
	}
	public String getTripFormType() {
		return tripFormType;
	}
	public void setTripFormType(String tripFormType) {
		this.tripFormType = tripFormType;
	}
	public String getStartArea() {
		return startArea;
	}
	public void setStartArea(String startArea) {
		this.startArea = startArea;
	}
	public String getDestinyArea() {
		return destinyArea;
	}
	public void setDestinyArea(String destinyArea) {
		this.destinyArea = destinyArea;
	}
	public String getOptinion() {
		return optinion;
	}
	public void setOptinion(String optinion) {
		this.optinion = optinion;
	}
	public String getMultiCityReason() {
		return multiCityReason;
	}
	public void setMultiCityReason(String multiCityReason) {
		this.multiCityReason = multiCityReason;
	}
	public String getScheduleAdvice() {
		return scheduleAdvice;
	}
	public void setScheduleAdvice(String scheduleAdvice) {
		this.scheduleAdvice = scheduleAdvice;
	}
	public String getTgHistory() {
		return tgHistory;
	}
	public void setTgHistory(String tgHistory) {
		this.tgHistory = tgHistory;
	}
	public String getStrength() {
		return strength;
	}
	public void setStrength(String strength) {
		this.strength = strength;
	}
	public Long getActivityEnterId() {
		return activityEnterId;
	}
	public void setActivityEnterId(Long activityEnterId) {
		this.activityEnterId = activityEnterId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public ApplyCity getApplyCity() {
		return applyCity;
	}
	public void setApplyCity(ApplyCity applyCity) {
		this.applyCity = applyCity;
	}
	public Long getTbJhsReturnId() {
		return tbJhsReturnId;
	}
	public void setTbJhsReturnId(Long tbJhsReturnId) {
		this.tbJhsReturnId = tbJhsReturnId;
	}
}
