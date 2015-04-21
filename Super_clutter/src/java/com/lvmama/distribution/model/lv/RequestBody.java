package com.lvmama.distribution.model.lv;

import com.lvmama.distribution.util.DistributionUtil;

/**
 * 请求报文体对象
 * @author lipengcheng
 *
 */
public class RequestBody {
	
	/** 分销产品列表请求报文体*/
	private ProductListParameter productListParameter;
	/** 分销产品价格列表请求报文体*/
	private ProductPriceListParameter productPriceListParameter;
	/** 单个分销产品价格请求报文体*/
	private ProductPriceParameter productPriceParameter;
	/** 单个分销产品请求报文体*/
	private ProductParameter productParameter;
	/** 创建订单请求报文体*/
	private OrderInfo orderInfo;
	/** 修改订单状态请求报文体*/
	private Order order;
	
	public RequestBody(){}
	public RequestBody(String xmlStr){
		this.xmlStr=xmlStr;
	}
	
	private String xmlStr = null;
	public String getXmlStr() {
		return xmlStr;
	}

	public void setXmlStr(String xmlStr) {
		this.xmlStr = xmlStr;
	}

	/**
	 * 获得签证信息
	 * @return
	 */
	public String getLoaclSigned() {
		String localSinged = null;
		if (productListParameter != null) {
			localSinged = productListParameter.getLocalSigned();
		} else if (productPriceListParameter != null) {
			localSinged = productPriceListParameter.getLocalSigned();
		} else if (productPriceParameter != null) {
			localSinged = productPriceParameter.getLoaclSigned();
		} else if (productParameter != null) {
			localSinged = productParameter.getLoaclSigned();
		} else if (orderInfo != null) {
			localSinged = orderInfo.getLocalSigned();
		} else if (order != null) {
			localSinged = order.getLocalSigned();
		}
		return localSinged;
	}

	/**
	 * 退款报文体生成的方法
	 * 
	 * @return
	 */
	public String buildXmlStr() {
		return DistributionUtil.buildXmlElement("body", xmlStr);
	}
	
	
	public ProductListParameter getProductListParameter() {
		return productListParameter;
	}

	public void setProductListParameter(ProductListParameter productListParameter) {
		this.productListParameter = productListParameter;
	}

	public ProductPriceListParameter getProductPriceListParameter() {
		return productPriceListParameter;
	}

	public void setProductPriceListParameter(
			ProductPriceListParameter productPriceListParameter) {
		this.productPriceListParameter = productPriceListParameter;
	}

	public ProductPriceParameter getProductPriceParameter() {
		return productPriceParameter;
	}

	public void setProductPriceParameter(ProductPriceParameter productPriceParameter) {
		this.productPriceParameter = productPriceParameter;
	}

	public ProductParameter getProductParameter() {
		return productParameter;
	}

	public void setProductParameter(ProductParameter productParameter) {
		this.productParameter = productParameter;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
}
