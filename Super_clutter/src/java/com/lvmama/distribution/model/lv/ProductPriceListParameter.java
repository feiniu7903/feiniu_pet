package com.lvmama.distribution.model.lv;

import com.lvmama.comm.utils.StringUtil;

/**
 * 分销产品价格列表请求报文体
 * @author lipengcheng
 *
 */
public class ProductPriceListParameter {
	
	/** 当前的页数*/
	private String currentPage;
	/** 每页记录数*/
	private String pageSize ;
	/** 产品类型*/
	private String productType;
	/** 产品价格：开始间*/
	private String beginDate;
	/** 门票有效期：结束时间*/
	private String endDate;
	
	/**
	 * 获得签证信息
	 * @return
	 */
	public String getLocalSigned(){
		return this.getCurrentPage()+this.getPageSize()+this.getProductType()+this.getBeginDate()+this.getEndDate();
	}
	
	public String getCurrentPage() {
		return StringUtil.replaceNullStr(currentPage);
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getPageSize() {
		return StringUtil.replaceNullStr(pageSize);
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getProductType() {
		return StringUtil.replaceNullStr(productType);
	}
	public void setProductType(String productType) {
		this.productType = productType;
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
