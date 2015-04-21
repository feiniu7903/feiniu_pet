package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;

public class MobileHotelDest implements Serializable{
    private String cityCode;

    private Long placeId;

    private String cityName;

    private String provinceId;

    private String hotelgeoNodeVersion;

    private String provinceName;

    private String pinyin;

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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId == null ? null : provinceId.trim();
    }

    public String getHotelgeoNodeVersion() {
        return hotelgeoNodeVersion;
    }

    public void setHotelgeoNodeVersion(String hotelgeoNodeVersion) {
        this.hotelgeoNodeVersion = hotelgeoNodeVersion == null ? null : hotelgeoNodeVersion.trim();
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }
}