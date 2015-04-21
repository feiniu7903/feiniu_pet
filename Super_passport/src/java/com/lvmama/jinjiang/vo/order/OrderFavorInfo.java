package com.lvmama.jinjiang.vo.order;

public class OrderFavorInfo {

	private Long favorUniqueId;
	private String favorCode;
	private String favorName;
	private String favorDesc;
	private String beginSaleFormatDate;
	private String endSaleFormatDate;
	public Long getFavorUniqueId() {
		return favorUniqueId;
	}
	public void setFavorUniqueId(Long favorUniqueId) {
		this.favorUniqueId = favorUniqueId;
	}
	public String getFavorCode() {
		return favorCode;
	}
	public void setFavorCode(String favorCode) {
		this.favorCode = favorCode;
	}
	public String getFavorName() {
		return favorName;
	}
	public void setFavorName(String favorName) {
		this.favorName = favorName;
	}
	public String getFavorDesc() {
		return favorDesc;
	}
	public void setFavorDesc(String favorDesc) {
		this.favorDesc = favorDesc;
	}
	public String getBeginSaleFormatDate() {
		return beginSaleFormatDate;
	}
	public void setBeginSaleFormatDate(String beginSaleFormatDate) {
		this.beginSaleFormatDate = beginSaleFormatDate;
	}
	public String getEndSaleFormatDate() {
		return endSaleFormatDate;
	}
	public void setEndSaleFormatDate(String endSaleFormatDate) {
		this.endSaleFormatDate = endSaleFormatDate;
	}
	
	
}
