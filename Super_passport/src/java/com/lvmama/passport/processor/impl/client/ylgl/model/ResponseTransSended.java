package com.lvmama.passport.processor.impl.client.ylgl.model;

/**
 * 交易送达信息的容器对象
 * @author lipengcheng
 * @date Nov 15, 2011
 */
public class ResponseTransSended {
	
	private String requestType;// 同步类型
	private ErDeliverReportSyncRequest erDeliverReportSyncRequest;//响应内容
	
	
	
	public ErDeliverReportSyncRequest getErDeliverReportSyncRequest() {
		return erDeliverReportSyncRequest;
	}

	public void setErDeliverReportSyncRequest(ErDeliverReportSyncRequest erDeliverReportSyncRequest) {
		this.erDeliverReportSyncRequest = erDeliverReportSyncRequest;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}




}
