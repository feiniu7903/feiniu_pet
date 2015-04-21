package com.lvmama.passport.processor.impl.client.newland.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 重发短信对象
 * @author chenlinjun
 *
 */
public class ResendReq {
	private static final Log log = LogFactory.getLog(ResendReq.class);
	private String is_sp_id;
	private String username;
	private String password;
	private String pgoods_id;
	private String message_id;
	private String send_class;
	private Credential credential;
	private Recipients recipients;
	
	public String toResendReqXml(){
    	StringBuilder buf=new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>")
		.append("<ResendReq>")
		.append("<MessageID>").append(this.message_id).append("</MessageID>")
		.append("<ISSPID>").append(WebServiceConstant.getProperties("newland_isspid")).append("</ISSPID>")
		.append("<Username>").append(WebServiceConstant.getProperties("newland_username")).append("</Username>")
		.append("<Password>").append(WebServiceConstant.getProperties("newland_password")).append("</Password>")

		.append("<Recipients>")
	         .append(this.recipients.toRecipientsXml())
		.append("</Recipients>")
		
		.append("<SendClass>").append(this.send_class).append("</SendClass>")

		.append("<Credential>")
	         .append(this.credential.toCredentialXml())
		.append("</Credential>")
		
		.append("<PGoodsID>").append(WebServiceConstant.getProperties("newland_pgoodsid")).append("</PGoodsID>")
		.append("</ResendReq>");
		 log.info("++++++++++++++++++++++++ 翼码重发短信请求信息"+buf.toString());
    	return buf.toString();
	}
	
	public String getIs_sp_id() {
		return is_sp_id;
	}
	public void setIs_sp_id(String is_sp_id) {
		this.is_sp_id = is_sp_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPgoods_id() {
		return pgoods_id;
	}
	public void setPgoods_id(String pgoods_id) {
		this.pgoods_id = pgoods_id;
	}
	public String getMessage_id() {
		return message_id;
	}
	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}
	public String getSend_class() {
		return send_class;
	}
	public void setSend_class(String send_class) {
		this.send_class = send_class;
	}
	public Credential getCredential() {
		return credential;
	}
	public void setCredential(Credential credential) {
		this.credential = credential;
	}
	public Recipients getRecipients() {
		return recipients;
	}
	public void setRecipients(Recipients recipients) {
		this.recipients = recipients;
	}
	
}
