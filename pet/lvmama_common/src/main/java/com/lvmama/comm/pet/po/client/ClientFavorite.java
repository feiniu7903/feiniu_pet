package com.lvmama.comm.pet.po.client;

import java.io.Serializable;

/**
 * @author yuzhibing
 *
 */
public class ClientFavorite implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 875354622306254650L;

	private Long favoriteId;

	private Long clientPlaceId;

	private String userId;
	
	private String name;
	
	private String type;

	private String tag;
	
	private Float sellPrice;
	
	private Float marketPrice;
	
	private String image;
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Float getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Float sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Float getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Float marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(Long favoriteId) {
		this.favoriteId = favoriteId;
	}
	
	public Long getClientPlaceId() {
		return clientPlaceId;
	}

	public void setClientPlaceId(Long clientPlaceId) {
		this.clientPlaceId = clientPlaceId;
	}

}
