package com.lvmama.comm.pet.po.sms;

import java.io.Serializable;

public class SmsMMS implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4212595528120056287L;
	private String text;
	private byte[] pics;
	private byte[] mids;
	
	public SmsMMS(String text, byte[] pics, byte[] mids) {
		this.text = text;
		this.pics = pics;
		this.mids = mids;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public byte[] getPics() {
		return pics;
	}

	public void setPics(byte[] pics) {
		this.pics = pics;
	}

	public byte[] getMids() {
		return mids;
	}

	public void setMids(byte[] mids) {
		this.mids = mids;
	}
}
