package com.lvmama.passport.processor.impl.client.ylgl.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 生成请求xml
 * @author lipengcheng
 *
 */
public class Request {
	
	private static final Log log = LogFactory.getLog(Request.class);
	private String requestType;// 请求类型
	private String organization;// 商户ID
	private String userId;// 账户ID
	private String pwd;// 账户密码
	private String dltel;// 分销商电话
	private String dlurl;// 分销商网址
	private String productId;// 产品编号
	private String tel;// 接收手机号
	private String count;// 数量
	private String type;// 票类型
	private String useTime;// 使用时间
	private String useName;// 客户姓名
	private String orderId;// 订单号
	private String oriSeq;// 申请重发/撤销的订单号

	/**
	 * 请求交易请求报文
	 * 
	 * @return
	 */
	public String toApplayCodeRequestXml() {
		StringBuilder buf = new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>")
				.append("<business_trans version=\"1.0\" >")
					.append("<request_type>").append(requestType).append("</request_type>")
					.append("<organization>").append(organization).append("</organization>")
					.append("<userid>").append(userId).append("</userid>")
					.append("<pwd>").append(pwd).append("</pwd>")
					.append("<dltel>").append(dltel).append("</dltel>")
					.append("<dlurl>").append(dlurl).append("</dlurl>")
					.append("<product_id>").append(productId).append("</product_id>")
					.append("<tel>").append(tel).append("</tel>")
					.append("<count>").append(count).append("</count>")
					.append("<type>").append(type).append("</type>")
					.append("<orderid>").append(orderId).append("</orderid>")
					.append("<usetime>").append(useTime).append("</usetime>")
					.append("<usename>").append(useName).append("</usename>")
				.append("</business_trans>");
		log.info("++++++++++++++++++++++++ Applay Code Request Xml:"
				+ buf.toString());
		return buf.toString();
	}
	/**
	 * 重发请求报文
	 * @return
	 */
	public String toRePlayApplayCodeRequestXml(){
    	StringBuilder buf=new StringBuilder();
    	buf.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>")
    		.append("<business_trans version=\"1.0\" >")
	    		.append("<request_type>").append(requestType).append("</request_type>")
	    		.append("<organization>").append(organization).append("</organization>")
	    		.append("<userid>").append(userId).append("</userid>")
	    		.append("<pwd>").append(pwd).append("</pwd>")
	    		.append("<er_resend_request>")
	    			.append("<ori_seq>").append(oriSeq).append("</ori_seq>")
	    		.append("</er_resend_request>")
    		.append("</business_trans>");
		 log.info("++++++++++++++++++++++++ Applay Code Request Xml:"+buf.toString());
    	return buf.toString();
	}
	
	
	/**
	 * 撤销交易请求报文
	 * @return
	 */
	public String toDestoryCodeRequestXml() {
		StringBuilder buf = new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>")
			.append("<business_trans version=\"1.0\" >")
				.append("<request_type>").append(requestType).append("</request_type>")
				.append("<organization>").append(organization).append("</organization>")
				.append("<userid>").append(userId).append("</userid>")
				.append("<pwd>").append(pwd).append("</pwd>")
				.append("<commission_cancel_request>")
					.append("<ori_seq>").append(oriSeq).append("</ori_seq>")
				.append(" </commission_cancel_request>")
			.append("</business_trans>");
		log.info("++++++++++++++++++++++++ Applay Code Request Xml:" + buf.toString());
		return buf.toString();
	}
	
	/**
	 * 获取产品信息交易返回报文
	 * @return
	 */
	
	public String toProductInfoRequestXml(){
		StringBuilder buf = new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>")
			.append("<business_trans>")
				.append("<request_type>").append(requestType).append("</request_type>")
				.append("<organization>").append(organization).append("</organization>")
				.append("<userid>").append(userId).append("</userid>")
				.append("<pwd>").append(pwd).append("</pwd>")
			.append("</business_trans>")
		;
		
		
		
		log.info("++++++++++++++++++++++++ Applay Code Request Xml:" + buf.toString());
		return buf.toString();
	}
	
	
	
	
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getDltel() {
		return dltel;
	}
	public void setDltel(String dltel) {
		this.dltel = dltel;
	}
	public String getDlurl() {
		return dlurl;
	}
	public void setDlurl(String dlurl) {
		this.dlurl = dlurl;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	public String getUseName() {
		return useName;
	}
	public void setUseName(String useName) {
		this.useName = useName;
	}
	public String getOriSeq() {
		return oriSeq;
	}
	public void setOriSeq(String oriSeq) {
		this.oriSeq = oriSeq;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
