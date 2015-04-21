package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;

import com.lvmama.comm.vo.Constant;

public class ComPicture implements Serializable {
	private static final long serialVersionUID = -8745132242956830207L;

	private Long pictureId;

	private Long pictureObjectId;

	private String pictureName;

	private String pictureObjectType;

	private String pictureUrl;
	private Boolean isNew;// 标识图片是否是新建的
	private Integer seq;

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
	
	public String getAbsolute580x290Url() {
		return Constant.getInstance().getPrifix580x290Pic() + pictureUrl;
	}
	
	public String getAbsoluteUrl() {
		return Constant.getInstance().getPrefixPic() + pictureUrl;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Boolean getIsNew() {
		if (this.isNew == null) {
			this.isNew = false;
		}
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	/**
	 * @return the seq
	 */
	public Integer getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	/**
	 * 图片关联对象类型
	 * @author taiqichao
	 *
	 */
	public enum PICTURE_OBJECT_TYPE{
		VIEW_PAGE,VIEW_JOURNEY,GROUP_DREAM
	}
	
}