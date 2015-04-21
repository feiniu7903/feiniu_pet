package com.lvmama.passport.processor.impl.client.yaowanggu.model;

public class ProductInfo {
	private String id;//产品id
	private String utilPrice;//产品单价
	private String unitsize;//产品单位数量
	private String price;//产品价格
	private String size;//产品数量
	private String total;//总价
	private String checkInTime;//入住时间 入住时间（客房，套票必须）
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUtilPrice() {
		return utilPrice;
	}
	public void setUtilPrice(String utilPrice) {
		this.utilPrice = utilPrice;
	}
	public String getUnitsize() {
		return unitsize;
	}
	public void setUnitsize(String unitsize) {
		this.unitsize = unitsize;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getCheckInTime() {
		return checkInTime;
	}
	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

}
