package com.lvmama.passport.processor.impl.client.newland.model;

/**
 * 
 * @author chenlinjun
 * 
 */
public class Recipients {
	private String phone_number;

	public String toRecipientsXml(){
		StringBuilder buf=new StringBuilder();
		buf.append("<Number>").append(this.phone_number).append("</Number>");
		return buf.toString();
	}
	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
}
