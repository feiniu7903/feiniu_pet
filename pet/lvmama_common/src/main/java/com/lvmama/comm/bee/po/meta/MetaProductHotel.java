package com.lvmama.comm.bee.po.meta;

import com.lvmama.comm.vo.Constant;

public class MetaProductHotel extends MetaProduct {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3291219828997850210L;

	private Long metaHotelId;
	
	private Long nights;//酒店晚数

	public Long getMetaHotelId() {
		return metaHotelId;
	}

	public void setMetaHotelId(Long metaHotelId) {
		this.metaHotelId = metaHotelId;
	}

	public String getProductType() {
		return Constant.PRODUCT_TYPE.HOTEL.name();
	}
	/**
	 * 酒店关联标的
	 */
	public boolean isRelatePlace() {
		return true;
	}

	/**
	 * @return the nights
	 */
	public Long getNights() {
		return nights==null||Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(getSubProductType())?1L:nights;//默认为1晚
	}

	/**
	 * @param nights the nights to set
	 */
	public void setNights(Long nights) {
		this.nights = nights;				
	}
	
	
}