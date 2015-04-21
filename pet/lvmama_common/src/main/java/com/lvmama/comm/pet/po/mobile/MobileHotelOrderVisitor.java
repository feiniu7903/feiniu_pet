package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.util.Date;

public class MobileHotelOrderVisitor implements Serializable {
	private static final long serialVersionUID = -7668799111080623456L;

	private Long mobileHotelOrderVisitorId;

    private Long lvHotelOrderId;

    private String name;

    private String mobile;

    private Date createdTime;

    private String userId;

    public Long getMobileHotelOrderVisitorId() {
        return mobileHotelOrderVisitorId;
    }

    public void setMobileHotelOrderVisitorId(Long mobileHotelOrderVisitorId) {
        this.mobileHotelOrderVisitorId = mobileHotelOrderVisitorId;
    }

    public Long getLvHotelOrderId() {
        return lvHotelOrderId;
    }

    public void setLvHotelOrderId(Long lvHotelOrderId) {
        this.lvHotelOrderId = lvHotelOrderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

	@Override
	public String toString() {
		return "MobileHotelOrderVisitor [mobileHotelOrderVisitorId="
				+ mobileHotelOrderVisitorId + ", lvHotelOrderId="
				+ lvHotelOrderId + ", name=" + name + ", mobile=" + mobile
				+ ", createdTime=" + createdTime + ", userId=" + userId + "]";
	}
    
}