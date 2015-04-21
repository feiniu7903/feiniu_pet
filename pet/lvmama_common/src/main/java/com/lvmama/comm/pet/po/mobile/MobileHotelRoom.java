package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;

public class MobileHotelRoom implements Serializable{
    private Long mobileRoomId;

    private String roomId;

    private String hotelId;

    private String name;

    private String area;

    private String floor;

    private Short broadnetaccess;

    private Short broadnetfee;

    private String bedtype;

    private String comments;

    private String description;

    private String roomNodeVersion;

    public Long getMobileRoomId() {
        return mobileRoomId;
    }

    public void setMobileRoomId(Long mobileRoomId) {
        this.mobileRoomId = mobileRoomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId == null ? null : roomId.trim();
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId == null ? null : hotelId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor == null ? null : floor.trim();
    }

    public Short getBroadnetaccess() {
        return broadnetaccess;
    }

    public void setBroadnetaccess(Short broadnetaccess) {
        this.broadnetaccess = broadnetaccess;
    }

    public Short getBroadnetfee() {
        return broadnetfee;
    }

    public void setBroadnetfee(Short broadnetfee) {
        this.broadnetfee = broadnetfee;
    }

    public String getBedtype() {
        return bedtype;
    }

    public void setBedtype(String bedtype) {
        this.bedtype = bedtype == null ? null : bedtype.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getRoomNodeVersion() {
        return roomNodeVersion;
    }

    public void setRoomNodeVersion(String roomNodeVersion) {
        this.roomNodeVersion = roomNodeVersion == null ? null : roomNodeVersion.trim();
    }
}