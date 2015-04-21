package com.lvmama.distribution.model.jd;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.distribution.util.DistributionUtil;
import com.lvmama.distribution.util.JdUtil;

/**
 * 京东明文输入消息报文
 * @author gaoxin
 *
 */
public class Request {
	private static Log log=LogFactory.getLog(Request.class);
	private Head head;
	private Body body;
	public Request(){}
	public Request(Head head,Body body){
		this.body=body;
		this.head=head;
	}
	
	
	/**
	 * 构造新增产品报文
	 * Base64(MD5(DistributionUtil.getKey()+messageId+partnerCode+timeStamp+resourceId+productId))
	 * @return
	 */
	public String buildProductToXml(){
		StringBuilder sb=new StringBuilder();
		String md5Str=DistributionUtil.getPropertiesByKey("jingdong.key") +head.getMessageId()+head.getPartnerCode()+head.getTimeStamp()+body.getProduct().getResourceId()+body.getProduct().getProductId();
		log.info("加密前的签名"+md5Str);
		this.head.setSigned(JdUtil.getSigned(md5Str));
		sb.append("<request>")
		.append(head.buildReqHeadToXml())
		.append(body.buildProductBodyToXml())
		.append("</request>");
		return sb.toString();
	}
	
	/**
	 * 构造更新产品报文
	 * Base64(MD5(key+messageId+partnerCode+timeStamp+resourceId+productId))
	 * @return
	 */
	public String buildUpdateProductToXml(){
		StringBuilder sb=new StringBuilder();
		String md5Str=DistributionUtil.getPropertiesByKey("jingdong.key")+head.getMessageId()+head.getPartnerCode()+head.getTimeStamp()+body.getProduct().getResourceId()+body.getProduct().getProductId();
		this.head.setSigned(JdUtil.getSigned(md5Str));
		sb.append("<request>")
		.append(head.buildReqHeadToXml())
		.append(body.buildUpdateProductBodyToXml())
		.append("</request>");
		return sb.toString();
	}
	
	/**
	 * 上、下架 产品报文
	 * Base64(MD5(DistributionUtil.getKey()+messageId+partnerCode+timeStamp+resourceId+productId))
	 * @return
	 */
	public String buildOnOffLineToXml(){
		StringBuilder sb=new StringBuilder();
		String md5Str=DistributionUtil.getPropertiesByKey("jingdong.key")+head.getMessageId()+head.getPartnerCode()+head.getTimeStamp()+body.getProduct().getResourceId()+body.getProduct().getProductId();
		this.head.setSigned(JdUtil.getSigned(md5Str));
		sb.append("<request>")
		.append(head.buildReqHeadToXml())
		.append(body.buildOnOffLineToXml())
		.append("</request>");
		return sb.toString();
	}
	/**
	 * 构造新增景区报文
	 * Base64(MD5(DistributionUtil.getKey()+messageId+partnerCode+timeStamp+resourceId))
	 * @return
	 */
	public String buildResourceToXml(){
		String md5Str=DistributionUtil.getPropertiesByKey("jingdong.key") +head.getMessageId()+head.getPartnerCode()+head.getTimeStamp()+body.getResource().getResourceId();
		this.head.setSigned(JdUtil.getSigned(md5Str));
		StringBuilder sb=new StringBuilder();
		sb.append("<request>")
		.append(head.buildReqHeadToXml())
		.append(body.buildResourcesBodyToXml())
		.append("</request>");
		return sb.toString();
	}
	/**
	 * 构造 更新景区  图片上传 报文
	 * Base64(MD5(DistributionUtil.getKey()+messageId+partnerCode+timeStamp+resourceId))
	 * @return
	 */
	public String buildUpdateResourceToXml(){
		StringBuilder sb=new StringBuilder();
		String md5Str= DistributionUtil.getPropertiesByKey("jingdong.key") +head.getMessageId()+head.getPartnerCode()+head.getTimeStamp()+body.getResource().getResourceId();
		this.head.setSigned(JdUtil.getSigned(md5Str));
		sb.append("<request>")
		.append(head.buildReqHeadToXml())
		.append(body.buildUpdateResourceToXml())
		.append("</request>");
		return sb.toString();
	}

	/**
     *	驴妈妈发起退款报文
     * Base64(MD5(DistributionUtil.getKey()+messageId+PartnerCode+timeStamp+orderid+refundAmount))
     * @return
     */
    
    public String buildApplyRefundToXml(){
    	StringBuilder sb=new StringBuilder();
    	String md5Str= DistributionUtil.getPropertiesByKey("jingdong.key") +head.getMessageId()+head.getPartnerCode()+head.getTimeStamp()+body.getOrder().getOrderId()+body.getOrder().getRefundAmount();
    	log.info(md5Str);
    	this.head.setSigned(JdUtil.getSigned(md5Str));
		sb.append("<request>")
		.append(head.buildReqHeadToXml())
		.append(body.buildApplyRefundToXml())
		.append("</request>");
		return sb.toString();
    }
    
    /**
	 * 新增、修改 每日价格  	 报文
	 * Base64(MD5(DistributionUtil.getKey()+messageId+partnerCode+timeStamp))
	 * @return
	 */
	public String buildDailyPriceToXml(){
		StringBuilder sb=new StringBuilder();
		String md5Str= DistributionUtil.getPropertiesByKey("jingdong.key") +head.getMessageId()+head.getPartnerCode()+head.getTimeStamp();
		this.head.setSigned(JdUtil.getSigned(md5Str));
		sb.append("<request>")
		.append(head.buildReqHeadToXml())
		.append(body.buildAddDailyPriceToXml())
		.append("</request>");
		return sb.toString();
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
