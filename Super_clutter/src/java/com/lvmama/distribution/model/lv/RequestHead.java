package com.lvmama.distribution.model.lv;

import com.lvmama.distribution.util.DistributionUtil;

/**
 * 分销请求报文头对象
 * @author lipengcheng
 *
 */
public class RequestHead {

	/** 版本号*/
	private String version;
	/** 分销商外网出口IP*/
	private String partnerIp;
	/** 分销商ID*/
	private String partnerCode;
	/** 通信签证信息*/
	private String signed;
	
	public RequestHead(){}
	
	public RequestHead(String partnerCode){
		this.partnerCode=partnerCode;
	}
	/**
	 * 请求分销商header的 build方法
	 * @return
	 */
	public String buildXmlStr(){
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<header>");
		xmlStr.append(DistributionUtil.buildXmlElement("version", version));
		xmlStr.append(DistributionUtil.buildXmlElement("partnerIp", partnerIp));
		xmlStr.append(DistributionUtil.buildXmlElement("partnerCode", partnerCode));
		xmlStr.append(DistributionUtil.buildXmlElement("signed", signed));
		xmlStr.append("</header>");
		return xmlStr.toString();
	}
	
	//setter and getter
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getpartnerIp() {
		return partnerIp;
	}
	public void setpartnerIp(String partnerIp) {
		this.partnerIp = partnerIp;
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

}
