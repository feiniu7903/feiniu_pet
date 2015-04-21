package com.lvmama.passport.processor.impl.client.newland.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 验证码响应对象
 * 
 * @author chenlinjun
 * 
 */
public class VerifyRes {
	private static final Log log = LogFactory.getLog(VerifyRes.class);
	private String pos_id;
	private String customization;
	private String req_sequence;
	private String credential_class;
	private String credential;
	private String assist_number;
	private String print_text;
	private String print_links;
	private String activity_id;
	private String activity_description;
	private GoodsRecordList goodsRecordList;
	private Status status;
	/**
	 * 翼码验证码响应XML
	 * @return
	 */
	public String toVerifyResXml(){
    	StringBuilder buf=new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>")
		.append("<VerifyRes>")
		.append("<PosID>").append(this.pos_id).append("</PosID>")
		.append("<Customization>").append(this.customization).append("</Customization>")
		.append("<ResSequence>").append(this.req_sequence).append("</ResSequence>")
		.append("<CredentialClass>").append(this.credential_class).append("</CredentialClass>")
		.append("<Credential>").append(this.credential).append("</Credential>")
		
		.append("<AssistNumber>").append(this.assist_number).append("</AssistNumber>")
		.append("<PrintText>").append(this.print_text).append("</PrintText>")
		.append("<PrintLinks>").append(this.print_links).append("</PrintLinks>")
		.append("<ActivityID>").append(this.activity_id).append("</ActivityID>")
		.append("<ActivityDescription>").append(this.activity_description).append("</ActivityDescription>")

		.append("<GoodsRecordList>")
	         .append(this.goodsRecordList.toGoodsRecordListXml())
		.append("</GoodsRecordList>")
		
		.append("<Status>")
	         .append(this.status.toStatusXml())
		.append("</Status>")
		.append("</VerifyRes>");
		 log.info("++++++++++++++++++++++++ 翼码验证码响应XML"+buf.toString());
    	return buf.toString();
	}
	
	public String getPos_id() {
		return pos_id;
	}

	public void setPos_id(String pos_id) {
		this.pos_id = pos_id;
	}

	public String getCustomization() {
		return customization;
	}

	public void setCustomization(String customization) {
		this.customization = customization;
	}

	public String getReq_sequence() {
		return req_sequence;
	}

	public void setReq_sequence(String req_sequence) {
		this.req_sequence = req_sequence;
	}

	public String getCredential_class() {
		return credential_class;
	}

	public void setCredential_class(String credential_class) {
		this.credential_class = credential_class;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	public String getAssist_number() {
		return assist_number;
	}

	public void setAssist_number(String assist_number) {
		this.assist_number = assist_number;
	}

	public String getPrint_text() {
		return print_text;
	}

	public void setPrint_text(String print_text) {
		this.print_text = print_text;
	}

	public String getPrint_links() {
		return print_links;
	}

	public void setPrint_links(String print_links) {
		this.print_links = print_links;
	}

	public String getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}

	public String getActivity_description() {
		return activity_description;
	}

	public void setActivity_description(String activity_description) {
		this.activity_description = activity_description;
	}

	public GoodsRecordList getGoodsRecordList() {
		return goodsRecordList;
	}

	public void setGoodsRecordList(GoodsRecordList goodsRecordList) {
		this.goodsRecordList = goodsRecordList;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
