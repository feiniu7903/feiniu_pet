package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.util.List;


/**
 * 景点查询结果
 * 
 * @author YangGan
 *
 */
public class PlaceSearchJOSN implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer pageSize = 10;
	private Integer totalPage;
	//城市ID
	private String cityId;
	//当前页数
	private Integer page;
	//返回的结果总数
	private Integer totalResultSize ;
	//返回的景点列表
	private List<Place> placeListJson;
	
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getTotalResultSize() {
		return totalResultSize;
	}
	public void setTotalResultSize(Integer totalResultSize) {
		this.totalResultSize = totalResultSize;
	}
	public List<Place> getPlaceListJson() {
		return placeListJson;
	}
	public void setPlaceListJson(List<Place> placeListJson) {
		this.placeListJson = placeListJson;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalPage(){
		if(this.totalResultSize%pageSize == 0){
			this.totalPage = this.totalResultSize / pageSize;
		}else{
			this.totalPage = this.totalResultSize / pageSize + 1;
		}
		return this.totalPage;
	}
	public boolean isLastPage(){
		if(page >= this.getTotalPage() ){
			return true;
		}else{
			return false;
		}
	}
	
}
