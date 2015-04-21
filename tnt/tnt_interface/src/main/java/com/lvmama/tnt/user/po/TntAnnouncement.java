package com.lvmama.tnt.user.po;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TntAnnouncement implements java.io.Serializable {
	
	private static final long serialVersionUID = 3736831018788267516L;
	
	private java.lang.Long announcementId;
	private java.lang.String title;
	private java.lang.String body;
	private java.util.Date publishtime;
	private java.lang.String formatdate;
	
	private java.lang.String beginpublishdate;
	private java.lang.String endpublishdate;
	
	public java.lang.String getEndpublishdate() {
		return endpublishdate;
	}

	public void setEndpublishdate(java.lang.String endpublishdate) {
		this.endpublishdate = endpublishdate;
	}

	public java.lang.String getBeginpublishdate() {
		return beginpublishdate;
	}

	public void setBeginpublishdate(java.lang.String beginpublishdate) {
		this.beginpublishdate = beginpublishdate;
	}

	public java.lang.Long getAnnouncementId() {
		return announcementId;
	}

	public void setAnnouncementId(java.lang.Long announcementId) {
		this.announcementId = announcementId;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getBody() {
		return body;
	}

	public void setBody(java.lang.String body) {
		this.body = body;
	}

	public java.util.Date getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(java.util.Date publishtime) {
		this.publishtime = publishtime;
	}
	
	public String convertDateToString(Date date,String formatString){
		SimpleDateFormat formatter = new SimpleDateFormat(formatString);
		return formatter.format(date);
	}
	
	public java.lang.String getFormatdate() {
		return convertDateToString(this.getPublishtime(),"yyyy-MM-dd HH:mm:ss");
	}

	public void setFormatdate(java.util.Date date) {
		this.formatdate = convertDateToString(date,"yyyy-MM-dd HH:mm:ss");
	}

	@Override
	public String toString() {
		return "TntAnnouncement [announcementId=" + announcementId + ", title="
				+ title + ", body=" + body + ", publishtime=" + publishtime
				+ ", formatdate=" + formatdate + ", getAnnouncementId()="
				+ getAnnouncementId() + ", getTitle()=" + getTitle()
				+ ", getBody()=" + getBody() + ", getPublishtime()="
				+ getPublishtime() + ", getFormatdate()=" + getFormatdate()
				+ "]";
	}
	
	public void trim() {
		if (title != null)
			setTitle(title.trim());
	}	

}
