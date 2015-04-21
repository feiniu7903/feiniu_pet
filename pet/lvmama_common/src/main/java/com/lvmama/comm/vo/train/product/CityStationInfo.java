package com.lvmama.comm.vo.train.product;

import java.io.Serializable;
import java.util.List;

public class CityStationInfo implements Serializable{
	/**
	 * 城市名中文，例如上海
	 */
	private String city_name;
	private String city_pinyin;
	/**
	 * 1-新增城市；2-更新城市；3-删除城市
	 */
	private int status;
	/**
	 * 城市对应车站名列表
	 */
	private List<String> station_list;
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getCity_pinyin() {
		return city_pinyin;
	}
	public void setCity_pinyin(String city_pinyin) {
		this.city_pinyin = city_pinyin;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<String> getStation_list() {
		return station_list;
	}
	public void setStation_list(List<String> station_list) {
		this.station_list = station_list;
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for(String s : station_list){
			if(sb.length() == 0)
				sb.append(s);
			else
				sb.append(":").append(s);
		}
		return "city_name :" + city_name + "|status:" + status + "|station_list:{" + sb.toString() + "}";
	}
}
