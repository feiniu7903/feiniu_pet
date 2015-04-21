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

public class MobilePushJogTask implements Serializable {
	private static final long serialVersionUID = 4920450822636944964L;
    private Long mobilePushJogTaskId;

    private String title;

    private String content;

    private Date createdTime;

    private Date beginTime;

    private String url;

    private String targetVersion;

    private String isValid;

    private String openType;

    private Long objectId;

    private String plaform;

    private String pushIds;

    private String pushUserIds;
    
    private String pushDeviceIds;

    public String getPushUserIds() {
		return pushUserIds;
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
    
    
//    public static void main(String[] args) throws JSONException {
//    	PushNotificationPayload payload = new PushNotificationPayload();
//		try {
//			payload.addAlert("asdfadfasdfasdfas");
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		payload.addSound("default");// 声音
//		payload.addCustomDictionary("title", "test");
//		//payload.getPayloadSize();
//		payload.addCustomDictionary("url",  "test");
//		payload.addCustomDictionary("type", "webbrower");
//		payload.addCustomDictionary(
//				"targetLvVersion","");
//		payload.addCustomDictionary("id", "102244");
//		
//		try {
//			System.out.println(j.toString().getBytes("utf-8").length);
//			System.out.println(payload.getPayload().toString());
//			System.out.println(payload.getPayloadSize());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//    
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

	public void setPushUserIds(String pushUserIds) {
		this.pushUserIds = pushUserIds;
	}

	public String getPushDeviceIds() {
		return pushDeviceIds;
	}

	public void setPushDeviceIds(String pushDeviceIds) {
		this.pushDeviceIds = pushDeviceIds;
	}

	public Long getMobilePushJogTaskId() {
        return mobilePushJogTaskId;
    }

    public void setMobilePushJogTaskId(Long mobilePushJogTaskId) {
        this.mobilePushJogTaskId = mobilePushJogTaskId;
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

    public String getTargetVersion() {
        return targetVersion;
    }

    public void setTargetVersion(String targetVersion) {
        this.targetVersion = targetVersion == null ? null : targetVersion.trim();
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

    	
    public List<String> getPushUserList(){
    	List<String> idsArray = new ArrayList<String>();
    	if(!StringUtil.isEmptyString(pushUserIds)&&pushUserIds.contains(",")){
    		String[] ids = pushUserIds.split("(,|，)");
    		for (String string : ids) {
    			idsArray.add(string);
			}
    	} else if(StringUtil.isNotEmptyString(pushUserIds)){
    		idsArray.add(pushUserIds);
    	}
    	return idsArray;
    }
    
    public List<String> getPushDeviceList(){
    	List<String> idsArray = new ArrayList<String>();
    	if(!StringUtil.isEmptyString(pushDeviceIds)&&pushDeviceIds.contains(",")){
    		String[] ids = pushDeviceIds.split("(,|，)");
    		for (String string : ids) {
    			idsArray.add(string);
			}
    	} else if(StringUtil.isNotEmptyString(pushDeviceIds)){
    		idsArray.add(pushDeviceIds);
    	}
    	return idsArray;
    }
    
    public boolean pushUserListToLarge(){
    	if(!StringUtil.isEmptyString(pushUserIds)&&pushUserIds.contains(",")&&pushUserIds.length()>4000){
    		return true;
    	}
    	return false;
    }
  
    
    public boolean pushDeviceListToLarge(){
    	if(!StringUtil.isEmptyString(pushDeviceIds)&&pushDeviceIds.contains(",")&&pushDeviceIds.length()>4000){
    		return true;
    	}
    	return false;
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
    
    public String getPushSuffix(){
    	return Constant.getInstance().getValue("PUSH_SUFFIX");
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
    	MobilePushJogTask mt = new MobilePushJogTask();
    	mt.setContent("我是中国人我爱自己的住过呵呵呵 啊发发时代发生发生点发手动阀手动阀速度发我是中国人我爱自己的住过呵呵呵 啊发发时代发生发生点发手动阀手动阀速度发");
    	mt.setObjectId(120044L);
    	mt.setUrl("http://m.lvmama.com/clutter/place/120044");
    	mt.setTitle("test");
    	mt.setOpenType("place");
    	System.out.println(mt.getIosPushData().toString().getBytes("UTF-8").length);
    	System.out.println(mt.getIosPushData().toString().length());
	}
    
	@Override
	public String toString() {
		return "MobilePushJogTask [mobilePushJogTaskId=" + mobilePushJogTaskId
				+ ", title=" + title + ", content=" + content
				+ ", createdTime=" + createdTime + ", beginTime=" + beginTime
				+ ", url=" + url + ", targetVersion=" + targetVersion
				+ ", isValid=" + isValid + ", openType=" + openType
				+ ", objectId=" + objectId + ", plaform=" + plaform
				+ ", pushIds=" + pushIds + "]";
	}
    
}