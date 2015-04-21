package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;
import java.util.List;
public class AvailAccomAdvancedHotelInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1931270245925749318L;
	private String ID;
	private String IDGroup;
	private String Name;
	private String HotelRemarks;
	private String ProdType;
	private String IDCategory;
	private String Category;
	private String IDAccomType;
	private String Description;
	private String Image;
	private String Map;
	
	private String IDCity;
	private String City;
	private String Latitude;
	private String Longitude;
	private String AvailStatus;
	private Long TotalPrice;
	private Long Supplements;
	private String Currency;
	private String IsOffer;
	private List<AvailAccomAdvancedRoomInfo> rooms;
	/**
	 * 酒店id
	 * @return
	 */
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	/**
	 * 酒店集团id
	 * @return
	 */
	public String getIDGroup() {
		return IDGroup;
	}

	public void setIDGroup(String iDGroup) {
		IDGroup = iDGroup;
	}
	/**
	 * 名称
	 * @return
	 */
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	/**
	 * 备注
	 * @return
	 */
	public String getHotelRemarks() {
		return HotelRemarks;
	}

	public void setHotelRemarks(String hotelRemarks) {
		HotelRemarks = hotelRemarks;
	}
	/**
	 * 居住类型
	 * @return
	 */
	public String getProdType() {
		return ProdType;
	}

	public void setProdType(String prodType) {
		ProdType = prodType;
	}
/**
 * 星级id
 * @return
 */
	public String getIDCategory() {
		return IDCategory;
	}

	public void setIDCategory(String iDCategory) {
		IDCategory = iDCategory;
	}
/**
 * 星级
 * @return
 */
	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}
/**
 * 住宿类型
 * @return
 */
	public String getIDAccomType() {
		return IDAccomType;
	}

	public void setIDAccomType(String iDAccomType) {
		IDAccomType = iDAccomType;
	}
/**
 * 描述
 * @return
 */
	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
/**
 * 图片
 * @return
 */
	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}
/**
 * 地图
 * @return
 */
	public String getMap() {
		return Map;
	}

	public void setMap(String map) {
		Map = map;
	}
/**
 * 城市id
 * @return
 */
	public String getIDCity() {
		return IDCity;
	}

	public void setIDCity(String iDCity) {
		IDCity = iDCity;
	}
/**
 * 城市
 * @return
 */
	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}
/**
 * 地理纬度
 * @return
 */
	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
/**地理经度
 * @return
 */
	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
/**
 * 当前状态<p>
 * shows the total availability of the reservation for each room with the following values:
 *	<p>“A" for available, “R” for on request, and “X” indicates if one or two days are on request and the
 *<p>	rest are available. The GetRoomDetails command allows us to obtain the days available and the
 *<p>	days which are on request
 * @return
 */
	public String getAvailStatus() {
		return AvailStatus;
	}

	public void setAvailStatus(String availStatus) {
		AvailStatus = availStatus;
	}
/**
 * 预订价格
 *<p> shows the final price of the reservation including Supplements and/or Discounts.
*<p>This tag shows the amount of all the pre-selected rooms on the requested reservation (Quantity > 0).
 * 
 * @return
 */
	public Long getTotalPrice() {
		return TotalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		TotalPrice = totalPrice;
	}
/**
 * 附加费用
 * <p>shows the total amount of supplements of a reservation based on the pre-selected
	<p>rooms. This tag is used as a guide.
 * @return
 */
	public Long getSupplements() {
		return Supplements;
	}

	public void setSupplements(Long supplements) {
		Supplements = supplements;
	}
/**
 * 币种
 * @return
 */
	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}
/**
 * 
 * @return
 */
	public String getIsOffer() {
		return IsOffer;
	}

	public void setIsOffer(String isOffer) {
		IsOffer = isOffer;
	}
	/**
	 * 房间信息
	 * @return
	 */
	public List<AvailAccomAdvancedRoomInfo> getRooms() {
		return rooms;
	}

	public void setRooms(List<AvailAccomAdvancedRoomInfo> rooms) {
		this.rooms = rooms;
	}
}
