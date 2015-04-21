package com.lvmama.comm.vo.comment;

import com.lvmama.comm.vo.Constant;

public class CmtPlaceTitleStatisticsVO extends CmtTitleStatisticsVO {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = 8706693739780696578L;
	/**
	 * 景区图片路径
	 */
	private String placeSmallImage;
	/**
	 * 省份
	 */
	private String provice;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 景区拼音名字
	 */
	private String pinYin;
	/**
	 * 带逻辑的拼音URL
	 */
	private String pinYinUrl;
	
	private String placeLargeImage;
	
	public String getPlaceSmallImgUrl() {
		return Constant.getInstance().getPrefixPic() + getPlaceSmallImage();
	}
	
	/**
	 *  ----------------------  get and set property -------------------------------
	 */
	public String getProvice() {
		return provice;
	}
	public void setProvice(final String provice) {
		this.provice = provice;
	}
	public String getCity() {
		return city;
	}
	public void setCity(final String city) {
		this.city = city;
	}

	public String getPinYin() {
		return pinYin;
	}

	public void setPinYin(final String pinYin) {
		this.pinYin = pinYin;
	}

	public String getPinYinUrl() {
		return pinYinUrl;
	}

	public void setPinYinUrl(final String pinYinUrl) {
		this.pinYinUrl = pinYinUrl;
	}

	public String getPlaceSmallImage() {
		return placeSmallImage;
	}

	public void setPlaceSmallImage(String placeSmallImage) {
		this.placeSmallImage = placeSmallImage;
	}

	public String getPlaceLargeImage() {
		return placeLargeImage;
	}

	public void setPlaceLargeImage(String placeLargeImage) {
		this.placeLargeImage = placeLargeImage;
	}
	
}
