package com.lvmama.comm.abroad.vo.response;

import java.util.List;

public class HotelDetailsRes extends ErrorRes {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8778253252274749075L;
	/**酒店基本信息********************************************/
	/**名称*/
	private String Name;
	/**地址*/
	private String Address;
	/**邮编*/
	private String PostalCode;
	/**城市id*/
	private String IDCity;
	/**城市*/
	private String City;
	/**省份（区）*/
	private String Province;
	/**国家*/
	private String Country;
	/**电话*/
	private String Telephone;
	/**传真*/
	private String Fax;
	/**描述*/
	private String Description;
	/**默认描述*/
	private String DescriptionDefaultGenerated;
	/**星级*/
	private String AccomCategory;
	/**星级描述*/
	private String AccomCategoryDesc;
	/**酒店类型ID*/
	private String IDAccomType;
	/**酒店类型*/
	private String AccomType;
	/**接受信用卡类型*/
	private String CreditCards;
	/**建筑年份*/
	private String BuildingConstructedYear;
	/**装修年份*/
	private String BuildingRenovatedYear;
	/**房间总数*/
	private String TotalRooms;
	/**楼层数*/
	private String TotalFloors;
	/**入住办理时间（从）*/
	private String CheckInFromTime;
	/**入住办理时间（至）*/
	private String CheckInToTime;
	/**退房办理时间（从）*/
	private String CheckOutFromTime;
	/**退房办理时间（至）*/
	private String CheckOutToTime;
	/**儿童年龄限制（从）*/
	private String ChildrenFromAge;
	/**儿童年龄限制（至）*/
	private String ChildrenToAge;
	
	/**地图信息**************************************/
	/**地图*/
	private String Map;
	
	/**UTM-x轴*/
	private String utmX;
	/**UTM-y轴*/
	private String utmY;
	/**UTM-方格主线距离*/
	private String utmZoom;
	/**纬度*/
	private String Latitude;
	/**经度*/
	private String Longitude;
	
	/**交通信息****************************************/
	/**所处区域*/
	private String AccomLocation;
	/**区域描述*/
	private String LocationDesc;
	/**路线*/
	private String RouteDesc;
	/**距离*/
	private List<String> Distance;
	/**周边地标*/
	private List<HotelDetailsDistance> Distances;
	
	/**详细服务信息*************************************************/
	/**服务*/
	private List<HotelDetailsService> Services;
	/**房型*/
	private List<HotelDetailsRoomType> RoomTypes;
	/**餐厅*/
	private List<HotelDetailsRestaurant> Restaurants;
	/**酒店大厅*/
	private List<HotelDetailsLounge> Lounges;
	/**图片*/
	private List<HotelDetailsImage> images;
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getPostalCode() {
		return PostalCode;
	}
	public void setPostalCode(String postalCode) {
		PostalCode = postalCode;
	}
	public String getIDCity() {
		return IDCity;
	}
	public void setIDCity(String iDCity) {
		IDCity = iDCity;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getProvince() {
		return Province;
	}
	public void setProvince(String province) {
		Province = province;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public String getTelephone() {
		return Telephone;
	}
	public void setTelephone(String telephone) {
		Telephone = telephone;
	}
	public String getFax() {
		return Fax;
	}
	public void setFax(String fax) {
		Fax = fax;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getDescriptionDefaultGenerated() {
		return DescriptionDefaultGenerated;
	}
	public void setDescriptionDefaultGenerated(String descriptionDefaultGenerated) {
		DescriptionDefaultGenerated = descriptionDefaultGenerated;
	}
	public String getAccomCategory() {
		return AccomCategory;
	}
	public void setAccomCategory(String accomCategory) {
		AccomCategory = accomCategory;
	}
	public String getAccomCategoryDesc() {
		return AccomCategoryDesc;
	}
	public void setAccomCategoryDesc(String accomCategoryDesc) {
		AccomCategoryDesc = accomCategoryDesc;
	}
	public String getIDAccomType() {
		return IDAccomType;
	}
	public void setIDAccomType(String iDAccomType) {
		IDAccomType = iDAccomType;
	}
	public String getAccomType() {
		return AccomType;
	}
	public void setAccomType(String accomType) {
		AccomType = accomType;
	}
	public String getCreditCards() {
		return CreditCards;
	}
	public void setCreditCards(String creditCards) {
		CreditCards = creditCards;
	}
	public String getBuildingConstructedYear() {
		return BuildingConstructedYear;
	}
	public void setBuildingConstructedYear(String buildingConstructedYear) {
		BuildingConstructedYear = buildingConstructedYear;
	}
	public String getBuildingRenovatedYear() {
		return BuildingRenovatedYear;
	}
	public void setBuildingRenovatedYear(String buildingRenovatedYear) {
		BuildingRenovatedYear = buildingRenovatedYear;
	}
	public String getTotalRooms() {
		return TotalRooms;
	}
	public void setTotalRooms(String totalRooms) {
		TotalRooms = totalRooms;
	}
	public String getTotalFloors() {
		return TotalFloors;
	}
	public void setTotalFloors(String totalFloors) {
		TotalFloors = totalFloors;
	}
	public String getCheckInFromTime() {
		return CheckInFromTime;
	}
	public void setCheckInFromTime(String checkInFromTime) {
		CheckInFromTime = checkInFromTime;
	}
	public String getCheckInToTime() {
		return CheckInToTime;
	}
	public void setCheckInToTime(String checkInToTime) {
		CheckInToTime = checkInToTime;
	}
	public String getCheckOutFromTime() {
		return CheckOutFromTime;
	}
	public void setCheckOutFromTime(String checkOutFromTime) {
		CheckOutFromTime = checkOutFromTime;
	}
	public String getCheckOutToTime() {
		return CheckOutToTime;
	}
	public void setCheckOutToTime(String checkOutToTime) {
		CheckOutToTime = checkOutToTime;
	}
	public String getChildrenFromAge() {
		return ChildrenFromAge;
	}
	public void setChildrenFromAge(String childrenFromAge) {
		ChildrenFromAge = childrenFromAge;
	}
	public String getChildrenToAge() {
		return ChildrenToAge;
	}
	public void setChildrenToAge(String childrenToAge) {
		ChildrenToAge = childrenToAge;
	}
	public String getMap() {
		return Map;
	}
	public void setMap(String map) {
		Map = map;
	}

	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getAccomLocation() {
		return AccomLocation;
	}
	public void setAccomLocation(String accomLocation) {
		AccomLocation = accomLocation;
	}
	public String getLocationDesc() {
		return LocationDesc;
	}
	public void setLocationDesc(String locationDesc) {
		LocationDesc = locationDesc;
	}
	public String getRouteDesc() {
		return RouteDesc;
	}
	public void setRouteDesc(String routeDesc) {
		RouteDesc = routeDesc;
	}
	public List<String> getDistance() {
		return Distance;
	}
	public void setDistance(List<String> distance) {
		Distance = distance;
	}
	public List<HotelDetailsDistance> getDistances() {
		return Distances;
	}
	public void setDistances(List<HotelDetailsDistance> distances) {
		Distances = distances;
	}
	public List<HotelDetailsService> getServices() {
		return Services;
	}
	public void setServices(List<HotelDetailsService> services) {
		Services = services;
	}
	public List<HotelDetailsRoomType> getRoomTypes() {
		return RoomTypes;
	}
	public void setRoomTypes(List<HotelDetailsRoomType> roomTypes) {
		RoomTypes = roomTypes;
	}
	public List<HotelDetailsRestaurant> getRestaurants() {
		return Restaurants;
	}
	public void setRestaurants(List<HotelDetailsRestaurant> restaurants) {
		Restaurants = restaurants;
	}
	public List<HotelDetailsLounge> getLounges() {
		return Lounges;
	}
	public void setLounges(List<HotelDetailsLounge> lounges) {
		Lounges = lounges;
	}
	public List<HotelDetailsImage> getImages() {
		return images;
	}
	public void setImages(List<HotelDetailsImage> images) {
		this.images = images;
	}
	public String getUtmX() {
		return utmX;
	}
	public void setUtmX(String utmX) {
		this.utmX = utmX;
	}
	public String getUtmY() {
		return utmY;
	}
	public void setUtmY(String utmY) {
		this.utmY = utmY;
	}
	public String getUtmZoom() {
		return utmZoom;
	}
	public void setUtmZoom(String utmZoom) {
		this.utmZoom = utmZoom;
	}
	
}
