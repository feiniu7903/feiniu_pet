package com.lvmama.distribution.model.lv;

import com.lvmama.distribution.util.DistributionUtil;

/**
 * 分销响应报文头对象
 * @author lipengcheng
 *
 */
public class ResponseHead {

	/** 错误代码*/
	private String code;
	/** 错误描述*/
	private String describe;
	
	public ResponseHead() {
	}

	public ResponseHead(String code, String describe) {
		this.code = code;
		this.describe = describe;
	}
	
	public String buildXmlStr(){
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<header>");
		xmlStr.append(DistributionUtil.buildXmlElement("code", code));
		xmlStr.append(DistributionUtil.buildXmlElement("describe", describe));
		xmlStr.append("</header>");
		return xmlStr.toString();
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
}
