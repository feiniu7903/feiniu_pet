package com.lvmama.comm.pet.po.place;

import java.io.Serializable;

public class PlaceSearchPinyin implements Serializable{
	private static final long serialVersionUID = 6453437471323272005L;

	private Long placeSearchPinyinId;

    private Long objectId;

    private String objectType;

    private String pinYin;

    private String jianPin;

    private String objectName;

    public Long getPlaceSearchPinyinId() {
        return placeSearchPinyinId;
    }

    public void setPlaceSearchPinyinId(Long placeSearchPinyinId) {
        this.placeSearchPinyinId = placeSearchPinyinId;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public String getJianPin() {
        return jianPin;
    }

    public void setJianPin(String jianPin) {
        this.jianPin = jianPin;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
}