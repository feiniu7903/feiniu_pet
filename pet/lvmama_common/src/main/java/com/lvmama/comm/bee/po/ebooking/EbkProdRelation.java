package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;

import com.lvmama.comm.vo.Constant;

public class EbkProdRelation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4108556500255315194L;
	
	private Long relationId;
	private Long ebkProductId;
	private Long relateProductId;
	private Long relateProdBranchId;
	private String saleNumType;
	private String relateProductName;
	private String relateProdBranchName;
	private String relateProductType;
	
	public String getRelateProductTypeCh(){
		if(null!=relateProductType){
			return Constant.SUB_PRODUCT_TYPE.getCnName(relateProductType);
		}
		return "";
	}
	public Long getRelationId() {
		return relationId;
	}
	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}
	public Long getEbkProductId() {
		return ebkProductId;
	}
	public void setEbkProductId(Long ebkProductId) {
		this.ebkProductId = ebkProductId;
	}
	public Long getRelateProductId() {
		return relateProductId;
	}
	public void setRelateProductId(Long relateProductId) {
		this.relateProductId = relateProductId;
	}
	public Long getRelateProdBranchId() {
		return relateProdBranchId;
	}
	public void setRelateProdBranchId(Long relateProdBranchId) {
		this.relateProdBranchId = relateProdBranchId;
	}
	public String getSaleNumType() {
		return saleNumType;
	}
	public void setSaleNumType(String saleNumType) {
		this.saleNumType = saleNumType;
	}
	public String getRelateProductName() {
		return relateProductName;
	}
	public void setRelateProductName(String relateProductName) {
		this.relateProductName = relateProductName;
	}
	public String getRelateProdBranchName() {
		return relateProdBranchName;
	}
	public void setRelateProdBranchName(String relateProdBranchName) {
		this.relateProdBranchName = relateProdBranchName;
	}
	public String getRelateProductType() {
		return relateProductType;
	}
	public void setRelateProductType(String relateProductType) {
		this.relateProductType = relateProductType;
	}
}
