package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class MobilePushJob implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3410905724151185982L;

	private Long mobilePushJobId;

    private String title;

    private String content;

    private Date createdTime;

    private Date beginTime;

    private String url;

    private String isValid;

    private String openType;

    private Long objectId;

    private String plaform;

    private String pushIds;

    private String status;

    private String pushType;

    private String pushPolicyId;


    /**
     * 是否是过期任务
     * @return
     */
    public boolean isOverDue(){
    	return new Date().after(beginTime);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid == null ? null : isValid.trim();
    }

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType == null ? null : openType.trim();
    }

    public Long getObjectId() {
    	if ("place".equals(openType) || "route".equals(openType)
				|| "guide".equals(openType)) {
			if (!StringUtils.isEmpty(url)) {
				String notificationId = url.substring(url.lastIndexOf("/") + 1,
						url.length());
				return Long.valueOf(notificationId);
			}
		}
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getPlaform() {
        return plaform;
    }

    public void setPlaform(String plaform) {
        this.plaform = plaform == null ? null : plaform.trim();
    }

    public String getPushIds() {
        return pushIds;
    }

    public void setPushIds(String pushIds) {
        this.pushIds = pushIds == null ? null : pushIds.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType == null ? null : pushType.trim();
    }

    public String getPushPolicyId() {
        return pushPolicyId;
    }

    public void setPushPolicyId(String pushPolicyId) {
        this.pushPolicyId = pushPolicyId == null ? null : pushPolicyId.trim();
    }
    
    public boolean isPushAll(){
    	return Constant.CLIENT_PUSH_TYPE.ALL.name().equals(pushType);
    }
    
    public boolean isPushToken(){
    	return Constant.CLIENT_PUSH_TYPE.DEVICE_TOKEN.name().equals(pushType);
    }
    
    public boolean isPushProvince(){
    	return Constant.CLIENT_PUSH_TYPE.PROVINCE.name().equals(pushType);
    }
    
    public List<String> getPloicyArray(){
    		List<String> idsArray = new ArrayList<String>();
	    	if(!StringUtil.isEmptyString(pushPolicyId)&&pushPolicyId.contains(",")){
	    		String[] ids = pushPolicyId.split("(,|，)");
	    		for (String string : ids) {
	    			idsArray.add(string);
				}
	    	} else if(StringUtil.isNotEmptyString(pushPolicyId)){
	    		idsArray.add(pushPolicyId);
	    	}
	    	return idsArray;
    }
    
  
    
    public  Map<String, Object> getAndroidPushExtraMap(){
    	Map<String, Object> extraMap = new HashMap<String, Object>();
		extraMap.put("url", getUrl());
		extraMap.put("type", getOpenType());
		if ("place".equals(openType) || "route".equals(openType)
				|| "guide".equals(openType)) {
			if (!StringUtils.isEmpty(url)) {
				String notificationId = url.substring(url.lastIndexOf("/") + 1,
						url.length());
				extraMap.put("id", notificationId);
			}
		}
		return extraMap;
    }
    
    public JSONObject getIosPushData() {
		JSONObject j = new JSONObject();
		JSONObject aps = new JSONObject();
		aps.put("sound", "default");
		aps.put("alert", this.getContent()==null?"":this.getContent());
		j.put("aps", aps);
		j.put("title", this.getTitle());
		j.put("url",  this.getUrl());
		j.put("type", getOpenType());
		j.put("id", getObjectStr());
		return j;
    }
  
    public boolean isPushIosDataToLarget() throws UnsupportedEncodingException{
		if(getIosPushData().toString().getBytes("UTF-8").length>256){
				return true;
		}
    	return false;
    }
    
    public int getAndroidPushLength(){
    	String allContent = JSONObject.fromObject(getAndroidPushExtraMap()).toString()+this.getContent();
    	try {
			return allContent.getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return 0;
    }
    
    public boolean androidPushMsgToLarge(){
    	if(getAndroidPushLength()>=220){
    		return true;
    	}
    	return false;
    }
    
    public boolean iphonePushMsgToLarge(){
    	if(getAndroidPushLength()>=220){
    		return true;
    	}
    	return false;
    }

    public String getObjectStr(){
    	if ("place".equals(openType) || "route".equals(openType)
				|| "guide".equals(openType)) {
			if (!StringUtils.isEmpty(url)) {
				String notificationId = url.substring(url.lastIndexOf("/") + 1,
						url.length());
				return notificationId;
			}
		}
        return "";
    }
    
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
    
    
	public Long getMobilePushJobId() {
		return mobilePushJobId;
	}

	public void setMobilePushJobId(Long mobilePushJobId) {
		this.mobilePushJobId = mobilePushJobId;
	}
}