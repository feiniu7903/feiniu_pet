package com.lvmama.distribution.model.jd;

import com.lvmama.distribution.util.JdUtil;
/**
 * 响应结果
 * @author gaoxin
 *
 */
public class Result {
	private String isSuccess;//是否成功
	private String errorCode;//错误码表
	private String errorMsg;//错误说明
	private String dealTime;//处理完时间
	private Order order;
	private Product product;
	
	public Result(){}
	public Result(String isSuccess,String errorCode,String errorMsg,String dealTime){
		this.isSuccess=isSuccess;
		this.errorCode=errorCode;
		this.errorMsg=errorMsg;
		this.dealTime=dealTime;
	}
	public Result(String isSuccess,String errorCode,String errorMsg,String dealTime,Order order){
		this.isSuccess=isSuccess;
		this.errorCode=errorCode;
		this.errorMsg=errorMsg;
		this.dealTime=dealTime;
		this.order=order;
	}
	public Result(String isSuccess,String errorCode,String errorMsg,String dealTime,Product product){
		this.isSuccess=isSuccess;
		this.errorCode=errorCode;
		this.errorMsg=errorMsg;
		this.dealTime=dealTime;
		this.product=product;
	}
	/**
	 * 下单响应
	 * @return
	 */
	public String buildSubmitOrdertoXml(){
		StringBuilder sb=new StringBuilder();
		sb.append("<result>")
		.append(JdUtil.buildXmlElement("isSuccess", isSuccess))
		.append(JdUtil.buildXmlElementInCheck("errorCode", errorCode))
		.append(JdUtil.buildXmlElementInCheck("errorMsg", errorMsg))
		.append(JdUtil.buildXmlElement("dealTime", dealTime));
		if(order!=null){
			sb.append(order.buildSubmitOrderToXml());
		}
		sb.append("</result>");
		return sb.toString();
	}
	
	/**
	 * 查询订单
	 * @return
	 */
	public String buildQueryOrderToXml(){
		StringBuilder sb=new StringBuilder();
		sb.append("<result>")
		.append(JdUtil.buildXmlElement("isSuccess", isSuccess))
		.append(JdUtil.buildXmlElementInCheck("errorCode", errorCode))
		.append(JdUtil.buildXmlElementInCheck("errorMsg", errorMsg))
		.append(JdUtil.buildXmlElement("dealTime", dealTime));
		if(order!=null){
			sb.append(order.buildQueryOrderToXml());
		}
		sb.append("</result>");
		return sb.toString();
	}
	/**
	 *  重发短信
	 * @return
	 */
	public String buildReSendSMSToXml() {
		StringBuilder sb=new StringBuilder();
		sb.append("<result>")
		.append(JdUtil.buildXmlElement("isSuccess", isSuccess))
		.append(JdUtil.buildXmlElementInCheck("errorCode", errorCode))
		.append(JdUtil.buildXmlElementInCheck("errorMsg", errorMsg))
		.append(JdUtil.buildXmlElement("dealTime", dealTime))
		.append("</result>");
		return sb.toString();
	}
	/**
	 * 退票
	 * @return
	 *//*
	public String buildRefundTicketToXml() {
		StringBuilder sb=new StringBuilder();
		sb.append("<result>")
		.append(JdUtil.buildXmlElement("isSuccess", isSuccess))
		.append(JdUtil.buildXmlElementInCheck("errorCode", errorCode))
		.append(JdUtil.buildXmlElementInCheck("errorMsg", errorMsg))
		.append(JdUtil.buildXmlElement("dealTime", dealTime))
		.append(order.buildRefundTicketToXml())
		.append("</result>");
		return sb.toString();
	}*/
	
	/**
     * 查询每日价格
     * @return
     */
    public String buildQueryDailyPricesToXml(){
		StringBuilder sb=new StringBuilder();
		sb.append("<result>")
		.append(JdUtil.buildXmlElement("isSuccess", isSuccess))
		.append(JdUtil.buildXmlElementInCheck("errorCode", errorCode))
		.append(JdUtil.buildXmlElementInCheck("errorMsg", errorMsg))
		.append(JdUtil.buildXmlElement("dealTime", dealTime));
		if(product!=null){
			sb.append(product.buildQueryDailyPricesToXml());
		}
		sb.append("</result>");
		return sb.toString();
    }
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getDealTime() {
		return dealTime;
	}
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
