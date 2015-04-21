package com.lvmama.passport.processor.impl.client.newland.model;

import java.util.List;

public class CredentialInfo {
	private String transactionId;
	private String activityDescription;
	private String contentMd5;
	private String assistNumberMd5;
	private String assistNumber;
	private String credentialAmount;
	private String printText;
	private String printLinks;
	private String beginUseTime;
	private String expiryTime;
	private String credentialStatus;
	private String spareField;
	private String useMode;
	private String credentialPrice;
	private List<PosList> posLists;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

	public String getContentMd5() {
		return contentMd5;
	}

	public void setContentMd5(String contentMd5) {
		this.contentMd5 = contentMd5;
	}

	public String getAssistNumberMd5() {
		return assistNumberMd5;
	}

	public void setAssistNumberMd5(String assistNumberMd5) {
		this.assistNumberMd5 = assistNumberMd5;
	}

	public String getAssistNumber() {
		return assistNumber;
	}

	public void setAssistNumber(String assistNumber) {
		this.assistNumber = assistNumber;
	}

	public String getCredentialAmount() {
		return credentialAmount;
	}

	public void setCredentialAmount(String credentialAmount) {
		this.credentialAmount = credentialAmount;
	}

	public String getPrintText() {
		return printText;
	}

	public void setPrintText(String printText) {
		this.printText = printText;
	}


	public String getPrintLinks() {
		return printLinks;
	}

	public void setPrintLinks(String printLinks) {
		this.printLinks = printLinks;
	}

	public String getBeginUseTime() {
		return beginUseTime;
	}

	public void setBeginUseTime(String beginUseTime) {
		this.beginUseTime = beginUseTime;
	}

	public String getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}

	public String getCredentialStatus() {
		return credentialStatus;
	}

	public void setCredentialStatus(String credentialStatus) {
		this.credentialStatus = credentialStatus;
	}

	public String getSpareField() {
		return spareField;
	}

	public void setSpareField(String spareField) {
		this.spareField = spareField;
	}

	public String getUseMode() {
		return useMode;
	}

	public void setUseMode(String useMode) {
		this.useMode = useMode;
	}

	public List<PosList> getPosLists() {
		return posLists;
	}

	public void setPosLists(List<PosList> posLists) {
		this.posLists = posLists;
	}

	public String getCredentialPrice() {
		return credentialPrice;
	}

	public void setCredentialPrice(String credentialPrice) {
		this.credentialPrice = credentialPrice;
	}

}
