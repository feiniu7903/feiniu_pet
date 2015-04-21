package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;
import java.util.List;

public class AvailAccomAdvancedRes extends ErrorRes implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6545774597192941754L;
	private List<AvailAccomAdvancedHotelInfo> hotelInfo;
	private int hotelCount;
	/**
	 * 酒店数量
	 * @return
	 */
	public int getHotelCount() {
		return hotelCount;
	}
	public void setHotelCount(int hotelCount) {
		this.hotelCount = hotelCount;
	}
	/**
	 * 酒店详情
	 * @return
	 */
	public List<AvailAccomAdvancedHotelInfo> getHotelInfo() {
		return hotelInfo;
	}
	public void setHotelInfo(List<AvailAccomAdvancedHotelInfo> hotelInfo) {
		this.hotelInfo = hotelInfo;
	}
}
