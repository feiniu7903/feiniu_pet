package com.lvmama.comm.abroad.vo.response;

import java.util.List;

public class HotelRes extends ErrorRes {
	private static final long serialVersionUID = 1L;
	private List<HotelBean> hotels;
	public List<HotelBean> getHotels() {
		return hotels;
	}
	public void setHotels(List<HotelBean> hotels) {
		this.hotels = hotels;
	}
	

}
