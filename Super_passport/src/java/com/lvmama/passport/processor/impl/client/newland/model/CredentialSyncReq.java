package com.lvmama.passport.processor.impl.client.newland.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CredentialSyncReq {
	private static final Log log = LogFactory.getLog(CredentialSyncReq.class);
	private String reqSequence;
	private String currentPage;
	private String pageSize;
	private String lastSyncTime;
	private String factSize;
	private String syncTime;
	private CredentialList CredentialList;
	private Status status; 
	public String toCredentialSyncReqXml(){
    	StringBuilder buf=new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>")
		.append("<CredentialSyncRes>")
			.append("<ResSequence>").append(this.reqSequence).append("</ResSequence>")
			.append("<CurrentPage>").append(this.currentPage).append("</CurrentPage>")
			.append("<PageSize>").append(this.pageSize).append("</PageSize>")
			.append("<FactSize>").append(this.factSize).append("</FactSize>")
			.append("<SyncTime>").append(this.syncTime).append("</SyncTime>")
	
			.append("<CredentialList>")
		         .append(this.CredentialList.toCredentialListXml())
			.append("</CredentialList>")
			
			.append("<Status>")
		         .append(this.status.toStatusXml())
			.append("</Status>")
		.append("</CredentialSyncRes>");
		log.info("++++++++++++++++++++++++ 翼码异步码响应XML"+buf.toString());
    	return buf.toString();
	}
	
	public String getReqSequence() {
		return reqSequence;
	}

	public void setReqSequence(String reqSequence) {
		this.reqSequence = reqSequence;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getLastSyncTime() {
		return lastSyncTime;
	}

	public void setLastSyncTime(String lastSyncTime) {
		this.lastSyncTime = lastSyncTime;
	}

	public String getFactSize() {
		return factSize;
	}

	public void setFactSize(String factSize) {
		this.factSize = factSize;
	}

	public String getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(String syncTime) {
		this.syncTime = syncTime;
	}

	public CredentialList getCredentialList() {
		return CredentialList;
	}

	public void setCredentialList(CredentialList credentialList) {
		CredentialList = credentialList;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}



}
