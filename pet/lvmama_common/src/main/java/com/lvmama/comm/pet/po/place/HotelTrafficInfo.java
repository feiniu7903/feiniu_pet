package com.lvmama.comm.pet.po.place;

import java.io.Serializable;

/**
 * 度假酒店的交通信息
 * @author Administrator
 *
 */
public class HotelTrafficInfo implements Serializable {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 5584590990387998494L;
	/** 主键 **/
	private Long id;
	/** 酒店标识 **/
	private Long placeId;
	/** 交通方式 **/
	private String trafficStyle;
	/** 起点 **/
	private String from;
	/** 距离 **/
	private String distance;
	/** 备注 **/
	private String description;
	
	public String getChTrafficInfo() {
		if ("1".equals(trafficStyle)) {
			return "自驾线路";
		}
		if ("2".equals(trafficStyle)) {
			return "火车站";
		}
		if ("3".equals(trafficStyle)) {
			return "机场";
		}
		if ("4".equals(trafficStyle)) {
			return "市中心";
		}
		if ("5".equals(trafficStyle)) {
			return "码头";
		}
		if("6".equals(trafficStyle)){
			return "地铁";
		}
		if("7".equals(trafficStyle)){
			return "长途汽车站";
		}
		return "";
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public String getTrafficStyle() {
		return trafficStyle;
	}
	public void setTrafficStyle(String trafficStyle) {
		this.trafficStyle = trafficStyle;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
}
