package com.lvmama.passport.processor.impl.client.shouxihu.model;

import java.util.Date;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.passport.processor.impl.client.shouxihu.ShouxihuUtil;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 扬州瘦西湖对接--请求报文头
 * @author lipengcheng
 *
 */
public class SXHRequestHead {
	/*这些属性都是解析回调接口所用,与构造报文没有任何关系*/
	private String accountID;//账户ID
	private String serviceName;//服务名
	private String digitalSign;//数字签名
	private String reqTime;//请求时间

	/**
	 * 构造请求报文头
	 * @param method
	 * @return
	 */
	public static String buildHead(String method) {
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<header>");
		xmlStr.append(ShouxihuUtil.buildXmlElement("accountID", WebServiceConstant.getProperties("shouxihu.user")));
		xmlStr.append(ShouxihuUtil.buildXmlElement("serviceName", method));
		xmlStr.append(ShouxihuUtil.buildXmlElement("digitalSign", WebServiceConstant.getProperties("shouxihu.password")));
		xmlStr.append(ShouxihuUtil.buildXmlElement("reqTime", DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", new Date())));
		xmlStr.append("</header>");
		return xmlStr.toString();
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getDigitalSign() {
		return digitalSign;
	}

	public void setDigitalSign(String digitalSign) {
		this.digitalSign = digitalSign;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

}
