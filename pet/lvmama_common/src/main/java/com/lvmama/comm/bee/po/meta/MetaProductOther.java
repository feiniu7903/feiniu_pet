package com.lvmama.comm.bee.po.meta;

import com.lvmama.comm.vo.Constant;

public class MetaProductOther  extends MetaProduct {

	/**
	 * 
	 */
	private static final long serialVersionUID = -733099528612489540L;

	private Long metaOtherId;
	
	private String otherType;
	
	private Integer insuranceDay;

	private String currencyName;
	
	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	public Long getMetaOtherId() {
		return metaOtherId;
	}

	public void setMetaOtherId(Long metaOtherId) {
		this.metaOtherId = metaOtherId;
	}
	public String getProductType() {
		return Constant.PRODUCT_TYPE.OTHER.name();
	}
	public String getOtherType() {
		return otherType;
	}

	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}
	
	public Integer getInsuranceDay() {
		return insuranceDay;
	}

	public void setInsuranceDay(Integer insuranceDay) {
		this.insuranceDay = insuranceDay;
	}

	/**
	 * 返回中文表述的门票类型
	 * 
	 * @return
	 */
	public String getZhOtherType() {
		return Constant.PRODUCT_TYPE.OTHER.getCnName();
	}

}