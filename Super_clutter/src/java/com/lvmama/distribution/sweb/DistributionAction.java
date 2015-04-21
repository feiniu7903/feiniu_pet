package com.lvmama.distribution.sweb;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.dom4j.io.SAXReader;

import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.bee.po.distribution.DistributionMessage;
import com.lvmama.distribution.service.DistributionCommonService;
import com.lvmama.distribution.service.impl.DistributionPushService.EVENT_TYPE;

public class DistributionAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4707049745989267171L;
	private String requestXml;
	private String responseXml="";
	String wsdl = "http://192.168.0.232/clutter/services/distributionService?wsdl";
	private String prodId;
	private DistributionCommonService distributionCommonService;
	
	
	@Action("/jingdong/testPush")
	public void distriPush() {
		HttpServletRequest request=ServletActionContext.getRequest();
		 try {
			 byte buffer[] = new byte[64 * 1024];
			 InputStream in = request.getInputStream();
			 in.read(buffer);
			 log.info("request xml:"+new String(buffer));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	//http://super.lvmama.com/clutter/commonProduct/testPush.do?prodId=72908
	@Action("/commonProduct/testPush")
	public void productPush() {
		log.info("Disribution product push pId: " +prodId );
		
		DistributionMessage distributionMessage = new DistributionMessage();
//				distributionMessage.setStatus(PUSH_STATUS.unpushed.name());
		distributionMessage.setEventType("product");
		distributionMessage.setObjectId(Long.valueOf(prodId));
		List<DistributionMessage> distributionMessages = distributionCommonService.queryMessageByMsgParams(distributionMessage);
		for (DistributionMessage message : distributionMessages) {
			if(EVENT_TYPE.offLine.name().equals( message.getEventType())){
				distributionCommonService.pushOffLine(message);
			}else{
				distributionCommonService.pushProduct(message);
			}
		}
	}
	String getXmlStr() throws Exception{
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("request.xml");
		SAXReader reader = new SAXReader();
		org.dom4j.Document document = reader.read(inputStream);
		return document.asXML();
	}

	public String getRequestXml() {
		return requestXml;
	}

	public void setRequestXml(String requestXml) {
		this.requestXml = requestXml;
	}

	public String getResponseXml() {
		return responseXml;
	}

	public void setResponseXml(String responseXml) {
		this.responseXml = responseXml;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}


	public void setDistributionCommonService(
			DistributionCommonService distributionCommonService) {
		this.distributionCommonService = distributionCommonService;
	}
	
	
}
