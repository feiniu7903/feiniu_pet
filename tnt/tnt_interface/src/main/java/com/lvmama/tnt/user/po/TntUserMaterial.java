package com.lvmama.tnt.user.po;

/**
 * 分销商资料
 * 
 * @author gaoxin
 * 
 */
public class TntUserMaterial implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.Long materialId;
	private java.lang.String materialType;
	private java.lang.String materialName;
	private java.lang.String materialUrll;

	private java.lang.Long userId;
	private java.lang.String materialStatus;

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	private String failReason;

	public TntUserMaterial() {
	}

	public TntUserMaterial(java.lang.Long materialId) {
		this.materialId = materialId;
	}

	public void setMaterialId(java.lang.Long value) {
		this.materialId = value;
	}

	public java.lang.Long getMaterialId() {
		return this.materialId;
	}

	public void setMaterialType(java.lang.String value) {
		this.materialType = value;
	}

	public java.lang.String getMaterialType() {
		return this.materialType;
	}

	public void setMaterialName(java.lang.String value) {
		this.materialName = value;
	}

	public java.lang.String getMaterialName() {
		return this.materialName;
	}

	public void setMaterialUrll(java.lang.String value) {
		this.materialUrll = value;
	}

	public java.lang.String getMaterialUrll() {
		return this.materialUrll;
	}

	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}

	public java.lang.Long getUserId() {
		return this.userId;
	}

	public void setMaterialStatus(java.lang.String value) {
		this.materialStatus = value;
	}

	public java.lang.String getMaterialStatus() {
		return this.materialStatus;
	}

	@Override
	public String toString() {
		return "TntUserMaterial [materialId=" + materialId + ", materialType="
				+ materialType + ", materialName=" + materialName
				+ ", materialUrll=" + materialUrll + ", userId=" + userId
				+ ", materialStatus=" + materialStatus + "]";
	}

}
