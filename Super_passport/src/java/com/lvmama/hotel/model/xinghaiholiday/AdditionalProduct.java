package com.lvmama.hotel.model.xinghaiholiday;

import java.util.Date;

public class AdditionalProduct {
	private String addiOrderId;// 驴妈妈附加产品订单ID
	private int addiProductId;// 星海假期附加产品ID
	private int addiCount;// 预订数量
	private Date addisDate;// 消费开始日期
	private Date addieDate;// 消费结束日期

	public String getAddiOrderId() {
		return addiOrderId;
	}

	public void setAddiOrderId(String addiOrderId) {
		this.addiOrderId = addiOrderId;
	}

	public int getAddiProductId() {
		return addiProductId;
	}

	public void setAddiProductId(int addiProductId) {
		this.addiProductId = addiProductId;
	}

	public int getAddiCount() {
		return addiCount;
	}

	public void setAddiCount(int addiCount) {
		this.addiCount = addiCount;
	}

	public Date getAddisDate() {
		return addisDate;
	}

	public void setAddisDate(Date addisDate) {
		this.addisDate = addisDate;
	}

	public Date getAddieDate() {
		return addieDate;
	}

	public void setAddieDate(Date addieDate) {
		this.addieDate = addieDate;
	}
}
