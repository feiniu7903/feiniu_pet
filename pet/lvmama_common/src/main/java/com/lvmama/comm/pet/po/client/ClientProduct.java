package com.lvmama.comm.pet.po.client;

import java.io.Serializable;
import java.util.List;

import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class ClientProduct   implements Serializable{
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long productId;

	private Long adultQuantity;
	private Long childQuantity;
	private String saleNumType;
	
	private String isAddtional="false";
	private String productName;
	//hotel 
	private Long hotelId;
	
	private String hotelRoom; // 房型

	private String nonSmokingRoom = "true"; // 是否是无烟房

	private String floor;// 楼层

	private String roomArea; // 房间面积

	private String roomGround;// 地面

	private String bedType;// 床型

	private String broadband;// 宽带

	private String services;// 服务设施
	
	private String description;

	private String icon;// 小icon;
	  
	private String couponAble = "true";
	//酒店套装包含天数
	private String days;
	
	private Long marketPrice;

    private Long sellPrice;
    
    private String shortName;

	private String announcement;
	
	private String costcontain;
	
	private String fauture;
	
	private String importmentclew;
	
	private String managerRecommend;
	
	private String orderToKnow;
	
	private String serviceGuarantee;
	
	private String shoppingExplain;
	
	private String refundSexPlanation;
	
	private String actionToKnow;
	
	private String recommendProject;
	
	private List<ClientViewJouney> viewJouneyList;
	
	private String toDest;
	
	private String smallImage;
	
	private String recommendReason;
	
	private String subProductType;
	
	private Long prodBranchId;
	
	private String branchId;
	
	private Long minimum;
	
	private Long maximum;
	
	private String payToSupplier;
	
	private String payToLvmama;
	
	private String productType;
	
	private boolean isAdditional;
	
	private boolean hotelSuit;
	
	public boolean isHotelSuit() {
		return hotelSuit;
	}
	public void setHotelSuit(boolean hotelSuit) {
		this.hotelSuit = hotelSuit;
	}
	private List<String> imageList;
	
	private boolean canOrderToday;
	
	private boolean canOrderTodayCurrentTime;
	
	private String todayOrderTips;

	public String getTodayOrderTips() {
		//todayOrderTips ="①至少提前1.5小时  ② 最晚18:50可订";
		return todayOrderTips;
	}
	public void setTodayOrderTips(String todayOrderTips) {
		this.todayOrderTips = todayOrderTips;
	}
	public Long getProdBranchId() {
		return prodBranchId;
	}
	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
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
	public String getIsAddtional() {
		return isAddtional;
	}
	public void setIsAddtional(String isAddtional) {
		this.isAddtional = isAddtional;
	}
	public String getSaleNumType() {
		return saleNumType;
	}
	public void setSaleNumType(String saleNumType) {
		this.saleNumType = saleNumType;
	}
	public String getHotelRoom() {
		return hotelRoom;
	}
	public void setHotelRoom(String hotelRoom) {
		this.hotelRoom = hotelRoom;
	}
	public String getNonSmokingRoom() {
		return nonSmokingRoom;
	}
	public void setNonSmokingRoom(String nonSmokingRoom) {
		this.nonSmokingRoom = nonSmokingRoom;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getRoomArea() {
		return roomArea;
	}
	public void setRoomArea(String roomArea) {
		this.roomArea = roomArea;
	}
	public String getRoomGround() {
		return roomGround;
	}
	public void setRoomGround(String roomGround) {
		this.roomGround = roomGround;
	}
	public String getBedType() {
		return bedType;
	}
	public void setBedType(String bedType) {
		this.bedType = bedType;
	}
	public String getBroadband() {
		return broadband;
	}
	public void setBroadband(String broadband) {
		this.broadband = broadband;
	}
	
	public Long getSellPrice(){
		return PriceUtil.getLongPriceYuan(this.sellPrice);
	}
	
	public Long getMarketPrice() {
		return PriceUtil.getLongPriceYuan(this.marketPrice);
	}
	
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public Long getHotelId() {
		return hotelId;
	}
	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getCouponAble() {
		return couponAble;
	}
	public void setCouponAble(String couponAble) {
		this.couponAble = couponAble;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	
	public String getShareContent () {
		String _productName = StringUtil.subStringStr(getProductName(), 60);
		String content = String.format("我刚在 @驴妈妈旅游网 发现一个不错的产品，“%s”", _productName);
		return content;
	}
	
	public String getShareUrl (){
		return Constant.WWW_HOST+"/product/"+getProductId();
	}
	public String getAnnouncement() {
		return announcement;
	}
	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}
	public String getCostcontain() {
		return costcontain;
	}
	public void setCostcontain(String costcontain) {
		this.costcontain = costcontain;
	}
	public String getFauture() {
		return fauture;
	}
	public void setFauture(String fauture) {
		this.fauture = fauture;
	}
	public String getImportmentclew() {
		return importmentclew;
	}
	public void setImportmentclew(String importmentclew) {
		this.importmentclew = importmentclew;
	}
	public String getManagerRecommend() {
		return managerRecommend;
	}
	public void setManagerRecommend(String managerRecommend) {
		this.managerRecommend = managerRecommend;
	}
	public String getOrderToKnow() {
		return orderToKnow;
	}
	public void setOrderToKnow(String orderToKnow) {
		this.orderToKnow = orderToKnow;
	}
	public String getServiceGuarantee() {
		return serviceGuarantee;
	}
	public void setServiceGuarantee(String serviceGuarantee) {
		this.serviceGuarantee = serviceGuarantee;
	}
	public String getShoppingExplain() {
		return shoppingExplain;
	}
	public void setShoppingExplain(String shoppingExplain) {
		this.shoppingExplain = shoppingExplain;
	}
	public String getRefundSexPlanation() {
		return refundSexPlanation;
	}
	public void setRefundSexPlanation(String refundSexPlanation) {
		this.refundSexPlanation = refundSexPlanation;
	}
	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}
	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public List<ClientViewJouney> getViewJouneyList() {
		return viewJouneyList;
	}
	public void setViewJouneyList(List<ClientViewJouney> viewJouneyList) {
		this.viewJouneyList = viewJouneyList;
	}
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getToDest() {
		return toDest;
	}
	public void setToDest(String toDest) {
		this.toDest = toDest;
	}
	public String getSmallImage() {
		return smallImage;
	}
	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}
	public String getRecommendReason() {
		return recommendReason;
	}
	public void setRecommendReason(String recommendReason) {
		this.recommendReason = recommendReason;
	}
	public String getSubProductType() {
		return subProductType;
	}
	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
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
	public String getPayToSupplier() {
		return payToSupplier;
	}
	public void setPayToSupplier(String payToSupplier) {
		this.payToSupplier = payToSupplier;
	}
	public String getPayToLvmama() {
		return payToLvmama;
	}
	public void setPayToLvmama(String payToLvmama) {
		this.payToLvmama = payToLvmama;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public boolean isAdditional() {
		return isAdditional;
	}
	public void setAdditional(boolean isAdditional) {
		this.isAdditional = isAdditional;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public boolean isSingleRoom(){ 
		return Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(subProductType); 
	}
	
	public boolean isRoute(){
		return Constant.PRODUCT_TYPE.ROUTE.name().equals(productType);
	}
	
	public boolean isTicket(){
		return Constant.PRODUCT_TYPE.TICKET.name().equals(productType);
	}
	public String getActionToKnow() {
		return actionToKnow;
	}
	public void setActionToKnow(String actionToKnow) {
		this.actionToKnow = actionToKnow;
	}
	public String getRecommendProject() {
		return recommendProject;
	}
	public void setRecommendProject(String recommendProject) {
		this.recommendProject = recommendProject;
	}
	public List<String> getImageList() {
		return imageList;
	}
	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}
	public boolean isCanOrderToday() {
		return canOrderToday;
	}
	public void setCanOrderToday(boolean canOrderToday) {
		this.canOrderToday = canOrderToday;
	}
	public boolean isCanOrderTodayCurrentTime() {
		return canOrderTodayCurrentTime;
	}
	public void setCanOrderTodayCurrentTime(boolean canOrderTodayCurrentTime) {
		this.canOrderTodayCurrentTime = canOrderTodayCurrentTime;
	}
	
}	
