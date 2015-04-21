package com.lvmama.comm.pet.po.place;

import java.io.Serializable;

public class PlacePlaneModel implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7535870052690790741L;

	private Long placeModelId;

    private String planeName;

    private String planeCode;

    private String placeType;

    private Long minSeat;

    private Long maxSeat;

    public Long getPlaceModelId() {
        return placeModelId;
    }

    public void setPlaceModelId(Long placeModelId) {
        this.placeModelId = placeModelId;
    }

    public String getPlaneName() {
        return planeName;
    }

    public void setPlaneName(String planeName) {
        this.planeName = planeName == null ? null : planeName.trim();
    }

    public String getPlaneCode() {
        return planeCode;
    }

    public void setPlaneCode(String planeCode) {
        this.planeCode = planeCode == null ? null : planeCode.trim();
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType == null ? null : placeType.trim();
    }

    public Long getMinSeat() {
        return minSeat;
    }

    public void setMinSeat(Long minSeat) {
        this.minSeat = minSeat;
    }

    public Long getMaxSeat() {
        return maxSeat;
    }

    public void setMaxSeat(Long maxSeat) {
        this.maxSeat = maxSeat;
    }
}