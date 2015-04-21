package com.lvmama.passport.processor.impl.client.newland.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 废码请求对象
 * 
 * @author chenlinjun
 * 
 */
public class RevokeReq {
	private static final Log log = LogFactory.getLog(RevokeReq.class);
	private String pos_id;
	private String customization;
	private String req_sequence;
	private String credential_class;
	private String credential;
	private String org_sequence;
	private GoodsRecord goodsRecord;
/**
 * 废码请求信息XML
 * @return
 */
	public String toDestoyCodeXml(){
    	StringBuilder buf=new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>")
		.append("<RevokeReq>")
		.append("<PosID>").append(this.pos_id).append("</PosID>")
		.append("<Customization>").append(this.customization).append("</Customization>")
		.append("<ReqSequence>").append(this.req_sequence).append("</ReqSequence>")
		.append("<CredentialClass>").append(this.credential_class).append("</CredentialClass>")
		.append("<Credential>").append(this.credential).append("</Credential>")
		.append("<OrgSequence>").append(this.org_sequence).append("</OrgSequence>")
		
		.append("<GoodsRecord>")
	         .append(this.goodsRecord.toDestoyCodeXml())
		.append("</GoodsRecord>")
		
		.append("</RevokeReq>");
		 log.info("++++++++++++++++++++++++ 翼码废码请求信息"+buf.toString());
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

	public String getOrg_sequence() {
		return org_sequence;
	}

	public void setOrg_sequence(String org_sequence) {
		this.org_sequence = org_sequence;
	}

	public GoodsRecord getGoodsRecord() {
		return goodsRecord;
	}

	public void setGoodsRecord(GoodsRecord goodsRecord) {
		this.goodsRecord = goodsRecord;
	}

}
