package com.lvmama.distribution.model.lv;

import com.lvmama.comm.utils.StringUtil;

/**
 * 分销产品基本信息请求报文参数
 * @author lipengcheng
 *
 */
public class ProductListParameter {

	/** 当前的页数*/
	private String currentPage;
	/** 每页记录数*/
	private String pageSize ;
	/** 产品类型*/
	private String productType ;
	
	/**
	 * 获得签证信息
	 * @return
	 */
	public String getLocalSigned(){
		return this.getCurrentPage() + this.getPageSize() + this.getProductType();
	}
	
	//setter and getter
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

}
