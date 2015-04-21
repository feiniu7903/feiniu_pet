package com.lvmama.passport.processor.impl.client.newland.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 申请码请求对象
 * 
 * @author chenlinjun
 * 
 */
public class SubmitReq {
	private static final Log log = LogFactory.getLog(SubmitReq.class);
	private String is_sp_id;
	private String username;
	private String password;
	private String pgoods_id;
	private String transactionId;
	private String sendClass;
	private String channelId;
	private String customArea;
	private Recipients recipients;
	private Credential credential;
	private Messages messages;
	private PassCode passCode;
	public SubmitReq(PassCode passCode){
		this.passCode=passCode;
		this.transactionId=passCode.getSerialNo();
	}
/**
 * 申请码请求XML
 * @return
 */
	public String toApplyCodeXml(){
    	StringBuilder buf=new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>")
		.append("<SubmitReq>")
		.append("<TransactionID>").append(this.transactionId).append("</TransactionID>")
		.append("<ISSPID>").append(WebServiceConstant.getProperties("newland_isspid")).append("</ISSPID>")
		.append("<Username>").append(WebServiceConstant.getProperties("newland_username")).append("</Username>")
		.append("<Password>").append(WebServiceConstant.getProperties("newland_password")).append("</Password>")

		.append("<Recipients>")
	         .append(this.recipients.toRecipientsXml())
		.append("</Recipients>")
		
		.append("<SendClass>").append(this.sendClass).append("</SendClass>")
		
		.append("<Messages>")
	         .append(this.messages.toMessagesXML())
		.append("</Messages>")
		
		.append("<Credential>")
	         .append(this.credential.toCredentialXml())
		.append("</Credential>")
		
		.append("<PGoodsID>").append(WebServiceConstant.getProperties("newland_pgoodsid")).append("</PGoodsID>")
		.append("<ChannelID>").append(WebServiceConstant.getProperties("newland_channelid")).append("</ChannelID>")
		.append("<CustomArea>").append(this.customArea).append("</CustomArea>")
		.append("</SubmitReq>");
		 log.info("++++++++++++++++++++++++ 翼码请求信息"+buf.toString());
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

	public Recipients getRecipients() {
		return recipients;
	}

	public void setRecipients(Recipients recipients) {
		this.recipients = recipients;
	}

	public Credential getCredential() {
		return credential;
	}

	public void setCredential(Credential credential) {
		this.credential = credential;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getSendClass() {
		return sendClass;
	}

	public void setSendClass(String sendClass) {
		this.sendClass = sendClass;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getCustomArea() {
		return customArea;
	}

	public void setCustomArea(String customArea) {
		this.customArea = customArea;
	}

	public Messages getMessages() {
		return messages;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}
	public PassCode getPassCode() {
		return passCode;
	}
	public void setPassCode(PassCode passCode) {
		this.passCode = passCode;
	}

}
