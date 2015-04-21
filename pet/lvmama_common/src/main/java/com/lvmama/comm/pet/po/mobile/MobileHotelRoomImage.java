package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.math.BigDecimal;

public class MobileHotelRoomImage implements Serializable{
    private Long mobileHotelRoomImageId;

    private BigDecimal mobileRoomId;

    private Long imgSize;

    private String imgUrl;

    private Long imgType;

    private Long watermark;

    private String imageVersion;

    private String hotelId;

    private String roomId;

    public Long getMobileHotelRoomImageId() {
        return mobileHotelRoomImageId;
    }

    public void setMobileHotelRoomImageId(Long mobileHotelRoomImageId) {
        this.mobileHotelRoomImageId = mobileHotelRoomImageId;
    }

    public BigDecimal getMobileRoomId() {
        return mobileRoomId;
    }

    public void setMobileRoomId(BigDecimal mobileRoomId) {
        this.mobileRoomId = mobileRoomId;
    }

    public Long getImgSize() {
        return imgSize;
    }

    public void setImgSize(Long imgSize) {
        this.imgSize = imgSize;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Long getImgType() {
        return imgType;
    }

    public void setImgType(Long imgType) {
        this.imgType = imgType;
    }

    public Long getWatermark() {
        return watermark;
    }

    public void setWatermark(Long watermark) {
        this.watermark = watermark;
    }

    public String getImageVersion() {
        return imageVersion;
    }

    public void setImageVersion(String imageVersion) {
        this.imageVersion = imageVersion == null ? null : imageVersion.trim();
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId == null ? null : hotelId.trim();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId == null ? null : roomId.trim();
    }
}