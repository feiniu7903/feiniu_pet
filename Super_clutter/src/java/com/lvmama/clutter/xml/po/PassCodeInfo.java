package com.lvmama.clutter.xml.po;

import com.lvmama.comm.vo.Constant;


public class PassCodeInfo {
	private String codeImageUrl;
	
	private String smsContent;
	
	public String getCodeImageUrl() {
		return Constant.PIC_HOST+"/"+codeImageUrl;
	}
	public void setCodeImageUrl(String codeImageUrl) {
		this.codeImageUrl = codeImageUrl;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
}
