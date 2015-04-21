package com.lvmama.distribution.model.jd;

import com.lvmama.distribution.util.DistributionUtil;
import com.lvmama.distribution.util.JdUtil;

/**
 * 
 * 京东响应消息
 * @author gaoxin
 *
 */
public class Response {
	private Head head;
	private Body body;
	public Response(){}
	public Response(Head head, Body body) {
		this.body=body;
		this.head=head;
	}
	/**
	 * 构造下单响应报文
	 * Base64(MD5(key+messageId+partnerCode+isSuccess))
	 * @return
	 */
	public String buildOrderToXml(){
		if("true".equals(body.getResult().getIsSuccess())&&body.getResult().getOrder()!=null){
			String md5Str=DistributionUtil.getPropertiesByKey("jingdong.key")+head.getMessageId()+head.getPartnerCode()+body.getResult().getIsSuccess()+body.getResult().getOrder().getOrderId();
			this.head.setSigned(JdUtil.getSigned(md5Str));
		}else{
			Md5Base64Str();
		}
		StringBuilder sb=new StringBuilder();
		sb.append("<response>")
		.append(head.buildResHeadToXml())
		.append(body.buildSubmitOrderBodyToXml())
		.append("</response>");
		return sb.toString();
	}
	 /**
     * 查询订单
     * Base64(MD5(key+messageId+partnerCode+isSuccess)))
     * @return
     */
    public String buildQueryOrderToXml(){
    	Md5Base64Str();
    	StringBuilder sb=new StringBuilder();
		sb.append("<response>")
		.append(head.buildResHeadToXml())
		.append(body.buildQueryOrderToXml())
		.append("</response>");
		return sb.toString();
    }
    
    /**
     * 重发短信
     * Base64(MD5(key+messageId+partnerCode+isSuccess))
     * @return
     */
    public String buildReSendSMSToXml(){
    	Md5Base64Str();
    	StringBuilder sb=new StringBuilder();
		sb.append("<response>")
		.append(head.buildResHeadToXml())
		.append(body.buildReSendSMSToXml())
		.append("</response>");
		return sb.toString();
    }
    
    /**
     * 退票 报文
     * Base64(MD5(key+messageId+partnerCode+isSuccess))
     * @return
     *//*
    public String buildRefundTicketToXml(){
    	Md5Base64Str();
    	StringBuilder sb=new StringBuilder();
		sb.append("<response>")
		.append(head.buildResHeadToXml())
		.append(body.buildRefundTicketToXml())
		.append("</response>");
		return sb.toString();
    }*/
    
    /**
     * 查询每日价格
     * Base64(MD5(key+messageId+partnerCode+isSuccess))
     * @return
     */
    public String buildQueryDailyPricesToXml(){
    	Md5Base64Str();
    	StringBuilder sb=new StringBuilder();
		sb.append("<response>")
		.append(head.buildResHeadToXml())
		.append(body.buildQueryDailyPricesToXml())
		.append("</response>");
		return sb.toString();
    }
    /**
     * 错误信息
     * @return
     */
    public String buildErrorMsg(){
    	StringBuilder sb=new StringBuilder();
    	Md5Base64Str();
		sb.append("<response>")
		.append(head.buildResHeadToXml())
		.append(body.buildQueryDailyPricesToXml())
		.append("</response>");
		return sb.toString();
    }
    
    /**
     * 加密签名
     */
    private void Md5Base64Str(){
    	String md5Str=DistributionUtil.getPropertiesByKey("jingdong.key")+head.getMessageId()+head.getPartnerCode()+body.getResult().getIsSuccess();
		this.head.setSigned(JdUtil.getSigned(md5Str));
    }
    
    
	public Head getHead() {
		return head;
	}
	public void setHead(Head head) {
		this.head = head;
	}
	public Body getBody() {
		return body;
	}
	public void setBody(Body body) {
		this.body = body;
	}
    

}
