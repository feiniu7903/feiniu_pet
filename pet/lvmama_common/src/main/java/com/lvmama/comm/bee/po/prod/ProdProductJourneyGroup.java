/**
 * 
 */
package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 行程段组，包含一个行程当中对应一组类型的产品的组,一个行程段当中一个类型的组只可以存在一个.
 * @author yangbin
 *
 */
public class ProdProductJourneyGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1670281846259888404L;
	private Long journeyGroupId;
	private Long prodJourneyId;//行程ID
	private String productType;
	private boolean required;
	
	private List<ProdJourneyProduct> journeyProductList=Collections.emptyList();
	//private boolean defaultGroup;
	/**
	 * @return the journeyGroupId
	 */
	public Long getJourneyGroupId() {
		return journeyGroupId;
	}
	/**
	 * @param journeyGroupId the journeyGroupId to set
	 */
	public void setJourneyGroupId(Long journeyGroupId) {
		this.journeyGroupId = journeyGroupId;
	}
	
	/**
	 * @return the prodJourneyId
	 */
	public Long getProdJourneyId() {
		return prodJourneyId;
	}
	/**
	 * @param prodJourneyId the prodJourneyId to set
	 */
	public void setProdJourneyId(Long prodJourneyId) {
		this.prodJourneyId = prodJourneyId;
	}
	/**
	 * @return the required
	 */
	public boolean isRequired() {
		return required;
	}
	/**
	 * @param required the required to set
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}
	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}
	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}
	/**
	 * @return the journeyProductList
	 */
	public List<ProdJourneyProduct> getJourneyProductList() {
		return journeyProductList;
	}
	/**
	 * @param journeyProductList the journeyProductList to set
	 */
	public void setJourneyProductList(List<ProdJourneyProduct> journeyProductList) {
		this.journeyProductList = journeyProductList;
	}
	
	
	
	
	
}
