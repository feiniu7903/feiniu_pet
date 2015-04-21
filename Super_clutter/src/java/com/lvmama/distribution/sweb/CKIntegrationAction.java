package com.lvmama.distribution.sweb;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.clutter.web.BaseAction;
import com.lvmama.distribution.service.DistributionForCKDeviceService;
import com.lvmama.distribution.util.RequestUtil;

public class CKIntegrationAction extends BaseAction {
	private static final Log log = LogFactory.getLog(CKIntegrationAction.class);
	private static final long serialVersionUID = 6350289068604922781L;
	private DistributionForCKDeviceService distributionForCKDeviceService;
	private String requestXml;
	
	@Action("/ckdevice/checkReservation")
	public void checkReservation() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String requestIp = RequestUtil.getIpAddr(request);
		log.info("checkReservation partnerIp:" + requestIp);
		String responseXml = distributionForCKDeviceService.checkReservation(requestXml);
		sendXmlResult(responseXml);
	}
	
	@Action("/ckdevice/getPrintInfo")
	public void getPrintInfo() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String requestIp = RequestUtil.getIpAddr(request);
		log.info("getPrintInfo partnerIp:" + requestIp);
		String responseXml = distributionForCKDeviceService.getPrintInfo(requestXml);
		sendXmlResult(responseXml);
	}
	
	
	@Action("/ckdevice/confirmPrint")
	public void confirmPrint() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String requestIp = RequestUtil.getIpAddr(request);
		log.info("confirmPrint partnerIp:" + requestIp);
		String responseXml = distributionForCKDeviceService.confirmPrint(requestXml);
		sendXmlResult(responseXml);
	}
	
	
	@Action("/ckdevice/queryProductList")
	public void queryProductList() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String requestIp = RequestUtil.getIpAddr(request);
		log.info("queryProductList partnerIp:" + requestIp);
		String responseXml = distributionForCKDeviceService.queryProductList(requestXml);
		sendXmlResult(responseXml);
	}
	
	@Action("/ckdevice/createOrder")
	public void createOrder() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String requestIp = RequestUtil.getIpAddr(request);
		log.info("createOrder partnerIp:" + requestIp);
		String responseXml = distributionForCKDeviceService.createOrder(requestXml);
		sendXmlResult(responseXml);
	}
	
	@Action("/ckdevice/queryOrder")
	public void queryOrder() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String requestIp = RequestUtil.getIpAddr(request);
		log.info("queryOrder partnerIp:" + requestIp);
		String responseXml = distributionForCKDeviceService.queryOrder(requestXml);
		sendXmlResult(responseXml);
	}
	
	@Action("/ckdevice/confirmPayment")
	public void confirmPayment() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String requestIp = RequestUtil.getIpAddr(request);
		log.info("confirmPayment partnerIp:" + requestIp);
		String responseXml = distributionForCKDeviceService.confirmPayment(requestXml);
		sendXmlResult(responseXml);
	}
	
	@Action("/ckdevice/requestPaymentChecksum")
	public void requestPaymentChecksum() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String requestIp = RequestUtil.getIpAddr(request);
		log.info("requestPaymentChecksum partnerIp:" + requestIp);
		String responseXml = distributionForCKDeviceService.requestPaymentChecksum(requestXml);
		sendXmlResult(responseXml);
	}

	public String getRequestXml() {
		return requestXml;
	}

	public void setRequestXml(String requestXml) {
		this.requestXml = requestXml;
	}

	public void setDistributionForCKDeviceService(
			DistributionForCKDeviceService distributionForCKDeviceService) {
		this.distributionForCKDeviceService = distributionForCKDeviceService;
	}


}
