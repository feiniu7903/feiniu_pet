package com.lvmama.comm.pet.po.place;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class PlacePhoto implements Serializable {
	private static final long serialVersionUID = -8760347440454000567L;

	private Long placePhotoId;

	private Long placeId;

	private String imagesUrl;

	private File imagePath;
	private String imagePathContentType;
	private String imagePathFileName;	
	
	private String type;

	private Date createTime;

	private Long seq;

	private String placePhotoName;
	private String placePhotoContext;
	private String placePhotoDisplay;
	private String placePhotoType;
	
	public PlacePhoto() {

	}

	public String getImagePathContentType() {
		return imagePathContentType;
	}

	public void setImagePathContentType(String imagePathContentType) {
		this.imagePathContentType = imagePathContentType;
	}

	public String getImagePathFileName() {
		return imagePathFileName;
	}

	public void setImagePathFileName(String imagePathFileName) {
		this.imagePathFileName = imagePathFileName;
	}

	public File getImagePath() {
		return imagePath;
	}

	public void setImagePath(File imagePath) {
		this.imagePath = imagePath;
	}

	public PlacePhoto(Long placeId) {
		this.placeId = placeId;
	}

	public Long getPlacePhotoId() {
		return placePhotoId;
	}

	public void setPlacePhotoId(Long placePhotoId) {
		this.placePhotoId = placePhotoId;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public String getImagesUrl() {
		return imagesUrl;
	}
	public String getAbsoluteImageUrl(){
		return "http://pic.lvmama.com"+this.imagesUrl;
	}

	public void setImagesUrl(String imagesUrl) {
		this.imagesUrl = imagesUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getPlacePhotoName() {
		return placePhotoName;
	}

	public void setPlacePhotoName(String placePhotoName) {
		this.placePhotoName = placePhotoName;
	}

	public String getPlacePhotoContext() {
		return placePhotoContext;
	}

	public void setPlacePhotoContext(String placePhotoContext) {
		this.placePhotoContext = placePhotoContext;
	}

	public String getPlacePhotoDisplay() {
		return placePhotoDisplay;
	}

	public void setPlacePhotoDisplay(String placePhotoDisplay) {
		this.placePhotoDisplay = placePhotoDisplay;
	}

	public String getPlacePhotoType() {
		return placePhotoType;
	}

	public void setPlacePhotoType(String placePhotoType) {
		this.placePhotoType = placePhotoType;
	}
	
}