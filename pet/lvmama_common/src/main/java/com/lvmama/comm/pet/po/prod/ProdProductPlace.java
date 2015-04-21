package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;
import java.util.List;

import com.lvmama.comm.pet.po.place.PlacePlaceDest;

/**
 * 销售产品景点
 * @author MrZhu
 *
 */
public class ProdProductPlace implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4529526210102451068L;

	private Long productPlaceId;
	
	private Long productId;
	private Long placeId;
	
	private String from;
	private String to;
	private String defaultTo;
	private Long destId;
	public Long getDestId() {
		return destId;
	}

	public void setDestId(Long destId) {
		this.destId = destId;
	}

	private String placeName;
	
	private List<PlacePlaceDest> parentPlaceIdList;
	


	public List<PlacePlaceDest> getParentPlaceIdList() {
		return parentPlaceIdList;
	}

	public void setParentPlaceIdList(List<PlacePlaceDest> parentPlaceIdList) {
		this.parentPlaceIdList = parentPlaceIdList;
	}

	public Long getProductPlaceId() {
		return productPlaceId;
	}

	public void setProductPlaceId(Long productPlaceId) {
		this.productPlaceId = productPlaceId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getDefaultTo() {
		return defaultTo;
	}

	public void setDefaultTo(String defaultTo) {
		this.defaultTo = defaultTo;
	}


}
