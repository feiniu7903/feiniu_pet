package com.lvmama.comm.pet.po.client;

public class ClientPicture {

	private Long pictureId;

	private Long pictureObjectId;

	private String pictureName;

	private String pictureObjectType;

	private String pictureUrl;
	private Boolean isNew;// 标识图片是否是新建的
	
	public Long getPictureId() {
		return pictureId;
	}
	public void setPictureId(Long pictureId) {
		this.pictureId = pictureId;
	}
	public Long getPictureObjectId() {
		return pictureObjectId;
	}
	public void setPictureObjectId(Long pictureObjectId) {
		this.pictureObjectId = pictureObjectId;
	}
	public String getPictureName() {
		return pictureName;
	}
	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}
	public String getPictureObjectType() {
		return pictureObjectType;
	}
	public void setPictureObjectType(String pictureObjectType) {
		this.pictureObjectType = pictureObjectType;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public Boolean getIsNew() {
		return isNew;
	}
	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
}
