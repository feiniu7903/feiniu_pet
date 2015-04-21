package com.lvmama.passport.processor.impl.client.newland.model;
/**
 * 废码响应对象
 * @author chenlinjun
 *
 */
public class RevokeRes {
	private String pos_id;
	private String customization;
	private String req_sequence;
	private String credential_class;
	private String credential;
	private String org_sequence;
	private String print_text;
	private String print_links;
	private GoodsRecord goodsRecord;
	private Status status;

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
