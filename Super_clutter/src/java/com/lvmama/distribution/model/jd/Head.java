package com.lvmama.distribution.model.jd;

import com.lvmama.distribution.util.JdUtil;
/**
 *  明文消息头	
 * @author gaoxin
 *
 */
public class Head {
	private String version="1";// 版本号，暂定为1
	private String messageId;// 消息序列号
	private String partnerCode;// 合作伙伴编号
	private String proxyId;// 合作伙伴Id
	private String timeStamp;// 当前时间戳   格式yyyyMMddHHmmssfff
	private String signed;// 消息签名
	public Head(){}
	public Head(String version,String messageId,String partnerCode,String proxyId,String timeStamp){
		this.version=version;
		this.messageId=messageId;
		this.partnerCode=partnerCode;
		this.proxyId=proxyId;
		this.timeStamp=timeStamp;
	}
	public Head(String version,String messageId){
		this.version=version;
		this.messageId=messageId;
	}
	
	/**
	 * 请求报文头
	 * @return
	 */
	public String buildReqHeadToXml(){
		StringBuilder sb=new StringBuilder();
		sb.append("<head>")
		.append(JdUtil.buildXmlElement("version", version))
		.append(JdUtil.buildXmlElement("messageId", messageId))
		.append(JdUtil.buildXmlElement("partnerCode", partnerCode))
		.append(JdUtil.buildXmlElement("proxyId", proxyId))
		.append(JdUtil.buildXmlElement("timeStamp", timeStamp))
		.append(JdUtil.buildXmlElement("signed", signed))
		.append("</head>");
		return sb.toString();
	}
	/**
	 * 响应报文 头
	 * @return
	 */
	public String buildResHeadToXml(){
		StringBuilder sb=new StringBuilder();
		sb.append("<head>")
		.append(JdUtil.buildXmlElement("version", version))
		.append(JdUtil.buildXmlElement("messageId", messageId))
		.append(JdUtil.buildXmlElement("signed", signed))
		.append("</head>");
		return sb.toString();
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getPartnerCode() {
		return partnerCode;
	}
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	public String getProxyId() {
		return proxyId;
	}
	public void setProxyId(String proxyId) {
		this.proxyId = proxyId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getSigned() {
		return signed;
	}
	public void setSigned(String signed) {
		this.signed = signed;
	}

}
