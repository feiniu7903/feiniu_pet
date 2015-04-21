package com.lvmama.passport.wulong.model;

import java.util.List;

public class OrderInfo {
	
	private String type;;//订单类型 1 普通订单2 预订订单
	private String orderId;;//订单编号
	private String realPrice;//订单实际成交价格
	private String contactName;//联系人
	private String contactPhone;//联系电话
	private String idcardCode;//身份证号码
	private String arriveDate;//到达日期
	private String provinceName;//省份
	private String cityName;//城市
	private List<Product> productLists;//产品列表
	private List<Tourist> touristLists;//人员列表
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(String realPrice) {
		this.realPrice = realPrice;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getIdcardCode() {
		return idcardCode;
	}
	public void setIdcardCode(String idcardCode) {
		this.idcardCode = idcardCode;
	}
	public String getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public List<Product> getProductLists() {
		return productLists;
	}
	public void setProductLists(List<Product> productLists) {
		this.productLists = productLists;
	}
	public List<Tourist> getTouristLists() {
		return touristLists;
	}
	public void setTouristLists(List<Tourist> touristLists) {
		this.touristLists = touristLists;
	}
}
