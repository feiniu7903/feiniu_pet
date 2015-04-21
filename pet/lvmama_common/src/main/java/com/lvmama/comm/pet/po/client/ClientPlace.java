package com.lvmama.comm.pet.po.client;

import java.io.Serializable;
import java.util.List;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.comment.PlaceCmtScoreVO;

public class ClientPlace implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6635089981010249107L;
	// Fields
	private Long id;
	private String name = "";
	private String shortName = "";
	private String city ="";
	private String provice ="";
	private String address = "";
	private String placeGrade;
	private String description = "";
	private String lvmamaId;
	private String largeImage = "";
	private String middleImage = "";
	private String smallImage = "";
	private String isValid;
	private Long seq;
	private String keystonePlace;
	private String stage = "";
	private String recommendReason = "";
	private String phone;
	private Integer roomNum;
	private String startTime = "";
	private String hotelType = "";
	private String hotelFacility;
	private String entertainForeigner;
	private String hotelStar = "";
	private String pinYinUrl;
	private String placeMainTitel = "";
	private String placeTitel = "";
	//客户端添加
	private String recommendTime = "";
	private String airport = "";
	private String season = "";
	private String specialty = "";
	private String food = "";
	private String block = "";
	private Long marketPrice = 0L;
	private Long sellPrice = 0L;
	private String isClient;
	//经纬度
	private Float longitude = 0f;
	private Float latitude = 0f;
	private String cmtNum;
	//预定须知
	private String orderNotice;
	//重要提示
	private String importantTips;

	private List<String> imageList;
	
	private List<PlaceCmtScoreVO> placeCmtScoreList;
	
	public List<PlaceCmtScoreVO> getPlaceCmtScoreList() {
		return placeCmtScoreList;
	}

	public void setPlaceCmtScoreList(List<PlaceCmtScoreVO> placeCmtScoreList) {
		this.placeCmtScoreList = placeCmtScoreList;
	}

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	/** 酒店主题 */
	private String hotelTitle = "";
	/**
	 * 推荐和分享短信内容
	 */
	private String shareSms = "";
	/**
	 * 是否有产品
	 */
	private boolean hasProduct;

	/**
	 * 是否有目的地自由行产品
	 * @return
	 */
	private boolean hasFreeness;
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getHotelType() {
		return hotelType;
	}

	public void setHotelType(String hotelType) {
		this.hotelType = hotelType;
	}

	// Property accessors


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvice() {
		return this.provice;
	}

	public void setProvice(String provice) {
		this.provice = provice;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPlaceGrade() {
		return this.placeGrade;
	}

	public void setPlaceGrade(String placeGrade) {
		this.placeGrade = placeGrade;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getLvmamaId() {
		return this.lvmamaId;
	}

	public void setLvmamaId(String lvmamaId) {
		this.lvmamaId = lvmamaId;
	}

	public String getLargeImage() {
		return this.largeImage;
	}

	public void setLargeImage(String largeImage) {
		this.largeImage = largeImage;
	}

	public String getMiddleImage() {
		return this.middleImage;
	}

	public void setMiddleImage(String middleImage) {
		this.middleImage = middleImage;
	}

	public String getSmallImage() {
		return this.smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public String getIsValid() {
		return this.isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public Long getSeq() {
		return this.seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getKeystonePlace() {
		return this.keystonePlace;
	}

	public void setKeystonePlace(String keystonePlace) {
		this.keystonePlace = keystonePlace;
	}

	public String getStage() {
		return this.stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}


	public String getRecommendReason() {
		return recommendReason;
	}

	public void setRecommendReason(String recommendReason) {
		this.recommendReason = recommendReason;
	}


	public String getEntertainForeigner() {
		return entertainForeigner;
	}

	public void setEntertainForeigner(String entertainForeigner) {
		this.entertainForeigner = entertainForeigner;
	}

	public String getHotelFacility() {
		return hotelFacility;
	}

	public void setHotelFacility(String hotelFacility) {
		this.hotelFacility = hotelFacility;
	}

	public String getEntertainForeignerStr() {
		return "true".equals(this.entertainForeigner) ? "涉外" : "不涉外";
	}

	public String getHotelStar() {
		return hotelStar;
	}

	public void setHotelStar(String hotelStar) {
		this.hotelStar = hotelStar;
	}

	
	public void setHotelTitle(String hotelTitle) {
		this.hotelTitle = hotelTitle;
	}

	public String getPlaceMainTitel() {
		return placeMainTitel;
	}

	public void setPlaceMainTitel(String placeMainTitel) {
		this.placeMainTitel = placeMainTitel;
	}

	public String getPlaceTitel() {
		return placeTitel;
	}

	public void setPlaceTitel(String placeTitel) {
		this.placeTitel = placeTitel;
	}

	
	public void setAirport(String airport) {
		this.airport = airport;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
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
		if (sellPrice==null) {
			return;
		}
		this.sellPrice = sellPrice;
	}


	public String getIsClient() {
		return isClient;
	}

	public void setIsClient(String isClient) {
		this.isClient = isClient;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	
	public String getPlaceSms(String stage){
		String sms = "我在驴妈妈看到一个不错的 "+("2".equals(stage)?"景点":"酒店")+" "+this.name;
		if(sellPrice!=null&&sellPrice!=0L){
			sms+=" 驴妈妈价:"+PriceUtil.convertToYuan(sellPrice)+"元 ";
		}
		if(address!=null){
			sms+=" 地址:"+address;
		}
		if(startTime!=null){
			sms+=" 开放时间:"+startTime;
		}
		sms+=""+("2".equals(stage)?" 订票":" 预订")+"热线：10106060";
		sms+=" 网址：http://dest.lvmama.com/place/mobilePlace.do?id="+this.id;
		return sms;
	}
	
	public String getSellPriceYuan() {
		if(sellPrice==-100){
			return "免费";
		}
		
		return sellPrice == null||sellPrice==0L? "" : PriceUtil.getLongPriceYuan(sellPrice)+"";
	}
	
	public String  getMaketPriceYuan() {
		
		return marketPrice == null ||marketPrice==0L ? "" : PriceUtil.getLongPriceYuan(marketPrice)+"";
	}
	
	public String getShareContent(){
		String marketPriceTipsInfo = "";
		String sellPriceTipsInfo = "";
		if ("免费".equals(this.getSellPriceYuan())) {
			marketPriceTipsInfo = String.format("免费哦");
		} else {
		if (null!=getMaketPriceYuan()&&!"".equals(this.getMaketPriceYuan())) {
			marketPriceTipsInfo = String.format("市场价：%s，", getMaketPriceYuan());
		} 
		
		if (!"".equals(this.getSellPriceYuan())&&!"免费".equals(this.getSellPriceYuan())) {
			sellPriceTipsInfo = String.format("驴妈妈价：%s，", getSellPriceYuan());
		} 
		}
		String placeName = StringUtil.subStringStr(this.name, 60);
		String content = String.format("#景点推荐#我刚在@驴妈妈旅游网，发现“%s”很不错哦！%s%s", placeName,marketPriceTipsInfo,sellPriceTipsInfo);
		return content;
	}
	
	public String getShareUrl() {
		return "http://www.lvmama.com/dest/"+this.getPinYinUrl();
	}
	
	public String getShareSms() {
		return shareSms;
	}

	public void setShareSms(String shareSms) {
		this.shareSms = shareSms;
	}

	public boolean isHasProduct() {
		return hasProduct;
	}

	public void setHasProduct(boolean hasProduct) {
		this.hasProduct = hasProduct;
	}

	public boolean isHasFreeness() {
		return hasFreeness;
	}

	public void setHasFreeness(boolean hasFreeness) {
		this.hasFreeness = hasFreeness;
	}

	public String getAirport() {
		return airport;
	}

	public String getCmtNum() {
		return cmtNum;
	}

	public void setCmtNum(String cmtNum) {
		this.cmtNum = cmtNum;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPinYinUrl() {
		return pinYinUrl;
	}

	public void setPinYinUrl(String pinYinUrl) {
		this.pinYinUrl = pinYinUrl;
	}

	public String getHotelTitle() {
		return hotelTitle;
	}

	public String getRecommendTime() {
		return recommendTime;
	}

	public void setRecommendTime(String recommendTime) {
		this.recommendTime = recommendTime;
	}

	public String getOrderNotice() {
		return orderNotice;
	}

	public void setOrderNotice(String orderNotice) {
		this.orderNotice = orderNotice;
	}

	public String getImportantTips() {
		return importantTips;
	}

	public void setImportantTips(String importantTips) {
		this.importantTips = importantTips;
	}

}
