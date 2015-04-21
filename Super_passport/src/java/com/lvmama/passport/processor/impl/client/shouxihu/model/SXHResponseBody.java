package com.lvmama.passport.processor.impl.client.shouxihu.model;

/**
 * 扬州瘦西湖--响应报文消息体
 * @author lipengcheng
 *
 */
public class SXHResponseBody {
	
	private SXHOrderInfo orderInfo;
	private SXHProductList productList;
	private String serialId;
	private String page;
	private String pagesize;

	public SXHOrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(SXHOrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public SXHProductList getProductList() {
		return productList;
	}

	public void setProductList(SXHProductList productList) {
		this.productList = productList;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPagesize() {
		return pagesize;
	}

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

}
