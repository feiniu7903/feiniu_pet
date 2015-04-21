package com.lvmama.comm.pet.po.place;

import java.io.Serializable;
import java.util.Date;

public class PlaceHotelRoom implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long roomId;    //房型 ID
	private Long placeId;
	private String roomName; //房型名称
	private Long roomNum;  		//房型数量
	private String roomFloor; //楼层
	private String isNonsmoking; //无烟房 (1:有无烟楼层   2:有无烟房  3:该房可做无烟处理  4:无)
	private String addBed; //是否加床 (1:否  2:是)
	private String addBedCost; //	加床费用
	private String broadband; 	//是否有宽带(1:免费   2:无   3:收费)
	private String broadbandCost;  //宽带费用
	private String isWindow; //是否有窗户(1:有窗  2:无窗  3:部分无窗)
	private String maxLive; //最大入住数
	private String buildingArea; //建筑面积
	private String bigBedWide; //大床宽度(0为没有大床)
	private String doubleBedWide;   //双床宽度(0为没有双床)
	private String roomRecommend; //房屋介绍
	private Date roomCreateTime; //创建时间
	private String roomRecommendNoHtml;
	private String seqNum;
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public Long getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(Long roomNum) {
		this.roomNum = roomNum;
	}
	public String getRoomFloor() {
		return roomFloor;
	}
	public void setRoomFloor(String roomFloor) {
		this.roomFloor = roomFloor;
	}
	public String getIsNonsmoking() {
		return isNonsmoking;
	}
	public void setIsNonsmoking(String isNonsmoking) {
		this.isNonsmoking = isNonsmoking;
	}
	public String getAddBed() {
		return addBed;
	}
	public void setAddBed(String addBed) {
		this.addBed = addBed;
	}
	public String getAddBedCost() {
		return addBedCost;
	}
	public void setAddBedCost(String addBedCost) {
		this.addBedCost = addBedCost;
	}
	public String getBroadband() {
		return broadband;
	}
	public void setBroadband(String broadband) {
		this.broadband = broadband;
	}
	public String getBroadbandCost() {
		return broadbandCost;
	}
	public void setBroadbandCost(String broadbandCost) {
		this.broadbandCost = broadbandCost;
	}
	public String getIsWindow() {
		return isWindow;
	}
	public void setIsWindow(String isWindow) {
		this.isWindow = isWindow;
	}
	public String getMaxLive() {
		return maxLive;
	}
	public void setMaxLive(String maxLive) {
		this.maxLive = maxLive;
	}
	public String getBuildingArea() {
		return buildingArea;
	}
	public void setBuildingArea(String buildingArea) {
		this.buildingArea = buildingArea;
	}
	public String getBigBedWide() {
		return bigBedWide;
	}
	public void setBigBedWide(String bigBedWide) {
		this.bigBedWide = bigBedWide;
	}
	public String getDoubleBedWide() {
		return doubleBedWide;
	}
	public void setDoubleBedWide(String doubleBedWide) {
		this.doubleBedWide = doubleBedWide;
	}
	public String getRoomRecommend() {
		return roomRecommend;
	}
	public void setRoomRecommend(String roomRecommend) {
		this.roomRecommend = roomRecommend;
	}
	public Date getRoomCreateTime() {
		return roomCreateTime;
	}
	public void setRoomCreateTime(Date roomCreateTime) {
		this.roomCreateTime = roomCreateTime;
	}
	public String getRoomRecommendNoHtml() {
		return roomRecommendNoHtml;
	}
	public void setRoomRecommendNoHtml(String roomRecommendNoHtml) {
		this.roomRecommendNoHtml = roomRecommendNoHtml;
	}
	public String getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
}
