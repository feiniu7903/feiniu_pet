package com.lvmama.pet.payment.callback.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.utils.Dom4jUtil;
import com.lvmama.pet.payment.callback.data.ByPayCallbackData;
import com.lvmama.pet.payment.callback.data.CallbackData;


@Results( {
	@Result(name = "order_success", location = "/WEB-INF/pages/pay/by_callback.ftl", type = "freemarker")
	})
public class ByPayCallBackAction extends CallbackBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8926915075581560944L;

	/**
	 * LOG.
	 */
	private static final Logger log = Logger.getLogger(ByPayCallBackAction.class);
	/**
	 * 通知返回的报文.
	 */
    private String xmlContent = "";	
	/**
	 * 百付支付回调.
	 * 
	 * @return /WEB-INF/pages/pay/order_pay_detail.ftl
	 */
	@Action("/pay/byPayCallback")
	public String execute() {
		String returnResult = callback(true);
		String returnXml = xmlContent.toString();
		returnXml = returnXml.replace("MTransNotify.Req", "MTransNotify.Rsp");
		if ("order_success".equalsIgnoreCase(returnResult)) {
			returnXml = returnXml.replace("</upbp>","<respCode>00</respCode><respDesc>通知成功</respDesc></upbp>");
		} else {
			returnXml = returnXml.replace("</upbp>","<respCode>00</respCode><respDesc>通知失败</respDesc></upbp>");
		}
		try {
			log.info("Notify:" + returnXml);
			PrintWriter writerXml = getResponse().getWriter();
			writerXml.write(returnXml);
			writerXml.close();
		} catch (IOException e) {
			e.printStackTrace();
			log.info("ByPay Notify Connection Broker error");
		}
		return returnResult;
	}

	
	@Override
	CallbackData getCallbackData() {
		try {
			InputStream inputStream = getRequest().getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				xmlContent += tempStr;
			}
			reader.close();
			inputStream.close();
		} catch (IOException e1) {
			log.info("byPayCallback data is error!");
		}
		log.info("byPayCallback Data:"+xmlContent);
		ByPayCallbackData byPayCallbackData =	new ByPayCallbackData(Dom4jUtil.AnalyticXml(xmlContent));
		return byPayCallbackData;
	}

	/**
	 * 获取通知返回的报文.
	 * 
	 * @return
	 */
	public String getXmlContent() {
		return xmlContent;
	}

	/**
	 * 设置通知返回的报文.
	 * 
	 * @param xmlContent
	 */
	public void setXmlContent(String xmlContent) {
		this.xmlContent = xmlContent;
	}
	
}
