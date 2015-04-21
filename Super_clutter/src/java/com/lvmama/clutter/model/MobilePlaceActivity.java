package com.lvmama.clutter.model;

/**
 * 景点活动.
 * @author qinzubo
 *
 */
public class MobilePlaceActivity {

	private Long placeActivityId; // 活动id

    private Long placeId; // 景点id
    
    private String title; // 活动标题
    
    private String contentUrl; // 活动详情url 

    
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

}
