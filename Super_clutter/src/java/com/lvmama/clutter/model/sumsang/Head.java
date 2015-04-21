package com.lvmama.clutter.model.sumsang;

import java.io.Serializable;
import java.util.Arrays;

public class Head implements Serializable {
	private static final long serialVersionUID = -8467319603625900004L;
	private String version;
	private String serial;
	private String skinId;
	private boolean storable;
	private ValidUntil validUntil;
	private String[] keywords;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getSkinId() {
		return skinId;
	}

	public void setSkinId(String skinId) {
		this.skinId = skinId;
	}

	public boolean isStorable() {
		return storable;
	}

	public void setStorable(boolean storable) {
		this.storable = storable;
	}

	public ValidUntil getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(ValidUntil validUntil) {
		this.validUntil = validUntil;
	}

	public String[] getKeywords() {
		return keywords;
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

	@Override
	public String toString() {
		return "Head [version=" + version + ", serial=" + serial + ", skinId="
				+ skinId + ", storable=" + storable + ", validUntil="
				+ validUntil + ", keywords=" + Arrays.toString(keywords) + "]";
	}

}
