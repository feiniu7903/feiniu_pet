package com.lvmama.fenxiao.model.tuangou;

import java.io.Serializable;

public class BaiduUser implements Serializable {
	private static final long serialVersionUID = -8105795869010884903L;
	private String uid;
	private String uname;
	private String portrait;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	@Override
	public String toString() {
		return "BaiduUser [uid=" + uid + ", uname=" + uname + ", portrait=" + portrait + "]";
	}
}
