package com.lvmama.comm.pet.po.place;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;

public class PlaceHotel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long placeId;
	/**
	 * 酒店英文地址
	 */
	private String addressEnglish;
	/**
	 * 酒店位置
	 */
	private String hotelPosition;
	/**
	 * 酒店电话
	 */
	private String hotelPhone;
	/**
	 * 酒店传真
	 */
	private String hotelFax;
	/**
	 * 酒店电子邮箱
	 */
	private String hotelEmail;
	/**
	 * 酒店邮政编码
	 */
	private String hotelZipCode;
	/**
	 * 酒店房间数
	 */
	private Long hotelRoomNum;
	/**
	 * 酒店级别
	 */
	private String hotelLevel;
	/**
	 * 酒店类型
	 */
	private String hotelType;
	/**
	 * 酒店星级
	 */
	private String hotelStar;
	/**
	 * 酒店所属集团
	 */
	private String hotelCompany;
	/**
	 * 酒店品牌
	 */
	private String hotelBrand;
	/**
	 * 是否接待外宾
	 */
	private String hotelForeigner;
	/**
	 * 酒店主题
	 */
	private String hotelTopic;
	/**
	 * 开业时间
	 */
	private Date hotelOpenTime;
	private String hotelOpenTimeStr;
	
	/**
	 * 最近装修时间
	 */
	private Date hotelDecorationTime;
	private String hotelDecorationTimeStr;
	/**
	 * 入住时间
	 */
	private String checkinTime;
	/**
	 * 离店时间
	 */
	private String checkoutTime;
	/**
	 * 早餐类型
	 */
	private String breakfastType;
	/**
	 * 早餐价格
	 */
	private String breakfastPrice;
	/**
	 * 支持的信用卡
	 */
	private String creditCard;
	/**
	 * 综合设施
	 */
	private String integratedFacilities;
	/**
	 * 客房设施
	 */
	private String roomFacilities;
	/**
	 * 娱乐设施
	 */
	private String recreationalFacilities;
	/**
	 * 餐饮设施
	 */
	private String diningFacilities;
	/**
	 * 服务项目
	 */
	private String services;
	/**
	 * 酒店列表显示图片的大小
	 */
	private String picDisplay;
	/**
	 * 是否可以携带宠物
	 */
	private String petCarry;
	/**
	 * 酒店交通
	 */
	private List<HotelTrafficInfo> hotelTrafficInfos;
	/**
	 * 酒店交通信息HTML
	 * @return
	 */
	public String getHotelTrafficInfoHtml(){
		StringBuffer stringBuffer = new StringBuffer();
		if(hotelTrafficInfos!=null){
			for (HotelTrafficInfo hotelTrafficInfo : hotelTrafficInfos) {
				if(stringBuffer.length()>0){
					stringBuffer.append("<br>");
				}
				stringBuffer.append("<b>"+hotelTrafficInfo.getChTrafficInfo()+"</b>"+"："+hotelTrafficInfo.getFrom()+"，距离酒店"+hotelTrafficInfo.getDistance()+"公里");
				if(StringUtil.isNotEmptyString(hotelTrafficInfo.getDescription())){
					stringBuffer.append("，备注："+hotelTrafficInfo.getDescription()+"；");
				}else {
					stringBuffer.append("；");
				}
			}
		}
		return stringBuffer.toString();
	}

	public List<HotelTrafficInfo> getHotelTrafficInfos() {
		return hotelTrafficInfos;
	}

	public void setHotelTrafficInfos(List<HotelTrafficInfo> hotelTrafficInfos) {
		this.hotelTrafficInfos = hotelTrafficInfos;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public String getAddressEnglish() {
		return addressEnglish;
	}

	public void setAddressEnglish(String addressEnglish) {
		this.addressEnglish = addressEnglish;
	}

	public String getHotelPosition() {
		return hotelPosition;
	}

	public void setHotelPosition(String hotelPosition) {
		this.hotelPosition = hotelPosition;
	}

	public String getHotelPhone() {
		return hotelPhone;
	}

	public void setHotelPhone(String hotelPhone) {
		this.hotelPhone = hotelPhone;
	}

	public String getHotelFax() {
		return hotelFax;
	}

	public void setHotelFax(String hotelFax) {
		this.hotelFax = hotelFax;
	}

	public String getHotelEmail() {
		return hotelEmail;
	}

	public void setHotelEmail(String hotelEmail) {
		this.hotelEmail = hotelEmail;
	}

	public String getHotelZipCode() {
		return hotelZipCode;
	}

	public void setHotelZipCode(String hotelZipCode) {
		this.hotelZipCode = hotelZipCode;
	}

	public Long getHotelRoomNum() {
		return hotelRoomNum;
	}

	public void setHotelRoomNum(Long hotelRoomNum) {
		this.hotelRoomNum = hotelRoomNum;
	}

	public String getHotelLevel() {
		return hotelLevel;
	}

	public void setHotelLevel(String hotelLevel) {
		this.hotelLevel = hotelLevel;
	}

	public String getHotelType() {
		return hotelType;
	}

	public void setHotelType(String hotelType) {
		this.hotelType = hotelType;
	}

	public String getHotelStar() {
		return hotelStar;
	}

	public void setHotelStar(String hotelStar) {
		this.hotelStar = hotelStar;
	}

	public String getHotelCompany() {
		return hotelCompany;
	}

	public void setHotelCompany(String hotelCompany) {
		this.hotelCompany = hotelCompany;
	}

	public String getHotelBrand() {
		return hotelBrand;
	}

	public void setHotelBrand(String hotelBrand) {
		this.hotelBrand = hotelBrand;
	}

	public String getHotelForeigner() {
		return hotelForeigner;
	}

	public void setHotelForeigner(String hotelForeigner) {
		this.hotelForeigner = hotelForeigner;
	}

	public String getHotelTopic() {
		return hotelTopic;
	}

	public void setHotelTopic(String hotelTopic) {
		this.hotelTopic = hotelTopic;
	}

	public String getCheckinTime() {
		return checkinTime;
	}

	public void setCheckinTime(String checkinTime) {
		this.checkinTime = checkinTime;
	}

	public String getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(String checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	public String getBreakfastType() {
		return breakfastType;
	}

	public void setBreakfastType(String breakfastType) {
		this.breakfastType = breakfastType;
	}

	public String getBreakfastPrice() {
		return breakfastPrice;
	}

	public void setBreakfastPrice(String breakfastPrice) {
		this.breakfastPrice = breakfastPrice;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public String getIntegratedFacilities() {
		return integratedFacilities;
	}

	public void setIntegratedFacilities(String integratedFacilities) {
		this.integratedFacilities = integratedFacilities;
	}

	public String getRoomFacilities() {
		return roomFacilities;
	}

	public void setRoomFacilities(String roomFacilities) {
		this.roomFacilities = roomFacilities;
	}

	public String getRecreationalFacilities() {
		return recreationalFacilities;
	}

	public void setRecreationalFacilities(String recreationalFacilities) {
		this.recreationalFacilities = recreationalFacilities;
	}

	public String getDiningFacilities() {
		return diningFacilities;
	}

	public void setDiningFacilities(String diningFacilities) {
		this.diningFacilities = diningFacilities;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public String getPicDisplay() {
		return picDisplay;
	}

	public void setPicDisplay(String picDisplay) {
		this.picDisplay = picDisplay;
	}

	public String getPetCarry() {
		return petCarry;
	}

	public void setPetCarry(String petCarry) {
		this.petCarry = petCarry;
	}

	public Date getHotelOpenTime() {
		return hotelOpenTime;
	}

	public void setHotelOpenTime(Date hotelOpenTime) {
		this.hotelOpenTime = hotelOpenTime;
	}

	public Date getHotelDecorationTime() {
		return hotelDecorationTime;
	}

	public void setHotelDecorationTime(Date hotelDecorationTime) {
		this.hotelDecorationTime = hotelDecorationTime;
	}

	public String getHotelOpenTimeStr() {
		if(StringUtils.isEmpty(hotelOpenTimeStr)   && hotelOpenTime!=null){
			hotelOpenTimeStr = DateUtil.formatDate(hotelOpenTime, "yyyy年MM月");
		}
		return hotelOpenTimeStr;
	}

	public void setHotelOpenTimeStr(String hotelOpenTimeStr) {
		this.hotelOpenTimeStr = hotelOpenTimeStr;
		if (StringUtils.isNotEmpty(hotelOpenTimeStr)) {
			hotelOpenTime = DateUtil.stringToDate(this.hotelOpenTimeStr,
					"yyyy年MM月");
		}
	}

	public String getHotelDecorationTimeStr() {
		if(StringUtils.isEmpty(hotelDecorationTimeStr)   && hotelDecorationTime!=null){
			hotelDecorationTimeStr = DateUtil.formatDate(hotelDecorationTime, "yyyy年MM月");
		}
		return hotelDecorationTimeStr;
	}

	public void setHotelDecorationTimeStr(String hotelDecorationTimeStr) {
		this.hotelDecorationTimeStr = hotelDecorationTimeStr;
		if (StringUtils.isNotEmpty(hotelDecorationTimeStr)) {
			hotelDecorationTime = DateUtil.stringToDate(this.hotelDecorationTimeStr,
					"yyyy年MM月");
		}
	}

}
