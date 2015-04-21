package com.lvmama.distribution.model.jd;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.distribution.util.JdUtil;
import com.lvmama.comm.pet.po.place.Place;
/**
 * 供应商
 * 资源  报文
 * @author gaoxin
 *
 */
public class Resource {
	private String resourceId;// 供应商的资源编号
	private String name;// 资源名称
	private String categoryIds;// 资源类型
	private String level;// 资源级别
	private String telephone;// 资源电话
	private String provinceName;// 省名字
	private String cityName;// 城市名字
	private String areaName;// 城市下级区域名
	private String address;// 资源地址
	private String briefIntr;// 资源的简单介绍
	private String description;// 资源的介绍
	private String openTime;// 营业时间
	private String traffic;// 资源的交通路线
	private String liveInfo;// 住宿信息
	private String qualification;// 景区资质
	private String touristSeason;// 最佳旅游季节
	private String guestPrompt;// 景区的客户须知
	private String feature;// 扩展字段,备用
	private List<String> picList=new ArrayList<String>(); //图片列表
	private Place place;
	
	public Resource(){}
	public Resource(Place place){
		this.place=place;
		this.resourceId=place.getPlaceId()+"";
		this.picList.add(place.getLargeImageUrl());
		this.picList.add(place.getSmallImageUrl());
	}
	
	
	
	public String buildResourceToXml(){
		StringBuilder sb=new StringBuilder();
		sb.append("<resource>")
		.append(JdUtil.buildXmlElement("resourceId", place.getPlaceId()))
		.append(JdUtil.buildXmlElement("name",place.getName()))
		.append(JdUtil.buildXmlElementInCheck("categoryIds", categoryIds))
		.append(JdUtil.buildXmlElementInCheck("level", level))
		.append(JdUtil.buildXmlElementInCheck("telephone", place.getPhone()))
		.append(JdUtil.buildXmlElementInCheck("provinceName", place.getProvince()))
		.append(JdUtil.buildXmlElement("cityName", place.getCity()))
		.append(JdUtil.buildXmlElementInCheck("areaName", areaName))			
		.append(JdUtil.buildXmlElement("address", place.getAddress()))
		.append(JdUtil.buildXmlElementInCheck("briefIntr", briefIntr))
		.append(JdUtil.buildXmlElementInCDATA("description", place.getDescription()))
		.append(JdUtil.buildXmlElementInCheck("openTime", openTime))
		.append(JdUtil.buildXmlElementInCheck("traffic", traffic))
		.append(JdUtil.buildXmlElementInCheck("liveInfo", liveInfo))
		.append(JdUtil.buildXmlElementInCheck("qualification", qualification))
		.append(JdUtil.buildXmlElementInCheck("touristSeason", touristSeason))
		.append(JdUtil.buildXmlElementInCheck("guestPrompt", guestPrompt))
		.append(JdUtil.buildXmlElementInCheck("feature", feature));
		if(picList.size()>0){
			sb.append("<picList>");
			for(String url : picList){
				sb.append(JdUtil.buildXmlElementInCheck("url", url));
			}
			sb.append("</picList>");
		}
		sb.append("</resource>");
		return sb.toString();
	}
	/**
	 *构造  更新景区  图片上传 报文
	 * @return
	 */
	public String buildUpdateResourceToXml() {
		StringBuilder sb=new StringBuilder();
		sb.append("<resource>")
		.append(JdUtil.buildXmlElement("resourceId", place.getPlaceId()))
		.append(JdUtil.buildXmlElementInCheck("briefIntr", briefIntr))
		.append(JdUtil.buildXmlElementInCDATA("description", place.getDescription()))
		.append(JdUtil.buildXmlElementInCheck("traffic", traffic))
		.append(JdUtil.buildXmlElementInCheck("liveInfo", liveInfo))
		.append(JdUtil.buildXmlElementInCheck("guestPrompt", guestPrompt))
		.append(JdUtil.buildXmlElementInCheck("feature", feature));
		/*if(picList.size()>0){
			sb.append("<picList>");
			for(String url : picList){
				sb.append(JdUtil.buildXmlElementInCheck("url", url));
			}
			sb.append("</picList>");
		}*/
		sb.append("</resource>");
		return sb.toString();
	}
	
	
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategoryIds() {
		return categoryIds;
	}
	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBriefIntr() {
		return briefIntr;
	}
	public void setBriefIntr(String briefIntr) {
		this.briefIntr = briefIntr;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getTraffic() {
		return traffic;
	}
	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}
	public String getLiveInfo() {
		return liveInfo;
	}
	public void setLiveInfo(String liveInfo) {
		this.liveInfo = liveInfo;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getTouristSeason() {
		return touristSeason;
	}
	public void setTouristSeason(String touristSeason) {
		this.touristSeason = touristSeason;
	}
	public String getGuestPrompt() {
		return guestPrompt;
	}
	public void setGuestPrompt(String guestPrompt) {
		this.guestPrompt = guestPrompt;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public List<String> getPicList() {
		return picList;
	}



	public void setPicList(List<String> picList) {
		this.picList = picList;
	}
	 
	
}
