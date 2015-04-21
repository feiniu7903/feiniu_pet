package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MobilePushLocation implements Serializable {
	private static final long serialVersionUID = -5180187253582997520L;

	private Long mobilePushLocationId;

    private Long mobilePushDeviceId;

    private String cityId;

    private String provinceId;

    private String ip;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Date createdTime;

    public Long getMobilePushLocationId() {
        return mobilePushLocationId;
    }

    public void setMobilePushLocationId(Long mobilePushLocationId) {
        this.mobilePushLocationId = mobilePushLocationId;
    }

    public Long getMobilePushDeviceId() {
        return mobilePushDeviceId;
    }

    public void setMobilePushDeviceId(Long mobilePushDeviceId) {
        this.mobilePushDeviceId = mobilePushDeviceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId == null ? null : cityId.trim();
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId == null ? null : provinceId.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}