package com.lvmama.distribution.model.lv;

import com.lvmama.comm.utils.StringUtil;

/**
 * 单个分销产品时间价格
 * @author lipengcheng
 *
 */
public class ProductPriceParameter {
	
	/** 产品Id*/
	private String productId;
	/** 产品价格：开始时间*/
	private String beginDate;
	/** 门票有效期：结束时间*/
	private String endDate;
	
	/**
	 * 获得签证信息
	 * @return
	 */
	public String getLoaclSigned(){
		return this.getProductId() + this.getBeginDate() + this.getEndDate();
	}
	
	public String getProductId() {
		return StringUtil.replaceNullStr(productId);
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getBeginDate() {
		return StringUtil.replaceNullStr(beginDate);
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return StringUtil.replaceNullStr(endDate);
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
