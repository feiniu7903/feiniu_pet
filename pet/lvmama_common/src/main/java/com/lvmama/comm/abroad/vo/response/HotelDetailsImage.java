package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;
public class HotelDetailsImage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1379022396402334961L;
	private String ImageTitle;
	private String ImageDesc;
	private String ImagePath;
	private String IsMainImage;
	public String getImageTitle() {
		return ImageTitle;
	}
	public void setImageTitle(String imageTitle) {
		ImageTitle = imageTitle;
	}
	public String getImageDesc() {
		return ImageDesc;
	}
	public void setImageDesc(String imageDesc) {
		ImageDesc = imageDesc;
	}
	public String getImagePath() {
		return ImagePath;
	}
	public void setImagePath(String imagePath) {
		ImagePath = imagePath;
	}
	public String getIsMainImage() {
		return IsMainImage;
	}
	public void setIsMainImage(String isMainImage) {
		IsMainImage = isMainImage;
	}
}
