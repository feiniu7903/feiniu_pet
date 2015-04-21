package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;

public class DayBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String Date;//日期
	private long PricePerDay;//每天价格
	private long  BoardPricePerDay;//额外费用
	private String AvailStatus;//是否可用
	private String AdultBoardTypeId;//成人额外服务类型(包括用餐类型,其他服务类型如SPA...)
	private String AdultBoardTypeDesc;//成人服务描述
	private String ChildBoardTypeId;//儿童额外服务类型(包括用餐类型,其他服务类型如SPA...)
	private String ChildBoardTypeDesc;//儿童服务描述
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public long getPricePerDay() {
		return PricePerDay;
	}
	public void setPricePerDay(long pricePerDay) {
		PricePerDay = pricePerDay;
	}
	public long getBoardPricePerDay() {
		return BoardPricePerDay;
	}
	public void setBoardPricePerDay(long boardPricePerDay) {
		BoardPricePerDay = boardPricePerDay;
	}
	public String getAvailStatus() {
		return AvailStatus;
	}
	public void setAvailStatus(String availStatus) {
		AvailStatus = availStatus;
	}
	public String getAdultBoardTypeId() {
		return AdultBoardTypeId;
	}
	public void setAdultBoardTypeId(String adultBoardTypeId) {
		AdultBoardTypeId = adultBoardTypeId;
	}
	public String getAdultBoardTypeDesc() {
		return AdultBoardTypeDesc;
	}
	public void setAdultBoardTypeDesc(String adultBoardTypeDesc) {
		AdultBoardTypeDesc = adultBoardTypeDesc;
	}
	public String getChildBoardTypeId() {
		return ChildBoardTypeId;
	}
	public void setChildBoardTypeId(String childBoardTypeId) {
		ChildBoardTypeId = childBoardTypeId;
	}
	public String getChildBoardTypeDesc() {
		return ChildBoardTypeDesc;
	}
	public void setChildBoardTypeDesc(String childBoardTypeDesc) {
		ChildBoardTypeDesc = childBoardTypeDesc;
	}
	

}
