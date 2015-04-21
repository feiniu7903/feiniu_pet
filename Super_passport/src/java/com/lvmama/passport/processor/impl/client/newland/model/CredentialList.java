package com.lvmama.passport.processor.impl.client.newland.model;

import java.util.List;

public class CredentialList {
	private List<CredentialInfo> credentialInfos;
	public String toCredentialListXml(){
		StringBuilder buf = new StringBuilder();
		for(CredentialInfo info:credentialInfos){
		buf.append("<CredentialInfo>")
        	.append("<TransactionID>").append(info.getTransactionId()).append("</TransactionID>")
        	.append("<ActivityDescription>").append(info.getActivityDescription()).append("</ActivityDescription>")
        	.append("<ContentMd5>").append(info.getContentMd5()).append("</ContentMd5>")
        	.append("<AssistNumberMd5>").append(info.getAssistNumberMd5()).append("</AssistNumberMd5>")
        	.append("<AssistNumber>").append(info.getAssistNumber()).append("</AssistNumber>")
        	.append("<CredentialAmount>").append(info.getCredentialAmount()).append("</CredentialAmount>")
        	.append("<PrintText>").append(info.getPrintText()).append("</PrintText>")
        	.append("<PrintLinks>").append(info.getPrintLinks()).append("</PrintLinks>")
        	.append("<BeginUseTime>").append(info.getBeginUseTime()).append("</BeginUseTime>")
        	.append("<ExpiryTime>").append(info.getExpiryTime()).append("</ExpiryTime>")
        	.append("<CredentialStatus>").append(info.getCredentialStatus()).append("</CredentialStatus>")
        	.append("<SpareField>").append(info.getSpareField()).append("</SpareField>")
        	.append("<CredentialPrice>").append(info.getCredentialPrice()).append("</CredentialPrice>")
        	.append("<UseMode>").append(info.getUseMode()).append("</UseMode>")
        	
        	.append("<PosList>");
        		for(PosList pos:info.getPosLists()){
        		 buf.append("<PosID>").append(pos.getPosId()).append("</PosID>");
        		}
        	buf.append("</PosList>");
        buf.append("</CredentialInfo>");
		}
		return buf.toString();
	}
	
	public List<CredentialInfo> getCredentialInfos() {
		return credentialInfos;
	}

	public void setCredentialInfos(List<CredentialInfo> credentialInfos) {
		this.credentialInfos = credentialInfos;
	}
}
