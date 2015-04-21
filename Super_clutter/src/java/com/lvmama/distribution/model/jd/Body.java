package com.lvmama.distribution.model.jd;

import com.lvmama.distribution.util.JdUtil;


/**
 * 明文消息体
 * @author gaoxin
 */
public class Body {
	private Resource resource;
	private Product product;
	private Order order;
	private Result result;
	
	public Body(){}
	public Body(Product product){
		this.product =product;
	}
	public Body(Result result){
		this.result=result;
	}
	public Body(Resource resource){
		this.resource=resource;
	}
	public Body(Order order) {
		this.order=order;
	}
	/**
	 * 构造新增景区报文
	 * @return 
	 */
	public String buildResourcesBodyToXml(){
		StringBuilder sb=new StringBuilder();
		sb.append(JdUtil.buildXmlElement("body", resource.buildResourceToXml()));
		return sb.toString();
	}
	/**
	 * 构造   更新景区  图片上传 报文
	 * @return
	 */
	public String buildUpdateResourceToXml() {
		StringBuilder sb=new StringBuilder();
		sb.append(JdUtil.buildXmlElement("body", resource.buildUpdateResourceToXml()));
		return sb.toString();
	}
	
	/**
	 * 构造产品报文
	 * @return
	 */
	public String buildProductBodyToXml(){
		StringBuilder sb=new StringBuilder();
		sb.append(JdUtil.buildXmlElement("body", product.buildProductToXml()));
		return sb.toString();
	}
	/**
	 * 构造更新产品报文
	 * @return
	 */
	public String buildUpdateProductBodyToXml(){
		StringBuilder sb=new StringBuilder();
		sb.append(JdUtil.buildXmlElement("body", product.buildUpdateProductToXml()));
		return sb.toString();
	}
	/**
	 * 上、下架产品报文
	 * @return
	 */
	public String buildOnOffLineToXml(){
		StringBuilder sb=new StringBuilder();
		sb.append(JdUtil.buildXmlElement("body", product.buildOnOffLineToXml()));
		return sb.toString();
	}

	/**
     *	驴妈妈发起退款
     * @return
     */
    
    public String buildApplyRefundToXml(){
    	StringBuilder sb=new StringBuilder();
		sb.append(JdUtil.buildXmlElement("body", this.order.buildApplyRefundToXml()));
		return sb.toString();
    }
    
    /**
	 * 新增、修改 每日价格  	 报文
	 * @return
	 */
	public String buildAddDailyPriceToXml(){
		StringBuilder sb=new StringBuilder();
		sb.append("<body>")
		.append(JdUtil.buildXmlElement("products", product.buildAddDailyPriceToXml()))
		.append("</body>");
		return sb.toString();
	}
	
	
	/**
	 * 构造下单响应报文
	 * @return
	 */
	public String buildSubmitOrderBodyToXml(){
		StringBuilder sb=new StringBuilder();
		sb.append(JdUtil.buildXmlElement("body", result.buildSubmitOrdertoXml()));
		return sb.toString();
	}
	
	
	 /**
     * 查询订单 报文
     * @return
     */
    public String buildQueryOrderToXml(){
    	StringBuilder sb=new StringBuilder();
    	sb.append(JdUtil.buildXmlElement("body", result.buildQueryOrderToXml()));
		return sb.toString();
    }
    /**
     * 重发短信 报文
     * @return
     */
    public String buildReSendSMSToXml(){
    	StringBuilder sb=new StringBuilder();
    	sb.append(JdUtil.buildXmlElement("body", result.buildReSendSMSToXml()));
		return sb.toString();
    }
	
 /*   *//**
     * 退票 报文
     * @return
     *//*
    public String buildRefundTicketToXml(){
    	StringBuilder sb=new StringBuilder();
    	sb.append(JdUtil.buildXmlElement("body", result.buildRefundTicketToXml()));
		return sb.toString();
    }*/
    /**
     * 查询每日价格
     * @return
     */
    public String buildQueryDailyPricesToXml(){
    	StringBuilder sb=new StringBuilder();
    	sb.append(JdUtil.buildXmlElement("body", result.buildQueryDailyPricesToXml()));
    	return sb.toString();
    }
	
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	
	
}
