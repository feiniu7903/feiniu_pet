package com.lvmama.comm.pet.po.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.utils.ActivityUtil;
import com.lvmama.comm.utils.FieldHandleUtil;
import com.lvmama.comm.vo.Constant;


/**
 * 门票,线路,酒店产品相关搜索表
 * @author huangzhi
 *
 */
public class ProductSearchInfo implements Serializable{
	private static final long serialVersionUID = -6240034684888479425L;
	public final static String DEFAULT_PIC = "http://pic.lvmama.com/cmt/images/img_90_90.jpg";
	public static enum IS_TICKET {
		TICKET("1"),  //门票
		HOTEL("2"),  //酒店
		FREENESS("3"),  //自由行
		GROUP("4");  //跟团游
		
		private String code;
		IS_TICKET(String code){
			this.code = code;
		}
		public String getCode(){
			return this.code;
		}
	};
	
	private Long productId;
	
	private Long prodBranchId;

	    private String productName;

	    private String recommendReason;

	    private Long marketPrice;

	    private Long sellPrice;

	    private String smallImage;

	    private String fromDest;

	    private String toDest;

	    private Date createTime;

	    private String isValid;

	    private Date updateTime;

	    private String isTicket;

	    private Long seq;

	    private String isHid;

	    private String cashRefund;

	    private String productUrl;

	    private String agio;

	    private String topic;

	    private String payMethod;

	    private String coupon;

	    private String visitDay;

	    private String isOld;

	    private String channel;
	    
	    private String channelFront;
	    
	    private String channelGroup;

	    private String toDestContent;

	    private String fromDestContent;

	    private String productType;

	    private String subProductType;

	    private Date nearDate;

	    private String costcontain;

	    private String importmentclew;

	    private String payToLvmama;

	    private String payToSupplier;

	    private Long minimum;

	    private Long maximum;

	    private Date offlineTime;

	    private String shortName;

	    private String largeImage;

	    private String onLine;

	    private String recommendInfoFirst;

	    private String recommendInfoSecond;

	    private String recommendInfoThird;
	    private String tagIds;
	    
	    private String tagsName;

	    private String tagsCss;
	    
	    private String tagsGroup;
	    
	    private String tagsDescript;
	    
	    private List<ProdTag> tagList =new ArrayList<ProdTag>();
	    
	    private Map<String,List<ProdTag>> tagGroupMap = new HashMap<String, List<ProdTag>>();
	    
	    private String productAlltoPlace;

	    private String productAlltoPlaceContent;

	    private String productAlltoPlaceIds;

	    private String prodRouteTypeName;

	    private String prodRouteTypeDes;

	    private String prodRouteTypeImg;

	    private String productAlltoPlacePinyin;

	    private Long orderPriceSum;

	    private String departArea;
	    
	    private String departAreaCode;
	    
	    private Long containerSeq;
	    
	    private String todayOrderAble;
	    

		private String placeKeywordBind;

	    private String selfPack;

	    private String orderToknown;

	    private Long orderQuantitySum;

	    private String bedType;

	    private String broadband;

	    private String travelTime;

	    private String scenicPlace;
	    
	    private List<ProdBranchSearchInfo> prodBranchSearchInfoList;

	    /**
		 * 交通
		 */
		private String traffic;
		/**
		 * 游玩人数
		 */
		private String playNum;
		/**
		 * 酒店类型
		 */
		private String hotelType;
		/**
		 * 产品点评数
		 */
		private Long cmtNum;
	    
	    private String routeStandard;
		private String attentionItem;
		
		private String couponAble;	//优惠券
		private String couponActivity;	//优惠活动
		/**
		 * 是否支持使用奖金账户Y,N
		 */
		private String canPayByBonus;
		private String isAperiodic = "false";
		private String shareWeiXin;
		public String getRouteStandard() {
			return routeStandard;
		}

		public void setRouteStandard(String routeStandard) {
			this.routeStandard = routeStandard;
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

		public String getRecommendReason() {
			return recommendReason;
		}

		public void setRecommendReason(String recommendReason) {
			this.recommendReason = recommendReason;
		}

		public Long getMarketPrice() {
			return marketPrice;
		}

		public void setMarketPrice(Long marketPrice) {
			this.marketPrice = marketPrice;
		}

		public Long getSellPrice() {
			return sellPrice;
		}

		public void setSellPrice(Long sellPrice) {
			this.sellPrice = sellPrice;
		}

		public String getSmallImage() {
			return smallImage;
		}
		
		public String getSmallImageUrl() {
			if (this.smallImage == null) {
				return DEFAULT_PIC;
			}
			if (smallImage.startsWith("http://")) {
				return smallImage;
			}
			return Constant.getInstance().getPrefixPic() + getSmallImage();
		}

		public void setSmallImage(String smallImage) {
			this.smallImage = smallImage;
		}

		public String getFromDest() {
			return fromDest;
		}

		public void setFromDest(String fromDest) {
			this.fromDest = fromDest;
		}

		public String getToDest() {
			return toDest;
		}

		public void setToDest(String toDest) {
			this.toDest = toDest;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public String getIsValid() {
			return isValid;
		}

		public void setIsValid(String isValid) {
			this.isValid = isValid;
		}

		public Date getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}

		public String getIsTicket() {
			return isTicket;
		}

		public void setIsTicket(String isTicket) {
			this.isTicket = isTicket;
		}

		public Long getSeq() {
			return seq;
		}

		public void setSeq(Long seq) {
			this.seq = seq;
		}

		public String getIsHid() {
			return isHid;
		}

		public void setIsHid(String isHid) {
			this.isHid = isHid;
		}

		public String getCashRefund() {
			return cashRefund;
		}

		public void setCashRefund(String cashRefund) {
			this.cashRefund = cashRefund;
		}

		public String getProductUrl() {
			return productUrl;
		}

		public void setProductUrl(String productUrl) {
			this.productUrl = productUrl;
		}

		public String getAgio() {
			return agio;
		}

		public void setAgio(String agio) {
			this.agio = agio;
		}

		public String getTopic() {
			return topic;
		}

		public void setTopic(String topic) {
			this.topic = topic;
		}

		public String getPayMethod() {
			return payMethod;
		}

		public void setPayMethod(String payMethod) {
			this.payMethod = payMethod;
		}

		public String getCoupon() {
			return coupon;
		}

		public void setCoupon(String coupon) {
			this.coupon = coupon;
		}

		public String getVisitDay() {
			return visitDay;
		}

		public void setVisitDay(String visitDay) {
			this.visitDay = visitDay;
		}

		public String getIsOld() {
			return isOld;
		}

		public void setIsOld(String isOld) {
			this.isOld = isOld;
		}

		public String getChannel() {
			return channel;
		}

		public void setChannel(String channel) {
			this.channel = channel;
		}

		public String getChannelFront() {
			return channelFront;
		}

		public void setChannelFront(String channelFront) {
			this.channelFront = channelFront;
		}

		public String getChannelGroup() {
			return channelGroup;
		}

		public void setChannelGroup(String channelGroup) {
			this.channelGroup = channelGroup;
		}

		public String getToDestContent() {
			return toDestContent;
		}

		public void setToDestContent(String toDestContent) {
			this.toDestContent = toDestContent;
		}

		public String getFromDestContent() {
			return fromDestContent;
		}

		public void setFromDestContent(String fromDestContent) {
			this.fromDestContent = fromDestContent;
		}

		public String getProductType() {
			return productType;
		}

		public void setProductType(String productType) {
			this.productType = productType;
		}

		public String getSubProductType() {
			return subProductType;
		}

		public void setSubProductType(String subProductType) {
			this.subProductType = subProductType;
		}

		public Date getNearDate() {
			return nearDate;
		}

		public void setNearDate(Date nearDate) {
			this.nearDate = nearDate;
		}

		public String getCostcontain() {
			return costcontain;
		}

		public void setCostcontain(String costcontain) {
			this.costcontain = costcontain;
		}

		public String getImportmentclew() {
			return importmentclew;
		}

		public void setImportmentclew(String importmentclew) {
			this.importmentclew = importmentclew;
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

		public Date getOfflineTime() {
			return offlineTime;
		}

		public void setOfflineTime(Date offlineTime) {
			this.offlineTime = offlineTime;
		}

		public String getShortName() {
			return shortName;
		}

		public void setShortName(String shortName) {
			this.shortName = shortName;
		}

		public String getLargeImage() {
			return largeImage;
		}
		
		public String getLargeImageUrl() {
			if (this.largeImage == null) {
				return DEFAULT_PIC;
			}
			if (largeImage.startsWith("http://")) {
				return largeImage;
			}
			return Constant.getInstance().getPrefixPic() + largeImage;
		}

		public void setLargeImage(String largeImage) {
			this.largeImage = largeImage;
		}

		public String getOnLine() {
			return onLine;
		}

		public void setOnLine(String onLine) {
			this.onLine = onLine;
		}

		public String getRecommendInfoFirst() {
			return recommendInfoFirst;
		}

		public void setRecommendInfoFirst(String recommendInfoFirst) {
			this.recommendInfoFirst = recommendInfoFirst;
		}

		public String getRecommendInfoSecond() {
			return recommendInfoSecond;
		}

		public void setRecommendInfoSecond(String recommendInfoSecond) {
			this.recommendInfoSecond = recommendInfoSecond;
		}

		public String getRecommendInfoThird() {
			return recommendInfoThird;
		}

		public void setRecommendInfoThird(String recommendInfoThird) {
			this.recommendInfoThird = recommendInfoThird;
		}

		public String getTagsName() {
			return tagsName;
		}

		public void setTagsName(String tagsName) {
			this.tagsName = tagsName;
		}

		public String getProductAlltoPlace() {
			return productAlltoPlace;
		}

		public void setProductAlltoPlace(String productAlltoPlace) {
			this.productAlltoPlace = productAlltoPlace;
		}

		public String getProductAlltoPlaceContent() {
			return productAlltoPlaceContent;
		}

		public void setProductAlltoPlaceContent(String productAlltoPlaceContent) {
			this.productAlltoPlaceContent = productAlltoPlaceContent;
		}

		public String getProductAlltoPlaceIds() {
			return productAlltoPlaceIds;
		}

		public void setProductAlltoPlaceIds(String productAlltoPlaceIds) {
			this.productAlltoPlaceIds = productAlltoPlaceIds;
		}

		public String getProdRouteTypeName() {
			return prodRouteTypeName;
		}

		public void setProdRouteTypeName(String prodRouteTypeName) {
			this.prodRouteTypeName = prodRouteTypeName;
		}

		public String getProdRouteTypeDes() {
			return prodRouteTypeDes;
		}

		public void setProdRouteTypeDes(String prodRouteTypeDes) {
			this.prodRouteTypeDes = prodRouteTypeDes;
		}

		public String getProdRouteTypeImg() {
			return prodRouteTypeImg;
		}

		public void setProdRouteTypeImg(String prodRouteTypeImg) {
			this.prodRouteTypeImg = prodRouteTypeImg;
		}

		public String getProductAlltoPlacePinyin() {
			return productAlltoPlacePinyin;
		}

		public void setProductAlltoPlacePinyin(String productAlltoPlacePinyin) {
			this.productAlltoPlacePinyin = productAlltoPlacePinyin;
		}

		public Long getOrderPriceSum() {
			return orderPriceSum;
		}

		public void setOrderPriceSum(Long orderPriceSum) {
			this.orderPriceSum = orderPriceSum;
		}

		public String getDepartArea() {
			return departArea;
		}

		public void setDepartArea(String departArea) {
			this.departArea = departArea;
		}

		public String getPlaceKeywordBind() {
			return placeKeywordBind;
		}

		public void setPlaceKeywordBind(String placeKeywordBind) {
			this.placeKeywordBind = placeKeywordBind;
		}

		public String getSelfPack() {
			return selfPack;
		}

		public void setSelfPack(String selfPack) {
			this.selfPack = selfPack;
		}

		public String getOrderToknown() {
			return orderToknown;
		}

		public void setOrderToknown(String orderToknown) {
			this.orderToknown = orderToknown;
		}

		public Long getOrderQuantitySum() {
			return orderQuantitySum;
		}

		public void setOrderQuantitySum(Long orderQuantitySum) {
			this.orderQuantitySum = orderQuantitySum;
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
		
		public String getBroadbandStr() {
			String broadbandStr = "无";
			if("free".equals(broadband)) {
				broadbandStr = "免费";
			} else if("fee".equals(broadband)) {
				broadbandStr = "收费";
			}
			return broadbandStr;
		}

		public void setBroadband(String broadband) {
			this.broadband = broadband;
		}

		public String getTravelTime() {
			return travelTime;
		}

		public void setTravelTime(String travelTime) {
			this.travelTime = travelTime;
		}

		public String getScenicPlace() {
			return scenicPlace;
		}

		public void setScenicPlace(String scenicPlace) {
			this.scenicPlace = scenicPlace;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public List<ProdBranchSearchInfo> getProdBranchSearchInfoList() {
			return prodBranchSearchInfoList;
		}

		public void setProdBranchSearchInfoList(
				List<ProdBranchSearchInfo> prodBranchSearchInfoList) {
			this.prodBranchSearchInfoList = prodBranchSearchInfoList;
		}

		public String getTraffic() {
			return traffic;
		}
		
		public String getTrafficHandle() {
			if (this.traffic == null) {
				return traffic;
			} else {
				return FieldHandleUtil.getFieldHandleStr(this.traffic);
			}
		}

		public void setTraffic(String traffic) {
			this.traffic = traffic;
		}

		public String getPlayNum() {
			return playNum;
		}
		
		public String getPlayNumHandle() {
			if (this.playNum == null) {
				return playNum;
			} else {
				return FieldHandleUtil.getFieldHandleStr(this.playNum);
			}
		}

		public void setPlayNum(String playNum) {
			this.playNum = playNum;
		}

		public String getHotelType() {
			return hotelType;
		}
		
		public String getHotelTypeHandle() {
			if (this.hotelType == null) {
				return hotelType;
			} else {
				return FieldHandleUtil.getFieldHandleStr(this.hotelType);
			}
		}

		public void setHotelType(String hotelType) {
			this.hotelType = hotelType;
		}
		
		public Long getCmtNum() {
			return cmtNum;
		}

		public void setCmtNum(Long cmtNum) {
			this.cmtNum = cmtNum;
		}

		public boolean isHotelSuit() {
			return "HOTEL_SUIT".equalsIgnoreCase(subProductType);
		}


		public String getAttentionItem() {
			return attentionItem;
		}

		public void setAttentionItem(String attentionItem) {
			this.attentionItem = attentionItem;
		}

		public Long getProdBranchId() {
			return prodBranchId;
		}

		public void setProdBranchId(Long prodBranchId) {
			this.prodBranchId = prodBranchId;
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

		public String getTodayOrderAble() {
			return todayOrderAble;
		}

		public void setTodayOrderAble(String todayOrderAble) {
			this.todayOrderAble = todayOrderAble;
		}


		public String getDepartAreaCode() {
			return departAreaCode;
		}

		public void setDepartAreaCode(String departAreaCode) {
			this.departAreaCode = departAreaCode;
		}

		public Long getContainerSeq() {
			return containerSeq;
		}

		public void setContainerSeq(Long containerSeq) {
			this.containerSeq = containerSeq;
		}

		public String getCanPayByBonus() {
			if(ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR)){
				canPayByBonus="N";
			}
			return canPayByBonus;
		}

		public void setCanPayByBonus(String canPayByBonus) {
			this.canPayByBonus = canPayByBonus;
		}

		public String getIsAperiodic() {
			return isAperiodic;
		}

		public void setIsAperiodic(String isAperiodic) {
			this.isAperiodic = isAperiodic;
		}
		
		public boolean hasAperiodic() {
			return "true".equalsIgnoreCase(isAperiodic)?true:false;
		}

		public String getTagsCss() {
			return tagsCss;
		}

		public void setTagsCss(String tagsCss) {
			this.tagsCss = tagsCss;
		}

		public String getTagsGroup() {
			return tagsGroup;
		}

		public void setTagsGroup(String tagsGroup) {
			this.tagsGroup = tagsGroup;
		}

		public String getTagsDescript() {
			return tagsDescript;
		}

		public void setTagsDescript(String tagsDescript) {
			this.tagsDescript = tagsDescript;
		}

		public List<ProdTag> getTagList() {
			if(tagList == null	){
				return new ArrayList<ProdTag>();
			}
			return tagList;
		}

		public void setTagList(List<ProdTag> tagList) {
			this.tagList = tagList;
		}

		public Map<String, List<ProdTag>> getTagGroupMap() {
			if(tagGroupMap ==  null){
				return new HashMap<String, List<ProdTag>>();
			}
			return tagGroupMap;
		}

		public String getTagIds() {
			return tagIds;
		}

		public void setTagIds(String tagIds) {
			this.tagIds = tagIds;
		}

		public void setTagGroupMap(Map<String, List<ProdTag>> tagGroupMap) {
			this.tagGroupMap = tagGroupMap;
		}

		public String getCouponAble() {
			return couponAble;
		}

		public void setCouponAble(String couponAble) {
			this.couponAble = couponAble;
		}

		public String getCouponActivity() {
			return couponActivity;
		}

		public void setCouponActivity(String couponActivity) {
			this.couponActivity = couponActivity;
		}

		public String getShareWeiXin() {
			return shareWeiXin;
		}

		public void setShareWeiXin(String shareWeiXin) {
			this.shareWeiXin = shareWeiXin;
		}
	
}
