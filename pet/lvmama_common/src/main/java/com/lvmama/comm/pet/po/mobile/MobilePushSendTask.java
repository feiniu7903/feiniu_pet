package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class MobilePushSendTask implements Serializable {
	private static final long serialVersionUID = -3428124234545877914L;

	private Long mobilePushSendTaskId;

    private Long mobilePushJogTaskId;

    private String pushObjectId;

    private String plaform;

    
    public boolean isPush2Android(){
    	if(Constant.MOBILE_PLATFORM.ANDROID.name().equals(plaform)){
    		return true;
    	}
    	return false;
    }
    
    public boolean isPush2Iphone(){
    	if(Constant.MOBILE_PLATFORM.IPHONE.name().equals(plaform)){
    		return true;
    	}
    	return false;
    } 
    
    public boolean isPush2Ipad(){
    	if(Constant.MOBILE_PLATFORM.IPAD.name().equals(plaform)){
    		return true;
    	}
    	return false;
    } 
    
    public boolean isPushToAll(){
    	if(StringUtil.isEmptyString(pushObjectId)){
    		return true;
    	}
    	return false;
    }
    
    public Long getMobilePushSendTaskId() {
        return mobilePushSendTaskId;
    }

    public void setMobilePushSendTaskId(Long mobilePushSendTaskId) {
        this.mobilePushSendTaskId = mobilePushSendTaskId;
    }

    public Long getMobilePushJogTaskId() {
        return mobilePushJogTaskId;
    }

    public void setMobilePushJogTaskId(Long mobilePushJogTaskId) {
        this.mobilePushJogTaskId = mobilePushJogTaskId;
    }

    public String getPushObjectId() {
        return pushObjectId;
    }

    public void setPushObjectId(String pushObjectId) {
        this.pushObjectId = pushObjectId == null ? null : pushObjectId.trim();
    }

    public String getPlaform() {
        return plaform;
    }

    public void setPlaform(String plaform) {
        this.plaform = plaform == null ? null : plaform.trim();
    }
    
    public String getPushSuffix(){
    	return Constant.getInstance().getValue("PUSH_SUFFIX");
    }
    
   

	@Override
	public String toString() {
		return "MobilePushSendTask [mobilePushSendTaskId="
				+ mobilePushSendTaskId + ", mobilePushJogTaskId="
				+ mobilePushJogTaskId + ", pushObjectId=" + pushObjectId
				+ ", plaform=" + plaform + "]";
	}
    
}