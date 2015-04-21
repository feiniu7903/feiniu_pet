package com.lvmama.comm.vo;

import java.io.Serializable;

import com.lvmama.comm.bee.po.prod.ProdHotel;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.ProdTicket;
import com.lvmama.comm.pet.po.place.Place;

public class ProdCProduct implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9203294192642710204L;
	/**
	 * 产品主信息
	 */
	private ProdProduct prodProduct;
 	private Place from;
	private Place to;
	
	/**
	 * 扩展门票信息
	 */
	private ProdTicket prodTicket;
	/**
	 * 扩展线路信息
	 */
	private ProdRoute prodRoute;
	/**
	 * 扩展酒店信息
	 * */
	private ProdHotel prodHotel;
	
	public ProdHotel getProdHotel() {
		return prodHotel;
	}
	public void setProdHotel(ProdHotel prodHotel) {
		this.prodHotel = prodHotel;
	}
	public ProdProduct getProdProduct() {
		return prodProduct;
	}
	public ProdTicket getProdTicket() {
		return prodTicket;
	}
	public ProdRoute getProdRoute() {
		return prodRoute;
	}
	public void setProdProduct(ProdProduct prodProduct) {
		this.prodProduct = prodProduct;
	}
	public void setProdTicket(ProdTicket prodTicket) {
		this.prodTicket = prodTicket;
	}
	public void setProdRoute(ProdRoute prodRoute) {
		this.prodRoute = prodRoute;
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
 
}
