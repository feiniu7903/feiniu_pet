package com.lvmama.comm.pet.po.conn;

import java.util.Date;

public class ConnRecord  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6342978529670488659L;

	private Long connRecordId;

    private Long callTypeId;

    private Long userId;

    private String operatorUserId;

    private Date feedbackTime;

    private String memo;

    private Date createDate;

    private String mobile;

    private String placeName;

    private String callBack;

    private Long channelId;

    private Date visitTime;

    private String toPlaceName;

    private String fromPlaceName;

    private Long day;

    private Long quantity;

    private Long productId;

    private String productZone;

    private String businessType;

    private String serviceType;

    private String subServiceType;
    
    private String filialeName;
    
    private String group;
    
    
    private String branchTypeStr;
    
    
    private String visaCountry;
    
    private String visaType;
    
    private Date feedbackTimeEnd;

    public Date getFeedbackTimeEnd() {
		return feedbackTimeEnd;
	}

	public void setFeedbackTimeEnd(Date feedbackTimeEnd) {
		this.feedbackTimeEnd = feedbackTimeEnd;
	}

	public Long getConnRecordId() {
        return connRecordId;
    }

    public void setConnRecordId(Long connRecordId) {
        this.connRecordId = connRecordId;
    }

    public Long getCallTypeId() {
        return callTypeId;
    }

    public void setCallTypeId(Long callTypeId) {
        this.callTypeId = callTypeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId == null ? null : userId;
    }

    public String getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(String operatorUserId) {
        this.operatorUserId = operatorUserId == null ? null : operatorUserId.trim();
    }

    public Date getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Date feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
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

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName == null ? null : placeName.trim();
    }

    public String getCallBack() {
        return callBack;
    }

    public void setCallBack(String callBack) {
        this.callBack = callBack;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getToPlaceName() {
        return toPlaceName;
    }

    public void setToPlaceName(String toPlaceName) {
        this.toPlaceName = toPlaceName;
    }

    public String getFromPlaceName() {
        return fromPlaceName;
    }

    public void setFromPlaceName(String fromPlaceName) {
        this.fromPlaceName = fromPlaceName;
    }

    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductZone() {
        return productZone;
    }

    public void setProductZone(String productZone) {
        this.productZone = productZone == null ? null : productZone.trim();
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType == null ? null : serviceType.trim();
    }

    public String getSubServiceType() {
        return subServiceType;
    }

    public void setSubServiceType(String subServiceType) {
        this.subServiceType = subServiceType == null ? null : subServiceType.trim();
    }
    
    public boolean hasCallBack(){
    	return "true".equals(this.callBack);
    }

	public String getBranchTypeStr() {
		return branchTypeStr;
	}

	public void setBranchTypeStr(String branchTypeStr) {
		this.branchTypeStr = branchTypeStr;
	}

	public String getVisaCountry() {
		return visaCountry;
	}

	public void setVisaCountry(String visaCountry) {
		this.visaCountry = visaCountry;
	}

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	public String getFilialeName() {
		return filialeName;
	}

	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
    
    public String getZhGroup() {
    	if(this.group != null) {
    		return this.group.equals("true") ? "是" : "否";
    	}
    	return "否";
    }
}