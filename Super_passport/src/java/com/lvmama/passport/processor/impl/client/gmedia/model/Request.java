package com.lvmama.passport.processor.impl.client.gmedia.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Request {
	private static final Log log = LogFactory.getLog(Request.class);
    private String version;
    private String sequenceId;
    private String partnerCode;
    private String signed;
    private String statusCode;
    private String message;
	private Head head;
	private Body body;
    private String reqBody;
/**
 * 申请请求
 * @return
 */
	public String toApplayCodeRequestXml(){
    	StringBuilder buf=new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
		.append("<Request>")
		.append(this.head.toRequestHeadXml())
		.append("<Body>")
		.append(this.body.toApplyCodeRequestBodyXml())
		.append("</Body>")
		.append("</Request>");
		 log.info("++++++++++++++++++++++++ Applay Code Request Xml:"+buf.toString());
    	return buf.toString();
	}
	/**
	 * 废码请求
	 * @return
	 */
	public String toDestoyCodeRequestXml(){
    	StringBuilder buf=new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
		.append("<Request>")
		.append(this.head.toRequestHeadXml())
		.append("<Body>")
		.append(this.body.toDestoyCodeRequestBodyXml())
		.append("</Body>")
		.append("</Request>");
		 log.info("++++++++++++++++++++++++ Applay Code Request Xml:"+buf.toString());
    	return buf.toString();
	}
	
	/**
	 * 废码请求
	 * @return
	 */
	public String toUpdateContentRequestXml(){
    	StringBuilder buf=new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
		.append("<Request>")
		.append(this.head.toRequestHeadXml())
		.append("<Body>")
		.append(this.body.toUpdateContentBodyXml())
		.append("</Body>")
		.append("</Request>");
		 log.info("++++++++++++++++++++++++ Applay Code Request Xml:"+buf.toString());
    	return buf.toString();
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
	public String getReqBody() {
		return reqBody;
	}
	public void setReqBody(String reqBody) {
		this.reqBody = reqBody;
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

}
