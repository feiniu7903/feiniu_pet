package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
import java.util.List;

import com.lvmama.comm.pet.po.place.Place;

public class RouteProduct implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7140964066375560758L;

	private ProdProduct prodProduct;
	
	private List<TimePrice> timePriceList;

	private boolean resourceConfirm = false;
	
	private Place from;
	private Place to;
	
	public ProdProduct getProdProduct() {
		return prodProduct;
	}
	public void setProdProduct(ProdProduct prodProduct) {
		this.prodProduct = prodProduct;
	}
	public List<TimePrice> getTimePriceList() {
		return timePriceList;
	}
	public void setTimePriceList(List<TimePrice> timePriceList) {
		this.timePriceList = timePriceList;
	}
	public Place getFrom() {
		return from;
	}
	public void setFrom(Place from) {
		this.from = from;
	}
	public Place getTo() {
		return to;
	}
	public void setTo(Place to) {
		this.to = to;
	}
	public boolean isResourceConfirm() {
		return resourceConfirm;
	}
	public void setResourceConfirm(boolean resourceConfirm) {
		this.resourceConfirm = resourceConfirm;
	}

}
