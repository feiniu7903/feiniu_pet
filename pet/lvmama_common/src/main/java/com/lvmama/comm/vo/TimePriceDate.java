package com.lvmama.comm.vo;

import java.io.Serializable;

public class TimePriceDate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8889196491139843491L;
	public String sdate;
	public String lvmamaPrice;

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getLvmamaPrice() {
		return lvmamaPrice;
	}

	public void setLvmamaPrice(String lvmamaPrice) {
		this.lvmamaPrice = lvmamaPrice;
	}
}
