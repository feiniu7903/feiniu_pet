package com.lvmama.comm.bee.vo.ebooking;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.lvmama.comm.bee.po.ebooking.EbkProdTimePrice;

public class EbkProdTimePriceAuditCombo {
	private Date day;
	private EbkProdTimePrice ebkProdTimePriceNew;
	private EbkProdTimePrice ebkProdTimePrice;
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public Boolean isFirstDayOfMonth(){
		return "01".equals(new SimpleDateFormat("dd").format(this.day));
	}
	public EbkProdTimePrice getEbkProdTimePriceNew() {
		return ebkProdTimePriceNew;
	}
	public void setEbkProdTimePriceNew(EbkProdTimePrice ebkProdTimePriceNew) {
		this.ebkProdTimePriceNew = ebkProdTimePriceNew;
	}
	public EbkProdTimePrice getEbkProdTimePrice() {
		return ebkProdTimePrice;
	}
	public void setEbkProdTimePrice(EbkProdTimePrice ebkProdTimePrice) {
		this.ebkProdTimePrice = ebkProdTimePrice;
	}
}
