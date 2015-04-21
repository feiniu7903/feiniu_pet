package com.lvmama.front.dto;

import java.io.Serializable;

import com.lvmama.comm.bee.po.prod.ProdProduct;

public  final class CalendarInfo implements Serializable{
	private ProdProduct product;
	public ProdProduct getProduct(){
		return this.product;
	}
	private float latestPrice;
	public float getLatestPrice(){
		return latestPrice;
	}
	public void setProduct(ProdProduct product){
		this.product=product;
	}
}
