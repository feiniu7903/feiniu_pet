package com.lvmama.comm.pet.vo;

import java.io.Serializable;

import com.lvmama.comm.pet.po.place.Place;

public class PlaceVo  implements Serializable{
	private static final long serialVersionUID = -5341886404300639437L;
	/**
	 * 目的地层级关系
	 */
	private String placeSuperior;
	private Place place;
	/**
	 * 上级目的地
	 */
	private Place parentPlace;
	
	public String getPlaceSuperior() {
		return placeSuperior;
	}
	public void setPlaceSuperior(String placeSuperior) {
		this.placeSuperior = placeSuperior;
	}
	public Place getPlace() {
		return place;
	}
	public void setPlace(Place place) {
		this.place = place;
	}
	public Place getParentPlace() {
		return parentPlace;
	}
	public void setParentPlace(Place parentPlace) {
		this.parentPlace = parentPlace;
	}
	
	
}