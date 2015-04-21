package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProdProductTag implements Serializable {
	private static final long serialVersionUID = -8040701070363194884L;

	private Long productTagId;

	private Long productId;

	private Long tagId;

	private Date beginTime;

	private Date endTime;

	private String productName;

	private String productType;

	private String tagGroupName;

	private String tagName;

	private Boolean checked;
	
	//hashCode--下面的方法后面注意再想下  
	//PROD_PRODUCT_TAG_CREATOR.SYSTEM
	private String creator;//创建者
	
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public ProdProductTag() {	
	}
	
	public ProdProductTag(Long productTagId) {
		this.productTagId = productTagId;
	}
	
	public ProdProductTag(Long productTagId, Long productId, Long tagId) {
		this.productTagId = productTagId;
		this.productId = productId;
		this.tagId = tagId;
	}

	public Long getProductTagId() {
		return productTagId;
	}

	public void setProductTagId(Long productTagId) {
		this.productTagId = productTagId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getTagGroupName() {
		return tagGroupName;
	}

	public void setTagGroupName(String tagGroupName) {
		this.tagGroupName = tagGroupName;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getStrBeginTime() {
		return beginTime == null ? "" : sdf.format(beginTime);
	}

	public String getStrEndTime() {
		return endTime == null ? "" : sdf.format(endTime);
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((productTagId == null) ? 0 : productTagId.hashCode());
		result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdProductTag other = (ProdProductTag) obj;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (productTagId == null) {
			if (other.productTagId != null)
				return false;
		} else if (!productTagId.equals(other.productTagId))
			return false;
		if (tagId == null) {
			if (other.tagId != null)
				return false;
		} else if (!tagId.equals(other.tagId))
			return false;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		
		return true;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	
}