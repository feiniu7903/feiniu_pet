package com.lvmama.clutter.model;

import java.io.Serializable;
import java.util.List;

import com.lvmama.comm.vo.comment.PlaceCmtScoreVO;

/**
 * 酒店.
 * 
 * @author qinzubo
 * 
 */
public class MobileProductHotel implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long productId; // 产品id
	private String productName; // 产品名称
	private Long marketPrice; // 市场价格
	private Double sellPrice; // 驴妈妈销售价格
	private Float avgScore;// 评价分数
	private int cmtNum;// 评论总数
	private boolean hasIn = false;// 是否收藏，默认false  
	private String managerRecommend;//产品经理推荐
	private String smallImage; // 小图片
	private Long branchId; // 分支id
	/**
	 *  产品类型：TRAFFIC("大交通"), TICKET("门票"), 
	 *  HOTEL("酒店"), HOTEL_FOREIGN("境外酒店"), 
	 *  ROUTE("线路"), OTHER("其它");
	 */
	private String productType; 
	private List<String> imageList; // 图片列表 
	private List<PlaceCmtScoreVO> placeCmtScoreList;
	private String announcement;
	
	// v3.1
	/**
	 * 优惠信息 
	 */
	private String preferentialInfo="";
	
	/**
	 * 优惠信息对应tag标签
	 * 奖金抵用1 早订早惠2 多订多惠3积分抵扣4
	 */
	private String preferentialTags="";
	
	/**
	 * 点评返现金额，单位分
	 */
	private Long maxCashRefund=0l;
	

	/**
	 * 是否支持多定多惠，早定早惠
	 */
	private boolean hasBusinessCoupon;
	
	/**
	 * 主题 
	 */
	private String subProductType;
	
	// hotel
	private String hotelId;
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
	private String days; // 酒店套装包含天数

	private String roomTypeId;// 房型id
	private Integer ratePlanId;// 产品id
	private int broadnetAccess;// 0表示无宽带，1 表示有宽带
	private int broadnetFee;// 0表示免费，1 表示收费
	private boolean status;// 是否可订 false--不可销售（可能是满房、部分日期满房、缺少价格）、true--可销售
	private String hasBreakfast;// 是否有早餐
	//private double cashBack;
	private String cashBack;
	private String cashBackDesc;

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

	public Long getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Float getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(Float avgScore) {
		this.avgScore = avgScore;
	}

	public int getCmtNum() {
		return cmtNum;
	}

	public void setCmtNum(int cmtNum) {
		this.cmtNum = cmtNum;
	}

	public boolean isHasIn() {
		return hasIn;
	}

	public void setHasIn(boolean hasIn) {
		this.hasIn = hasIn;
	}

	public String getManagerRecommend() {
		return managerRecommend;
	}

	public void setManagerRecommend(String managerRecommend) {
		this.managerRecommend = managerRecommend;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	public List<PlaceCmtScoreVO> getPlaceCmtScoreList() {
		return placeCmtScoreList;
	}

	public void setPlaceCmtScoreList(List<PlaceCmtScoreVO> placeCmtScoreList) {
		this.placeCmtScoreList = placeCmtScoreList;
	}

	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	public String getPreferentialInfo() {
		return preferentialInfo;
	}

	public void setPreferentialInfo(String preferentialInfo) {
		this.preferentialInfo = preferentialInfo;
	}

	public String getPreferentialTags() {
		return preferentialTags;
	}

	public void setPreferentialTags(String preferentialTags) {
		this.preferentialTags = preferentialTags;
	}

	public Long getMaxCashRefund() {
		return maxCashRefund;
	}

	public void setMaxCashRefund(Long maxCashRefund) {
		this.maxCashRefund = maxCashRefund;
	}

	public boolean isHasBusinessCoupon() {
		return hasBusinessCoupon;
	}

	public void setHasBusinessCoupon(boolean hasBusinessCoupon) {
		this.hasBusinessCoupon = hasBusinessCoupon;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
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

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
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

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public Integer getRatePlanId() {
		return ratePlanId;
	}

	public void setRatePlanId(Integer ratePlanId) {
		this.ratePlanId = ratePlanId;
	}

	public int getBroadnetAccess() {
		return broadnetAccess;
	}

	public void setBroadnetAccess(int broadnetAccess) {
		this.broadnetAccess = broadnetAccess;
	}

	public int getBroadnetFee() {
		return broadnetFee;
	}

	public void setBroadnetFee(int broadnetFee) {
		this.broadnetFee = broadnetFee;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getHasBreakfast() {
		return hasBreakfast;
	}

	public void setHasBreakfast(String hasBreakfast) {
		// if(!StringUtils.isEmpty(hasBreakfast)){
		// if(hasBreakfast.length()>3){
		// this.hasBreakfast = StringUtils.substring(hasBreakfast, 0, 3);
		// }else{
		// this.hasBreakfast = hasBreakfast;
		// }
		// }
		this.hasBreakfast = hasBreakfast;
	}

//	public double getCashBack() {
//		return cashBack;
//	}
//
//	public void setCashBack(double cashBack) {
//		this.cashBack = cashBack;
//	}

	public String getCashBackDesc() {
		return cashBackDesc;
	}

	public String getCashBack() {
		return cashBack;
	}

	public void setCashBack(String cashBack) {
		this.cashBack = cashBack;
	}

	public void setCashBackDesc(String cashBackDesc) {
		this.cashBackDesc = cashBackDesc;
	}

}
