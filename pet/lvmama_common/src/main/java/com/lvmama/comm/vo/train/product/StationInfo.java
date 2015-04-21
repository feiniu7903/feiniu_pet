package com.lvmama.comm.vo.train.product;

import java.io.Serializable;

/**
 * 车站信息类
 * @author XuSemon
 * @date 2013-8-12
 */
public class StationInfo implements Serializable{
	/**
	 * 车站名中文，例如南京
	 */
	private String station_name;
	/**
	 * 车站名拼音全称，例如nanjing
	 */
	private String station_pinyin;
	/**
	 * 车站名拼音首字母，例如nj
	 */
	private String station_index;
	/**
	 * 1-新增车站；2-更新车站；3-删除车站
	 */
	private int status;
	public String getStation_name() {
		return station_name;
	}
	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}
	public String getStation_pinyin() {
		return station_pinyin;
	}
	public void setStation_pinyin(String station_pinyin) {
		this.station_pinyin = station_pinyin;
	}
	public String getStation_index() {
		return station_index;
	}
	public void setStation_index(String station_index) {
		this.station_index = station_index;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString(){
		return "station_name:"+station_name+"|station_pinyin:"+station_pinyin+"|station_index:"+station_index
				+"|status:"+status;
	}
}
