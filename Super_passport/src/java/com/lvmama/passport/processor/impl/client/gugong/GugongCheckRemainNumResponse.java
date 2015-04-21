package com.lvmama.passport.processor.impl.client.gugong;

public class GugongCheckRemainNumResponse {
	private String intodate;
	private String idnumber;
	private int remainnum;
	private int resultcode;

	public String getIntodate() {
		return intodate;
	}

	public void setIntodate(String intodate) {
		this.intodate = intodate;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public int getRemainnum() {
		return remainnum;
	}

	public void setRemainnum(int remainnum) {
		this.remainnum = remainnum;
	}

	public int getResultcode() {
		return resultcode;
	}

	public void setResultcode(int resultcode) {
		this.resultcode = resultcode;
	}

}
