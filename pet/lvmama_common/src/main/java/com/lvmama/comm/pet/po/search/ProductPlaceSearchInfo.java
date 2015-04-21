package com.lvmama.comm.pet.po.search;

import java.io.Serializable;

/**
 * 景点，门票搜索
 * @author liukang
 *
 */
public class ProductPlaceSearchInfo implements Serializable {
	private static final long serialVersionUID = 7521103858145539667L;
	private Long placeId;
	private Long proudtId;
	private String province;
	private String city;
	private String placeName;
	private String placeAddress;
	private String placePinYinUrl;
	private String ticketsName;
	private String ticketsAttr;
	private Long ticketsPrice;
	private Long ticketsNowPrice;
	private String ticketsContent;
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getPlaceAddress() {
		return placeAddress;
	}
	public void setPlaceAddress(String placeAddress) {
		this.placeAddress = placeAddress;
	}
	public String getPlacePinYinUrl() {
		return placePinYinUrl;
	}
	public void setPlacePinYinUrl(String placePinYinUrl) {
		this.placePinYinUrl = placePinYinUrl;
	}
	public String getTicketsName() {
		return ticketsName;
	}
	public void setTicketsName(String ticketsName) {
		this.ticketsName = ticketsName;
	}
	public String getTicketsAttr() {
		return ticketsAttr;
	}
	public void setTicketsAttr(String ticketsAttr) {
		this.ticketsAttr = ticketsAttr;
	}
	public Long getTicketsPrice() {
		return ticketsPrice;
	}
	public void setTicketsPrice(Long ticketsPrice) {
		this.ticketsPrice = ticketsPrice;
	}
	public Long getTicketsNowPrice() {
		return ticketsNowPrice;
	}
	public void setTicketsNowPrice(Long ticketsNowPrice) {
		this.ticketsNowPrice = ticketsNowPrice;
	}
	public String getTicketsContent() {
		return ticketsContent;
	}
	public void setTicketsContent(String ticketsContent) {
		this.ticketsContent = ticketsContent;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public Long getProudtId() {
		return proudtId;
	}
	public void setProudtId(Long proudtId) {
		this.proudtId = proudtId;
	}

	}
