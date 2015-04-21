package com.lvmama.elong.model;

import java.io.Serializable;

public class HotelCommentCondition implements Serializable {
	private static final long serialVersionUID = -3429349355872423985L;
	private String cityId;
	private String hotelIds;
	private String sortBy = "CreateTime";
	private String sortType = "Desc";
	private long pageSize = 20;
	private long pageIndex = 1;
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getHotelIds() {
		return hotelIds;
	}
	public void setHotelIds(String hotelIds) {
		this.hotelIds = hotelIds;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public long getPageSize() {
		return pageSize;
	}
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	public long getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(long pageIndex) {
		this.pageIndex = pageIndex;
	}
	
}
