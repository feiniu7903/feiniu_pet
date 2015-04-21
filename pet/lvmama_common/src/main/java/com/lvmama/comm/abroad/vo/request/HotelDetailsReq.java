package com.lvmama.comm.abroad.vo.request;

import java.io.Serializable;

public class HotelDetailsReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2503876024398189594L;
	/**
	 * 酒店id
	 */
	private String IDhotel;
	private String Language;
	public String getIDhotel() {
		return IDhotel;
	}
	/**
	 * 设置IDHotel，必填
	 * @param iDhotel
	 */
	public void setIDhotel(String iDhotel) {
		IDhotel = iDhotel;
	}
	public String getLanguage() {
		return Language;
	}
	/**
	 * 语言
	 * @param language
	 */
	public void setLanguage(String language) {
		Language = language;
	}
}
