package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

public class TimePriceDate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9070532102633697982L;
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
