package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class MobilePersistanceLog implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5678659383108415870L;

	private Long mobilePersistanceLogId;

    private Long objectId;

    private String objectType;

    private Date createdTime;

    private Long lvVersion;

    private String deviceId;

    private String deviceIdType;

    private String channel;

    private String osVersion;

    private String firstChannel;
    
    private String secondChannel;
    
    private String userAgent;
    
    public Long getMobilePersistanceLogId() {
        return mobilePersistanceLogId;
    }

    public void setMobilePersistanceLogId(Long mobilePersistanceLogId) {
        this.mobilePersistanceLogId = mobilePersistanceLogId;
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
        this.objectType = objectType == null ? null : objectType.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getLvVersion() {
        return lvVersion;
    }

    public void setLvVersion(Long lvVersion) {
        this.lvVersion = lvVersion;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getDeviceIdType() {
    	if(!StringUtil.isEmptyString(deviceIdType)){
    		 return deviceIdType;
    	}
    	
    	if(getChannel()==null){
    		return null;
    	}
    	
    	if(getChannel().contains("ANDROID")){
    		return "IEMI";
    	}
    	if(getChannel().contains("IPHONE")){
    		return "MAC";
    	}
    	return null;
       
    }

    public void setDeviceIdType(String deviceIdType) {
        this.deviceIdType = deviceIdType == null ? null : deviceIdType.trim();
    }

    public String getChannel() {
    	return firstChannel+"_"+secondChannel;
    }


    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion == null ? null : osVersion.trim();
    }

	public String getFirstChannel() {
		return firstChannel;
	}

	public void setFirstChannel(String firstChannel) {
		this.firstChannel = firstChannel;
	}


	public String getSecondChannel() {
		return secondChannel;
	}

	public void setSecondChannel(String secondChannel) {
		this.secondChannel = secondChannel;
	}

	public void setChannel(String channel) {
		if(channel!=null&&channel.contains("_")){
			String[] array = channel.split("_");
			this.setFirstChannel(array[0]);
			this.setSecondChannel(array[1]);
		}
		this.channel = channel;
	}
	
	public boolean isClient(){
		return Constant.MOBILE_PLATFORM.ANDROID.name().equals(this.firstChannel)||Constant.MOBILE_PLATFORM.IPHONE.name().equals(this.firstChannel)||Constant.MOBILE_PLATFORM.IPAD.name().equals(this.firstChannel);	
	}
	
	public boolean isTouch(){
		return Constant.MOBILE_PLATFORM.TOUCH.name().equals(this.firstChannel);
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
}