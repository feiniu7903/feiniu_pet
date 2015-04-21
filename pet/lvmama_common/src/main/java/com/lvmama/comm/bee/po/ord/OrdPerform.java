package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

public class OrdPerform implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2459189319523092756L;
	
	private Long performId;
	
	private Long performTargetId;
	
	private Long objectId;
	
	private String objectType;
	
	private Long adultQuantity;//组成该采购产品的成人数量（对于单件采购产品来说）
	
	private Long childQuantity;//组成该采购产品的儿童数量（对于单件采购产品来说）
	
	private Date createTime;

	private String memo;

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public Long getPerformId() {
		return performId;
	}

	public void setPerformId(Long performId) {
		this.performId = performId;
	}
	
	public Long getPerformTargetId() {
		return performTargetId;
	}

	public void setPerformTargetId(Long performTargetId) {
		this.performTargetId = performTargetId;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public Long getAdultQuantity() {
		return adultQuantity;
	}

	public void setAdultQuantity(Long adultQuantity) {
		this.adultQuantity = adultQuantity;
	}

	public Long getChildQuantity() {
		return childQuantity;
	}

	public void setChildQuantity(Long childQuantity) {
		this.childQuantity = childQuantity;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
