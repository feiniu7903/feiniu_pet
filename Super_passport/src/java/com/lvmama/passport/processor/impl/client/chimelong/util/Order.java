package com.lvmama.passport.processor.impl.client.chimelong.util;

import java.util.Map;

public class Order {

	private String ver_no;
	private String mer_no;
	private String orderInfo;
	private String sign;
	private String tk = "";
	private String tkType = "";
	private String validTime = "";
	private String invalidTime = "";
	private String maxiTime;
	private String typeCode = "";
	private String validDate="";
	private boolean isMaxi;
	private Map<String,String> maXiTimes;
	public Map<String, String> getMaXiTimes() {
		return maXiTimes;
	}

	public void setMaXiTimes(Map<String, String> maXiTimes) {
		this.maXiTimes = maXiTimes;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getTk() {
		return tk;
	}

	public void setTk(String tk) {
		this.tk = tk;
	}

	public String getTkType() {
		return tkType;
	}

	public void setTkType(String tkType) {
		this.tkType = tkType;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public String getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(String invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getVer_no() {
		return ver_no;
	}

	public void setVer_no(String verNo) {
		ver_no = verNo;
	}

	public String getMer_no() {
		return mer_no;
	}

	public void setMer_no(String merNo) {
		mer_no = merNo;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getMaxiTime() {
		return maxiTime;
	}

	public void setMaxiTime(String maxiTime) {
		this.maxiTime = maxiTime;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public boolean isMaxi() {
		return isMaxi;
	}

	public void setMaxi(boolean isMaxi) {
		this.isMaxi = isMaxi;
	}

}
