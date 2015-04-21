package com.lvmama.passport.wulong.model;

public class Product {
	private String proCode;//产品编码
	private String buyNum;//订购数量
	private String buyPrice;//订购单价
	private String buyTotalPrice;//订购总价
	private String extend;//扩展属性
	public String getProCode() {
		return proCode;
	}
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	public String getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(String buyNum) {
		this.buyNum = buyNum;
	}
	public String getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(String buyPrice) {
		this.buyPrice = buyPrice;
	}
	public String getBuyTotalPrice() {
		return buyTotalPrice;
	}
	public void setBuyTotalPrice(String buyTotalPrice) {
		this.buyTotalPrice = buyTotalPrice;
	}
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}

}
