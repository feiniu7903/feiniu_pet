package com.lvmama.comm.search.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.prod.ProdTag;

/*
 * 产品大类
 * 2012-12-31 huangzhi
 * */
public class ProductBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 624927742827466829L;

	private Long productId;
	private String productName;
	private String recommendReason;
	private Long marketPrice;
	private Long sellPrice;// 销售价格范围
	private String smallImage;
	private String fromDest;
	private String toDest;
	private String fromDestContent;
	private String toDestContent;
	private String createTime;
	private String isValid;
	private String updateTime;
	private String isTicket;// 产品类型
	private Long seq;
	private String isHid;
	private Long agio;// 折扣
	private String topic;// 主题
	private String payMethod;// 支付方式
	private String coupon;// 优惠卷
	private Long visitDay;// 出游天数
	private String ticketTypeName;
	private String productUrl;
	private String cashRefund; // 奖金查询
	private String productInfo;
	private String productChannel;
	private String isOld;
	private String productType;
	private String subProductType;
	private String update_time;
	/**
	 * 标签名称,多个标签以 , 分隔
	 * */
	private String tagsName;
	/**
	 * 标签描述,多个标签以 , 分隔
	 * */
	private String tagsDescript;
	/**
	 * 标签Class样式名称,多个标签以 , 分隔
	 * */
	private String tagsCss;
	/**
	 * 标签小组名称,多个标签以 , 分隔
	 * */
	private String tagsGroup;

	/**
	 * 所有的标签列表
	 */
	private List<ProdTag> tagList = new ArrayList<ProdTag>();
	/**
	 * 标签按照标签组分类
	 */
	private Map<String, List<ProdTag>> tagGroupMap = new HashMap<String, List<ProdTag>>();

	private String productAllToPlace; // 只存城市，地区筛选用
	private String productAllToPlaceContent; // 所有涉及的地标
	private String productAllToPlaceIds; // 产品的目的地地标
	private String productRouteTypeName;
	private String productRouteTypeDes;
	private String productRouteTypeImg;

	private String productAllToPlacePinYin;
	private String departArea;
	private String placeKeywordBind; // 来自PLACE的高频关键字HFKW集合
	private String bedType; // 默认类别产品床型
	private String broadBand; // 默认类别产品宽带
	private Date offlineTime; // 下线时间
	private String validTime; // 团购倒计时
	private Date todayOrderAbleTime;
	private String channelFront;
	private String channelGroup;
	private Long cmtNum;
	private Float score;// lucene计算的分数
	private Float hbasescore;// hbase的分数
	private Float normalscore;// 初始的分数；

	private Long subTypeSale;// 子类别销量；
	private String onLineTime;// 上线时间

	private Float salePer;// 销量壁纸

	private Float midSalePer;// 中值销量比值

	private Float timediff;// 时间差

	private Float tagnum;// 标签数量

	private Float brandnum;// 品牌数量

	private Float subTypeMaxTagNum;// 子类别最大标签数

	private Float subTypeMaxBrandNum;// 子类别最大品牌数

	private Float tagratio;// 标签比值

	private Float brandratio;// 品牌比值

	private Float realWeekSales;// 真实销量
	/**
	 * 超级自由行标示
	 */
	private String selfPack;
	/**
	 * 一句话推荐 2
	 */
	private String recommendInfoSecond;

	// 以下是product_property_search_info属性
	/** 上岛交通 */
	private String landTraffic;
	/** 岛屿特色 */
	private String landFeature;
	/** 旅游区域 */
	private String playArea;
	/** 游玩线路 */
	private String playLine;
	/** 游玩特色 */
	private String playFeatures;
	/** 游玩特色品牌 */
	private String playBrand;
	/** 游玩人数 */
	private String playNum;
	/** 往返交通 */
	private String traffic;
	/** 酒店类型 */
	private String hotelType;
	/** 酒店位置 */
	private String hotelLocation;
	/** 线路主题 */
	private String routeTopic;
	/** 出发班期 */
	private String travelTime;
	/** 途径景点 */
	private String scenicPlace;
	/** 统计数 **/
	private Long weekSales;
	/** 是否是期票 */
	private String isAperiodic = "false";

	//微信分享标识
	private String shareweixin;
	
	public String getLandTraffic() {
		if (null != landTraffic) {
			return landTraffic;
		} else {
			return "";
		}
	}

	public void setLandTraffic(String landTraffic) {
		this.landTraffic = landTraffic;
	}

	public String getLandFeature() {
		if (null != landFeature) {
			return landFeature;
		} else {
			return "";
		}
	}

	public void setLandFeature(String landFeature) {
		this.landFeature = landFeature;
	}

	public String getTravelTime() {
		if (null != travelTime) {
			return travelTime;
		} else {
			return "";
		}
	}

	public void setTravelTime(String travelTime) {
		this.travelTime = travelTime;
	}

	public String getScenicPlace() {
		if (null != scenicPlace) {
			return scenicPlace;
		} else {
			return "";
		}
	}

	public void setScenicPlace(String scenicPlace) {
		this.scenicPlace = scenicPlace;
	}

	public synchronized Float getRealWeekSales() {
		if (null != realWeekSales) {
			return realWeekSales;
		} else {
			return 0F;
		}
	}

	public synchronized void setRealWeekSales(Float realWeekSales) {
		this.realWeekSales = realWeekSales;
	}

	public synchronized Float getTagratio() {
		if (null != tagratio) {
			return tagratio;
		} else {
			return 0F;
		}
	}

	public synchronized void setTagratio(Float tagratio) {
		this.tagratio = tagratio;
	}

	public synchronized Float getBrandratio() {
		if (null != brandratio) {
			return brandratio;
		} else {
			return 0F;
		}
	}

	public synchronized void setBrandratio(Float brandratio) {
		this.brandratio = brandratio;
	}

	public synchronized Float getSalePer() {
		if (null != salePer) {
			return salePer;
		} else {
			return 0F;
		}
	}

	public synchronized void setSalePer(Float salePer) {
		this.salePer = salePer;
	}

	public synchronized void setSubTypeMaxBrandNum(Long subTypeMaxBrandNum) {
		this.subTypeMaxBrandNum = subTypeMaxBrandNum.floatValue();
	}

	public synchronized Float getTagnum() {
		if (null != tagnum) {
			return tagnum;
		} else {
			return 0F;
		}
	}

	public synchronized void setTagnum(Float tagnum) {
		this.tagnum = tagnum;
	}

	public synchronized Float getBrandnum() {
		if (null != brandnum) {
			return brandnum;
		} else {
			return 0F;
		}
	}

	public synchronized void setBrandnum(Float brandnum) {
		this.brandnum = brandnum;
	}

	public synchronized Float getSubTypeMaxTagNum() {
		if (null != subTypeMaxTagNum) {
			return subTypeMaxTagNum;
		} else {
			return 0F;
		}
	}

	public synchronized void setSubTypeMaxTagNum(Float subTypeMaxTagNum) {
		this.subTypeMaxTagNum = subTypeMaxTagNum;
	}

	public synchronized Float getSubTypeMaxBrandNum() {
		if (null != subTypeMaxBrandNum) {
			return subTypeMaxBrandNum;
		} else {
			return 0F;
		}
	}

	public synchronized void setSubTypeMaxBrandNum(Float subTypeMaxBrandNum) {
		this.subTypeMaxBrandNum = subTypeMaxBrandNum;
	}

	public synchronized Float getTimediff() {
		if (null != timediff) {
			return timediff;
		} else {
			return 0F;
		}
	}

	public synchronized void setTimediff(Float timediff) {
		this.timediff = timediff;
	}

	public synchronized Float getMidSalePer() {
		if (null != midSalePer) {
			return midSalePer;
		} else {
			return 0F;
		}
	}

	public synchronized void setMidSalePer(Float midSalePer) {
		this.midSalePer = midSalePer;
	}

	public String getPlayArea() {
		if (null != playArea) {
			return playArea;
		} else {
			return "";
		}
	}

	public void setPlayArea(String playArea) {
		this.playArea = playArea;
	}

	public String getPlayLine() {
		if (null != playLine) {
			return playLine;
		} else {
			return "";
		}
	}

	public void setPlayLine(String playLine) {
		this.playLine = playLine;
	}

	public String getPlayBrand() {
		if (null != playBrand) {
			return playBrand;
		} else {
			return "";
		}
	}

	public void setPlayBrand(String playBrand) {
		this.playBrand = playBrand;
	}

	public synchronized Long getSubTypeSale() {
		if (null != subTypeSale) {
			return subTypeSale;
		} else {
			return 0L;
		}
	}

	public synchronized void setSubTypeSale(Long subTypeSale) {
		this.subTypeSale = subTypeSale;
	}

	public synchronized String getOnLineTime() {
		if (null != onLineTime) {
			return onLineTime;
		} else {
			return "";
		}
	}

	public synchronized void setOnLineTime(String onLineTime) {
		this.onLineTime = onLineTime;
	}

	public String getPlayNum() {
		if (null != playNum) {
			return playNum;
		} else {
			return "";
		}
	}

	public void setPlayNum(String playNum) {
		this.playNum = playNum;
	}

	public String getTraffic() {
		if (null != traffic) {
			return traffic;
		} else {
			return "";
		}
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getHotelType() {
		if (null != hotelType) {
			return hotelType;
		} else {
			return "";
		}
	}

	public void setHotelType(String hotelType) {
		this.hotelType = hotelType;
	}

	public String getHotelLocation() {
		if (null != hotelLocation) {
			return hotelLocation;
		} else {
			return "";
		}
	}

	public void setHotelLocation(String hotelLocation) {
		this.hotelLocation = hotelLocation;
	}

	public String getRouteTopic() {
		if (null != routeTopic) {
			return routeTopic;
		} else {
			return "";
		}
	}

	public void setRouteTopic(String routeTopic) {
		this.routeTopic = routeTopic;
	}

	public String getPlaceKeywordBind() {
		if (null != placeKeywordBind) {
			return placeKeywordBind;
		} else {
			return "";
		}
	}

	public void setPlaceKeywordBind(String placeKeywordBind) {
		this.placeKeywordBind = placeKeywordBind;
	}

	public String getDepartArea() {
		if (null != departArea) {
			return departArea;
		} else {
			return "";
		}
	}

	public void setDepartArea(String departArea) {
		this.departArea = departArea;
	}

	public String getProductAllToPlacePinYin() {
		if (null != productAllToPlacePinYin) {
			return productAllToPlacePinYin;
		} else {
			return "";
		}
	}

	public void setProductAllToPlacePinYin(String productAllToPlacePinYin) {
		this.productAllToPlacePinYin = productAllToPlacePinYin;
	}

	public String getProductAllToPlaceIds() {
		if (null != productAllToPlaceIds) {
			return productAllToPlaceIds;
		} else {
			return "";
		}
	}

	public void setProductAllToPlaceIds(String productAllToPlaceIds) {
		this.productAllToPlaceIds = productAllToPlaceIds;
	}

	public synchronized Float getHbasescore() {
		if (null != hbasescore) {
			return hbasescore;
		} else {
			return 0F;
		}
	}

	public synchronized void setHbasescore(Float hbasescore) {
		this.hbasescore = hbasescore;
	}

	public synchronized Float getNormalscore() {
		if (null != normalscore) {
			return normalscore;
		} else {
			return 0F;
		}
	}

	public synchronized void setNormalscore(Float normalscore) {
		this.normalscore = normalscore;
	}

	public String getProductAllToPlace() {
		if (null != productAllToPlace) {
			return productAllToPlace;
		} else {
			return "";
		}
	}

	public void setProductAllToPlace(String productAllToPlace) {
		this.productAllToPlace = productAllToPlace;
	}

	public String getProductAllToPlaceContent() {
		if (null != productAllToPlaceContent) {
			return productAllToPlaceContent;
		} else {
			return "";
		}
	}

	public void setProductAllToPlaceContent(String productAllToPlaceContent) {
		this.productAllToPlaceContent = productAllToPlaceContent;
	}

	public String getPlayFeatures() {
		if (null != playFeatures) {
			return playFeatures;
		} else {
			return "";
		}
	}

	public void setPlayFeatures(String playFeatures) {
		this.playFeatures = playFeatures;
	}

	public String getFromDestContent() {
		if (null != fromDestContent) {
			return fromDestContent;
		} else {
			return "";
		}
	}

	public void setFromDestContent(String fromDestContent) {
		this.fromDestContent = fromDestContent;
	}

	public String getToDestContent() {
		if (null != toDestContent) {
			return toDestContent;
		} else {
			return "";
		}
	}

	public void setToDestContent(String toDestContent) {
		this.toDestContent = toDestContent;
	}

	public String getCashRefund() {
		if (null != cashRefund) {
			return cashRefund;
		} else {
			return "";
		}
	}

	public void setCashRefund(String cashRefund) {
		this.cashRefund = cashRefund;
	}

	public String getProductUrl() {
		if (null != productUrl) {
			return productUrl;
		} else {
			return "";
		}
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public Long getVisitDay() {
		if (null != visitDay) {
			return visitDay;
		} else {
			return 0L;
		}
	}

	public void setVisitDay(Long visitDay) {
		this.visitDay = visitDay;
	}

	public String getValidTime() {
		if (null != validTime) {
			return validTime;
		} else {
			return "";
		}
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public Long getProductId() {
		if (null != productId) {
			return productId;
		} else {
			return 0L;
		}
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		if (null != productName) {
			return productName;
		} else {
			return "";
		}
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getRecommendReason() {
		if (null != recommendReason) {
			return recommendReason;
		} else {
			return "";
		}
	}

	public void setRecommendReason(String recommendReason) {
		this.recommendReason = recommendReason;
	}

	public synchronized Float getScore() {
		if (null != score) {
			return score;
		} else {
			return 0F;
		}
	}

	public synchronized void setScore(Float score) {
		this.score = score;
	}

	public String getUpdate_time() {
		if (null != update_time) {
			return update_time;
		} else {
			return "";
		}
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getSmallImage() {
		if (null != smallImage) {
			return smallImage;
		} else {
			return "";
		}
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public String getFromDest() {
		if (null != fromDest) {
			return fromDest;
		} else {
			return "";
		}
	}

	public void setFromDest(String fromDest) {
		this.fromDest = fromDest;
	}

	public String getCreateTime() {
		if (null != createTime) {
			return createTime;
		} else {
			return "";
		}
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getIsValid() {
		if (null != isValid) {
			return isValid;
		} else {
			return "";
		}
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public Long getCmtNum() {
		if (null != cmtNum) {
			return cmtNum;
		} else {
			return 0L;
		}
	}

	public void setCmtNum(Long cmtNum) {
		this.cmtNum = cmtNum;
	}

	public String getUpdateTime() {
		if (null != updateTime) {
			return updateTime;
		} else {
			return "";
		}
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getIsTicket() {
		if (null != isTicket) {
			return isTicket;
		} else {
			return "";
		}
	}

	public void setIsTicket(String isTicket) {
		this.isTicket = isTicket;
	}

	public String getIsHid() {
		if (null != isHid) {
			return isHid;
		} else {
			return "";
		}
	}

	public void setIsHid(String isHid) {
		this.isHid = isHid;
	}

	public String getTopic() {
		if (null != topic) {
			return topic;
		} else {
			return "";
		}
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getPayMethod() {
		if (null != payMethod) {
			return payMethod;
		} else {
			return "";
		}
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getCoupon() {
		if (null != coupon) {
			return coupon;
		} else {
			return "";
		}
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public String getTicketTypeName() {
		if (this.isTicket.indexOf("1") > -1) {
			ticketTypeName = "打折门票";
		} else if (this.isTicket.indexOf("3") > -1
				|| this.isTicket.indexOf("5") > -1) {
			ticketTypeName = "自由行";
		} else if (this.isTicket.indexOf("4") > -1) {
			ticketTypeName = "跟团游";
		} else if (this.isTicket.indexOf("2") > -1) {
			ticketTypeName = "酒店";
		}
		if (null != ticketTypeName) {
			return ticketTypeName;
		} else {
			return "";
		}
	}

	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = this.isTicket;
	}

	public Float getMarketPrice() {
		if(null == marketPrice){
			marketPrice = 0L;
		}
		BigDecimal bd1 = new BigDecimal(this.marketPrice);
		BigDecimal bd2 = BigDecimal.valueOf(100L);
		bd1 = bd1.divide(bd2, 2, BigDecimal.ROUND_HALF_DOWN);
		return bd1.floatValue();
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Float getSellPrice() {
		if(null == sellPrice){
			sellPrice = 0L;
		}
		BigDecimal bd1 = new BigDecimal(this.sellPrice);
		BigDecimal bd2 = BigDecimal.valueOf(100L);
		bd1 = bd1.divide(bd2, 2, BigDecimal.ROUND_HALF_DOWN);
		return bd1.floatValue();
	}

	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Long getSeq() {
		if (null != seq) {
			return seq;
		} else {
			return 0L;
		}
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Long getAgio() {
		if (null != agio) {
			return agio;
		} else {
			return 0L;
		}
	}

	public String getChannelFront() {
		if (null != channelFront) {
			return channelFront;
		} else {
			return "";
		}
	}

	public void setChannelFront(String channelFront) {
		this.channelFront = channelFront;
	}

	public String getChannelGroup() {
		if (null != channelGroup) {
			return channelGroup;
		} else {
			return "";
		}
	}

	public void setChannelGroup(String channelGroup) {
		this.channelGroup = channelGroup;
	}

	public Date getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(Date offlineTime) {
		this.offlineTime = offlineTime;
	}

	public void setAgio(Long agio) {
		this.agio = agio;
	}

	public String getProductInfo() {
		if (null != productInfo) {
			return productInfo;
		} else {
			return "";
		}
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}

	public String getToDest() {
		if (null != toDest) {
			return toDest;
		} else {
			return "";
		}
	}

	public void setToDest(String toDest) {
		this.toDest = toDest;
	}

	public String getProductChannel() {
		if (null != productChannel) {
			return productChannel;
		} else {
			return "";
		}
	}

	public void setProductChannel(String productChannel) {
		this.productChannel = productChannel;
	}

	public String getIsOld() {
		if (null != isOld) {
			return isOld;
		} else {
			return "";
		}
	}

	public void setIsOld(String isOld) {
		this.isOld = isOld;
	}

	public String getProductType() {
		if (null != productType) {
			return productType;
		} else {
			return "";
		}
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getSubProductType() {
		if (null != subProductType) {
			return subProductType;
		} else {
			return "";
		}
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getProductRouteTypeName() {
		if (null != productRouteTypeName) {
			return productRouteTypeName;
		} else {
			return "";
		}
	}

	public void setProductRouteTypeName(String productRouteTypeName) {
		this.productRouteTypeName = productRouteTypeName;
	}

	public String getProductRouteTypeDes() {
		if (null != productRouteTypeDes) {
			return productRouteTypeDes;
		} else {
			return "";
		}
	}

	public void setProductRouteTypeDes(String productRouteTypeDes) {
		this.productRouteTypeDes = productRouteTypeDes;
	}

	public String getProductRouteTypeImg() {
		if (null != productRouteTypeImg) {
			return productRouteTypeImg;
		} else {
			return "";
		}
	}

	public void setProductRouteTypeImg(String productRouteTypeImg) {
		this.productRouteTypeImg = productRouteTypeImg;
	}

	public String getBedType() {
		if (null != bedType) {
			return bedType;
		} else {
			return "";
		}
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public String getBroadBand() {
		if (null != broadBand) {
			return broadBand;
		} else {
			return "";
		}
	}

	public void setBroadBand(String broadBand) {
		this.broadBand = broadBand;
	}

	public Long getWeekSales() {
		if (null != weekSales) {
			return weekSales;
		} else {
			return 0L;
		}
	}

	public void setWeekSales(Long weekSales) {
		this.weekSales = weekSales;
	}

	/** sellPrice排序比较器 **/
	public static class comparatorSellPrice implements Comparator<ProductBean> {
		public int compare(ProductBean o1, ProductBean o2) {
			ProductBean s1 = (ProductBean) o1;
			ProductBean s2 = (ProductBean) o2;
			int result = (int) (s1.sellPrice - s2.sellPrice);
			return result;
		}

	}

	public String getSelfPack() {
		if (null != selfPack) {
			return selfPack;
		} else {
			return "";
		}
	}

	public void setSelfPack(String selfPack) {
		this.selfPack = selfPack;
	}

	public String getRecommendInfoSecond() {
		if (null != recommendInfoSecond) {
			return recommendInfoSecond;
		} else {
			return "";
		}
	}

	public void setRecommendInfoSecond(String recommendInfoSecond) {
		this.recommendInfoSecond = recommendInfoSecond;
	}

	public String getIsAperiodic() {
		if (null != isAperiodic) {
			return isAperiodic;
		} else {
			return "";
		}
	}

	public void setIsAperiodic(String isAperiodic) {
		this.isAperiodic = isAperiodic;
	}

	public String getTagsCss() {
		if (null != tagsCss) {
			return tagsCss;
		} else {
			return "";
		}
	}

	public void setTagsCss(String tagsCss) {
		this.tagsCss = tagsCss;
	}

	public String getTagsGroup() {
		if (null != tagsGroup) {
			return tagsGroup;
		} else {
			return "";
		}
	}

	public void setTagsGroup(String tagsGroup) {
		this.tagsGroup = tagsGroup;
	}

	public Map<String, List<ProdTag>> getTagGroupMap() {
		return tagGroupMap;
	}

	public void setTagGroupMap(Map<String, List<ProdTag>> tagGroupMap) {
		this.tagGroupMap = tagGroupMap;
	}

	public List<ProdTag> getTagList() {
		return tagList;
	}

	public void setTagList(List<ProdTag> tagList) {
		this.tagList = tagList;
	}

	public String getTagsName() {
		if (null != tagsName) {
			return tagsName;
		} else {
			return "";
		}
	}

	public void setTagsName(String tagsName) {
		this.tagsName = tagsName;
	}

	public String getTagsDescript() {
		if (null != tagsDescript) {
			return tagsDescript;
		} else {
			return "";
		}
	}

	public void setTagsDescript(String tagsDescript) {
		this.tagsDescript = tagsDescript;
	}

	public Date getTodayOrderAbleTime() {
		return todayOrderAbleTime;
	}

	public void setTodayOrderAbleTime(Date todayOrderAbleTime) {
		this.todayOrderAbleTime = todayOrderAbleTime;
	}

	public String getShareweixin() {
		return shareweixin;
	}
    
	public void setShareweixin(String shareweixin) {
		this.shareweixin = shareweixin;
	}

}
