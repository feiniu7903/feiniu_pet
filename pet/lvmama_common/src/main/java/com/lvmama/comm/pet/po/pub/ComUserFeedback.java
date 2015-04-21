package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

public class ComUserFeedback implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 535893055954330836L;

	private Long userFeedbackId;

    private String userName;

    private Long userId;

    private String ip;

    private String contact;

    private String content;

    private Date createDate;

    private String mobile;

    private String instantMessaging;

    private String email;

    private String url;

    private String type;

    private String stateId;

   

    public Long getUserFeedbackId() {
		return userFeedbackId;
	}

	public void setUserFeedbackId(Long userFeedbackId) {
		this.userFeedbackId = userFeedbackId;
	}

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getInstantMessaging() {
        return instantMessaging;
    }

    public void setInstantMessaging(String instantMessaging) {
        this.instantMessaging = instantMessaging == null ? null : instantMessaging.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId == null ? null : stateId.trim();
    }
    
    public String getZhCreateDate() {
       return DateFormatUtils.format(this.createDate, "yyyy-MM-dd HH:mm:ss");
    }
}