package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;

public class MobileHotelLandmark implements Serializable{
    private Long mobileHotelLandmarkId;

    private String cityCode;

    private Long placeId;

    private String locationId;

    private String locationName;

    private String locationType;

    private String pinyin;

    public Long getMobileHotelLandmarkId() {
        return mobileHotelLandmarkId;
    }

    public void setMobileHotelLandmarkId(Long mobileHotelLandmarkId) {
        this.mobileHotelLandmarkId = mobileHotelLandmarkId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId == null ? null : locationId.trim();
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName == null ? null : locationName.trim();
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType == null ? null : locationType.trim();
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }
}