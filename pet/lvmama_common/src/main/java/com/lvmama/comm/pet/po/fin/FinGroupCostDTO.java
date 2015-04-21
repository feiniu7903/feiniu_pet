package com.lvmama.comm.pet.po.fin;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

public class FinGroupCostDTO extends FinGLInterfaceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5025416889619419792L;
	
	private Long itemId;
	private String name;
	private Long subTotalCosts;
	private String supplierId;
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getSubTotalCosts() {
		return subTotalCosts;
	}
	public void setSubTotalCosts(Long subTotalCosts) {
		this.subTotalCosts = subTotalCosts;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getProductTypeName(){
		return Constant.PRODUCT_TYPE.getCnName(super.getProductType());
	}
	public String getSubProductTypeName(){
		return Constant.SUB_PRODUCT_TYPE.getCnName(super.getSubProductType());
	}
}
