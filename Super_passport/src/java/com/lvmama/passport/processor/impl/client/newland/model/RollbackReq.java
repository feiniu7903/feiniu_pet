package com.lvmama.passport.processor.impl.client.newland.model;

/**
 * 回退请求对象
 * 
 * @author chenlinjun
 * 
 */
public class RollbackReq {
	private String pos_id;
	private String customization;
	private String req_sequence;
	private String credential_class;
	private String credential;
	private String org_sequence;
	private String comment;
	private GoodsRecord goodsRecord;

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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public GoodsRecord getGoodsRecord() {
		return goodsRecord;
	}

	public void setGoodsRecord(GoodsRecord goodsRecord) {
		this.goodsRecord = goodsRecord;
	}
}
