package com.lvmama.comm.abroad.vo.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class AvailAccomAdvancedReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3865186981960836628L;
	/**国家id*/
	private String countryId;
	/**城市id*/
	private String cityId;
	/**酒店名称*/
	private String hotel;
	/**酒店Id*/
	private String hotelId;
	/**入住日期*/
	private Date checkIn;
	/**离开日期*/
	private Date checkOut;
	/**住宿类型*/
	private String accomType;
	/**主题类型*/
	private String accomProdType;
	/**房间*/
	private List<AvailAccomAdvancedRoom> rooms;
	/**星级*/
	private List<String> stars;
	/**价格区间*/
	private List<int[]> prices;
	/**排序类型*/
	private String sortType;
	public String getCountryId() {
		return countryId;
	}
	/**
	 * 国家id
	 * @param countryId
	 */
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getCityId() {
		return cityId;
	}
	/**
	 * 城市Id
	 * @param cityId
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getHotel() {
		return hotel;
	}
	/**
	 * 酒店名称
	 * <li><strong><font color="red">必需传入三位或以上有效过滤字符</font></strong></li>
	 * @param hotel
	 */
	public void setHotel(String hotel) {
		this.hotel = hotel;
	}
	public Date getCheckIn() {
		return checkIn;
	}
	/**
	 * 入住日期.必填
	 * @param checkIn
	 */
	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}
	public Date getCheckOut() {
		return checkOut;
	}
	/**
	 * 离开日期.必填
	 * @param checkOut
	 */
	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}
	public List<String> getStars() {
		return stars;
	}
	/**
	 * 星级
	 * @param stars
	 */
	public void setStars(List<String> stars) {
		this.stars = stars;
	}
	public List<int[]> getPrices() {
		return prices;
	}
	/**
	 * 价格范围.选填
	 * 价格数组格式[min,max]或者[min]
	 * @param prices
	 */
	public void setPrices(List<int[]> prices) {
		this.prices = prices;
	}
	public String getSortType() {
		return sortType;
	}
	/**
	 * 排序类型（<p>Category星级升序，CategoryDesc星级降序
	 * <p>Price价格升序，PriceDesc价格降序）
	 * @param sortType
	 */
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public List<AvailAccomAdvancedRoom> getRooms() {
		return rooms;
	}
	/**
	 * 房间
	 * @param rooms
	 */
	public void setRooms(List<AvailAccomAdvancedRoom> rooms) {
		this.rooms = rooms;
	}
	public String getHotelId() {
		return hotelId;
	}
	/**
	 * 酒店Id
	 * @param hotelId
	 */
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	public String getAccomType() {
		return accomType;
	}
	public void setAccomType(String accomType) {
		this.accomType = accomType;
	}
	public String getAccomProdType() {
		return accomProdType;
	}
	public void setAccomProdType(String accomProdType) {
		this.accomProdType = accomProdType;
	}
}
