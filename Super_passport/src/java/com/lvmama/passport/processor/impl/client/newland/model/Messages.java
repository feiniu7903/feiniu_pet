package com.lvmama.passport.processor.impl.client.newland.model;

public class Messages {
	private Sms sms;
	private Mms mms;

	public String toMessagesXML() {
		StringBuilder buf = new StringBuilder();
		buf.append("<Sms>").append(this.sms.toSmsxXML()).append("</Sms>")
		.append("<Mms>").append(this.mms.toMmsxXML()).append("</Mms>");
		return buf.toString();
	}

	public Sms getSms() {
		return sms;
	}

	public void setSms(Sms sms) {
		this.sms = sms;
	}

	public Mms getMms() {
		return mms;
	}

	public void setMms(Mms mms) {
		this.mms = mms;
	}
}
