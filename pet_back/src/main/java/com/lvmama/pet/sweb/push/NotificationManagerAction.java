package com.lvmama.pet.sweb.push;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.mobile.MobilePushJogTask;
import com.lvmama.comm.pet.service.mobile.MobileClientService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

@Results({
		@Result(name = "notification.manage", location = "/WEB-INF/pages/back/push/notificationManager.jsp"),
		@Result(name = "test.notification.manage", location = "/WEB-INF/pages/back/push/testNotificationManager.jsp") })
public class NotificationManagerAction extends BackBaseAction {
	private static final long serialVersionUID = 7447871684478275712L;
	private MobileClientService mobileClientService;
	private String notificationTitle;
	private String notificationUrl;
	private String notificationContent;
	private String notificationBeginTime;
	private String notificationType;
	private String deviceType;
	private String pushIds;

	private String deviceToken;

	private JSONObject jsonObj = new JSONObject();

	@Action("/push/notificationManage")
	public String notificationManage() {
		return "notification.manage";
	}

	@Action("/push/testNotificationManage")
	public String testNotificationManage() {
		return "test.notification.manage";
	}

	@Action("/push/addNotification")
	public void addNotification() {
		try {
			MobilePushJogTask mobilePushJogTask = new MobilePushJogTask();
			mobilePushJogTask.setTitle(notificationTitle);
			mobilePushJogTask.setContent(notificationContent);
			mobilePushJogTask.setCreatedTime(Calendar.getInstance().getTime());
			mobilePushJogTask.setBeginTime(DateUtil.stringToDate(
					notificationBeginTime, "yyyy-MM-dd HH:mm:ss"));
			mobilePushJogTask.setUrl(notificationUrl);
			mobilePushJogTask.setOpenType(notificationType);
			mobilePushJogTask.setTargetVersion(Constant.getInstance().getValue(
					"notification.push.target.lvsession"));
			mobilePushJogTask.setPlaform(deviceType);
			mobilePushJogTask.setPushDeviceIds(pushIds);
			if(mobilePushJogTask.isPush2Android()&&mobilePushJogTask.androidPushMsgToLarge()){
				jsonObj.put("returnCode", "success");
				jsonObj.put("description", "android 推送报文太长，请精简字数");
				this.sendAjaxMsg(jsonObj.toString());
				return;
			}
			
			if((mobilePushJogTask.isPush2Iphone()||mobilePushJogTask.isPush2Ipad())&&mobilePushJogTask.isPushIosDataToLarget()){
				jsonObj.put("returnCode", "success");
				jsonObj.put("description", "ios 推送报文太长，请精简字数");
				this.sendAjaxMsg(jsonObj.toString());
				return;
				
			}
			mobileClientService.insert(mobilePushJogTask);
			jsonObj.put("returnCode", "success");
			jsonObj.put("description", "添加推送成功!");
			this.sendAjaxMsg(jsonObj.toString());
		} catch (Exception e) {
			log.error("添加推送失败...", e);
			jsonObj.put("returnCode", "fail");
			jsonObj.put("description", e.getMessage());
			this.sendAjaxMsg(jsonObj.toString());
		} 
	}
	
	public String getNotificationUrl() {
		return notificationUrl;
	}

	public void setNotificationUrl(String notificationUrl) {
		this.notificationUrl = notificationUrl;
	}

	public String getNotificationContent() {
		return notificationContent;
	}

	public void setNotificationContent(String notificationContent) {
		this.notificationContent = notificationContent;
	}

	public String getNotificationTitle() {
		return notificationTitle;
	}

	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public String getPushIds() {
		return pushIds;
	}

	public void setPushIds(String pushIds) {
		this.pushIds = pushIds;
	}

	public String getNotificationBeginTime() {
		return notificationBeginTime;
	}

	public void setNotificationBeginTime(String notificationBeginTime) {
		this.notificationBeginTime = notificationBeginTime;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public MobileClientService getMobileClientService() {
		return mobileClientService;
	}

	public void setMobileClientService(MobileClientService mobileClientService) {
		this.mobileClientService = mobileClientService;
	}

}
