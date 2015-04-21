package com.lvmama.comm.pet.po.place;

import java.util.Date;

public class PlaceActivity implements java.io.Serializable {
    private Long placeActivityId;

    private Long placeId;

    private Date startTime;

    private Date endTime;

    private String title;

    private String content;

    private String isValid;

    private Long seq;

    public Long getPlaceActivityId() {
        return placeActivityId;
    }

    public void setPlaceActivityId(Long placeActivityId) {
        this.placeActivityId = placeActivityId;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }
}