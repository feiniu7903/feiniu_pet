package com.lvmama.passport.processor.impl.client.newland.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 验证码请求对象
 * 
 * @author chenlinjun
 * 
 */
public class VerifyReq {
	private static final Log log = LogFactory.getLog(VerifyReq.class);
	private String pos_id;
	private String customization;
	private String req_sequence;
	private String credential_class;
	private String credential;
	private String posTransTime ; 
	private GoodsRecord goodsRecord;
	/**
	 * 验证码XML
	 * @return
	 */
	public String toVerifyReqXml(){
    	StringBuilder buf=new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>")
		.append("<VerifyReq>")
		.append("<PosID>").append(this.pos_id).append("</PosID>")
		.append("<Customization>").append(this.customization).append("</Customization>")
		.append("<ReqSequence>").append(this.req_sequence).append("</ReqSequence>")
		.append("<CredentialClass>").append(this.credential_class).append("</CredentialClass>")
		.append("<Credential>").append(this.credential).append("</Credential>")

		.append("<GoodsRecord>")
	         .append(this.goodsRecord.toVerifyReqXml())
		.append("</GoodsRecord>")

		.append("</VerifyReq>");
		 log.info("++++++++++++++++++++++++ 翼码请求信息"+buf.toString());
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

	public GoodsRecord getGoodsRecord() {
		return goodsRecord;
	}

	public void setGoodsRecord(GoodsRecord goodsRecord) {
		this.goodsRecord = goodsRecord;
	}

	public String getPosTransTime() {
		return posTransTime;
	}

	public void setPosTransTime(String posTransTime) {
		this.posTransTime = posTransTime;
	}

}
