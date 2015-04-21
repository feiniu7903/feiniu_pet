package com.lvmama.passport.processor.impl.client.newland.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 回退响应对象
 * @author chenlinjun
 *
 */
public class RollbackRes {
	private static final Log log = LogFactory.getLog(RollbackRes.class);
	private String pos_id;
	private String customization;
	private String req_sequence;
	private String credential_class;
	private String credential;
	private String org_sequence;
	private GoodsRecord goodsRecord;
	private Status status;
	/**
	 * 回退响应XML
	 * @return
	 */
	public String toRollbackResXml(){
    	StringBuilder buf=new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>")
		.append("<RollbackRes>")
		.append("<PosID>").append(this.pos_id).append("</PosID>")
		.append("<Customization>").append(this.customization).append("</Customization>")
		.append("<ResSequence>").append(this.req_sequence).append("</ResSequence>")
		.append("<CredentialClass>").append(this.credential_class).append("</CredentialClass>")
		.append("<Credential>").append(this.credential).append("</Credential>")
		.append("<OrgSequence>").append(this.org_sequence).append("</OrgSequence>")
		
		.append("<GoodsRecord>")
	         .append(this.goodsRecord.toRollbackResXml())
		.append("</GoodsRecord>")
		
		.append("<Status>")
	         .append(this.status.toStatusXml())
		.append("</Status>")
		
		.append("</RollbackRes>");
		 log.info("++++++++++++++++++++++++ 翼码回退响应信息"+buf.toString());
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
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
}
