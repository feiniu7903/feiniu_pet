package com.lvmama.passport.processor.impl.client.gmedia.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Response {
	private static final Log log = LogFactory.getLog(Response.class);
    private String version;
    private String sequenceId;
    private String partnerCode;
    private String signed;
    private String statusCode;
    private String message;
	private Head head;
	private String body;
	private Body repBody;
	/**
	 * 回收码响应
	 * @return
	 */
	public String toUsedCodeResponseXml(){
    	StringBuilder buf=new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
		.append("<Response>")
		.append(this.head.toResponseHeadXml())
		.append("<Body>")
		.append(this.repBody.toUsedCodeBodyXml())
		.append("</Body>")
		.append("</Response>");
		 log.info("++++++++++++++++++++++++ Used Code Response Xml:"+buf.toString());
    	return buf.toString();
	}
	
	/**
	 * 更改人数响应
	 * @return
	 */
	public String toUpdatePersonResponseXml(){
    	StringBuilder buf=new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
		.append("<Response>")
		.append(this.head.toResponseHeadXml())
		.append("<Body>")
		.append(this.repBody.toUpdatePersonBodyXml())
		.append("</Body>")
		.append("</Response>");
		 log.info("++++++++++++++++++++++++ Update Person Response Xml:"+buf.toString());
    	return buf.toString();
	}
	
	/**
	 * 验证码响应
	 * @return
	 */
	public String toValidCodeResponseXml(){
    	StringBuilder buf=new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
		.append("<Response>")
		.append(this.head.toResponseHeadXml())
		.append("<Body>")
		.append(this.repBody.toValidCodeBodyXml())
		.append("</Body>")
		.append("</Response>");
		 log.info("++++++++++++++++++++++++ Valid Code Response Xml:"+buf.toString());
    	return buf.toString();
	}

	/**
	 * 离线多条凭证响应
	 * 
	 * @return
	 */
	public String toVoucherResponseXml() {
		StringBuilder buf = new StringBuilder()
			.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
			.append("<Response>")
			.append(this.head.toResponseHeadXml())
			.append("<Body>")
			.append(this.repBody.toVouchersBodyXml())
			.append("</Body>")
			.append("</Response>");
		if (log.isDebugEnabled()) {
			log.debug("++++++++++++++++++++++++ Vouchers Code Response Xml:"
					+ buf.toString());
		}
		return buf.toString();
	}

	/**
	 * 离线单条凭证响应
	 * 
	 * @return
	 */
	public String getSingleVoucherResponseXml() {
		StringBuilder buf = new StringBuilder()
			.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
			.append("<Response>")
			.append(this.head.toResponseHeadXml())
			.append("<Body>")
			.append(this.repBody.createSingleVouchersBodyXml())
			.append("</Body>")
			.append("</Response>");
		log.info("++++++++++++++++++++++++ Single Voucher Code Response Xml:" + buf.toString());
		return buf.toString();
	}

	/**
	 * 离线凭证响应
	 * @return
	 */
	public String toFormatResponseXml(){
    	StringBuilder buf=new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
		.append("<Response>")
		.append(this.head.toResponseHeadXml())
		.append("<Body>")
		.append(this.repBody.toFormatBodyXml())
		.append("</Body>")
		.append("</Response>");
		if (log.isDebugEnabled()) {
			log.debug("++++++++++++++++++++++++ Vouchers Code Response Xml:"
					+ buf.toString());
		}
    	return buf.toString();
	}
	
	public Head getHead() {
		return head;
	}
	public void setHead(Head head) {
		this.head = head;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}
	public String getPartnerCode() {
		return partnerCode;
	}
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	public String getSigned() {
		return signed;
	}
	public void setSigned(String signed) {
		this.signed = signed;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Body getRepBody() {
		return repBody;
	}
	public void setRepBody(Body repBody) {
		this.repBody = repBody;
	}
	
}
