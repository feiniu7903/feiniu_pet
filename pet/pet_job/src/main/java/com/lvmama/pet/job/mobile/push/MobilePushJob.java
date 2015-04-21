package com.lvmama.pet.job.mobile.push;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationPayload;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.jpush.api.DeviceEnum;
import cn.jpush.api.ErrorCodeEnum;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

import com.lvmama.comm.pet.po.mobile.MobilePushJogTask;
import com.lvmama.comm.pet.po.mobile.MobilePushSendTask;
import com.lvmama.comm.pet.po.mobile.MobilePushSendTaskLog;
import com.lvmama.comm.pet.service.mobile.MobileClientService;
import com.lvmama.comm.vo.Constant;

public class MobilePushJob {
	private final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private MobileClientService mobileClientService;

	private static String LOCK="LOCK";

	public void sendMobilePush() {
		if(Constant.getInstance().isJobRunnable()) {
			synchronized (LOCK) {
				logger.info("before invoke sendMobilePush method...");
				try {
					beginSend();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info("after invoke sendMobilePush method...");
			}
				
		}else {
			logger.info("job.enable = false");
		}
	}
	

	/**
	 * 分页回调同步
	 * 
	 * @param mobilePushJogTaskList
	 * @throws Exception 
	 */
	private void beginSend() throws Exception {
		logger.info("before invoke beginSend method...");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", 0);
		param.put("end", 100);
		param.put("plaform", "'"+Constant.MOBILE_PLATFORM.IPHONE.name()+"','"+
		Constant.MOBILE_PLATFORM.ANDROID.name()+"','"+
				Constant.MOBILE_PLATFORM.IPAD.name()+"'");
		while(true){
			List<MobilePushSendTask> mobilePushSendTaskList = mobileClientService
						.pagedQueryMobilePushSendTask(param);
			logger.info("send size :"+mobilePushSendTaskList.size());
			
			if(mobilePushSendTaskList==null || mobilePushSendTaskList.isEmpty()){
					break;
			}
			
			for (MobilePushSendTask mobilePushSendTask : mobilePushSendTaskList) {
				MobilePushJogTask mobilePushJogTask = mobileClientService
								.selectByPrimaryKey( mobilePushSendTask
										.getMobilePushJogTaskId());
						// 调用发送函数
						logger.info("push invoke push ");
						if(mobilePushSendTask.isPush2Iphone()&&!mobilePushSendTask.isPushToAll()){
							notificationPush2ISO(mobilePushJogTask, mobilePushSendTask);
						} else if(mobilePushSendTask.isPush2Ipad()){
							this.notificationPush2IPAD(mobilePushJogTask, mobilePushSendTask);	
						} else if(mobilePushSendTask.isPush2Android()){
							this.pushToAndroid(mobilePushJogTask, mobilePushSendTask);
						} 
						
						MobilePushSendTaskLog mobilePushSendTaskLog = new MobilePushSendTaskLog();
						mobilePushSendTaskLog
								.setMobilePushJogTaskId(mobilePushJogTask
										.getMobilePushJogTaskId());
						mobilePushSendTaskLog
								.setMobilePushSendTaskId(mobilePushSendTask.getMobilePushSendTaskId());
						mobilePushSendTaskLog.setPushObjectId(mobilePushSendTask
								.getPushObjectId());
						mobilePushSendTaskLog.setPushStatu("Y");
						mobileClientService
								.insertMobilePushSendTaskLog(mobilePushSendTaskLog);
						logger.info("before invoke insertMobilePushSendTaskLog...");
						// 删除已经发送的记录
						mobileClientService
								.deleteSendTaskByPrimaryKey(mobilePushSendTask
										.getMobilePushSendTaskId());
			}
		}
	}


	public void pushToAndroid(MobilePushJogTask mobilePushJogTask,MobilePushSendTask mobilePushSendTask) throws Exception{
		logger.info("before invoke pushToAndroid method.....");
		String masterSecret = Constant.getInstance().getValue(
				"notification.push.master.secret");
		String appKey = Constant.getInstance().getValue(
				"notification.push.app.key");
		JPushClient jpush = new JPushClient(masterSecret, appKey,
				DeviceEnum.Android);
		
		int sendNo = getRandomSendNo();
		logger.info("push to jpush data length:"+mobilePushJogTask.getAndroidPushLength()+" isPushAll ? "+ mobilePushSendTask.isPushToAll());
		MessageResult msgResult = null;
		if(mobilePushSendTask.isPushToAll()){
			 msgResult = jpush.sendNotificationWithAppKey(sendNo,
					mobilePushJogTask.getTitle(),
					mobilePushJogTask.getContent(), 0, mobilePushJogTask.getAndroidPushExtraMap());
		} else {
			msgResult = jpush.sendCustomMessageWithImei(sendNo, mobilePushSendTask.getPushObjectId(), mobilePushJogTask.getTitle(), 
				mobilePushJogTask.getContent(), "2", mobilePushJogTask.getAndroidPushExtraMap());
		}
		if (null != msgResult) {
			if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
				MobilePushSendTaskLog mobilePushSendTaskLog = new MobilePushSendTaskLog();
				mobilePushSendTaskLog
						.setMobilePushJogTaskId(mobilePushJogTask
								.getMobilePushJogTaskId());
				mobilePushSendTaskLog.setPushStatu("Y");
				mobileClientService
						.insertMobilePushSendTaskLog(mobilePushSendTaskLog);
				logger.info("android推送成功");
			}else{
				throw new Exception(MessageFormat.format("android推送失败,errcode=[{0}],errmsg=[{1}]",msgResult.getErrcode(),msgResult.getErrmsg()));
			}
		}
		logger.info("after invoke pushToAndroid method.....");
	}
	
	
	private void notificationPush2ISO(MobilePushJogTask mobilePushJogTask,
			MobilePushSendTask mobilePushSendTask) throws Exception {
		logger.info("before invoke notificationPush2ISO.....");
		try {
			PushNotificationPayload payload = new PushNotificationPayload();
			payload.addAlert(mobilePushJogTask.getContent());
			payload.addSound("default");// 声音
			payload.addCustomDictionary("title", mobilePushJogTask.getTitle()==null?"":mobilePushJogTask.getTitle());
			payload.addCustomDictionary("url",  mobilePushJogTask.getUrl());
			payload.addCustomDictionary("type", mobilePushJogTask.getOpenType());
			payload.addCustomDictionary("id", mobilePushJogTask.getObjectId()==null?"":String.valueOf(mobilePushJogTask.getObjectId()));
			logger.info("in .....1");
			logger.info(payload.getPayload().toString());
			logger.info(mobilePushJogTask.getIosPushData().toString());
			logger.info("payload size is :"+payload.getPayloadSize()+ " jsonSize:"+mobilePushJogTask.getIosPushData().toString().getBytes("UTF-8").length);
			if(payload.getPayloadSize()<=256){
				PayloadPerDevice pay = new PayloadPerDevice(payload, mobilePushSendTask.getPushObjectId());// 将要推送的消息和手机唯一标识绑定
				NotificationQueueManager.getInstance().addQueue(pay);
				logger.info("IPHONE推送成功.....");
			} else {
				logger.info("push content limit 256 byte");
			}
			
		} catch (Exception e) {
			logger.error(e);
			throw new Exception("ISO推送失败");
		}
		logger.info("after invoke notificationPush2ISO.....");
	}

	private void notificationPush2IPAD(MobilePushJogTask mobilePushJogTask,
			MobilePushSendTask mobilePushSendTask) throws Exception {
		logger.info("before invoke notificationPush2IPAD method.....");
		try {
			
			logger.info("in .....");
			PushNotificationPayload payload = new PushNotificationPayload();
			payload.addAlert(mobilePushJogTask.getContent());
			payload.addSound("default");// 声音
			payload.addCustomDictionary("title", mobilePushJogTask.getTitle()==null?"":mobilePushJogTask.getTitle());
			payload.addCustomDictionary("url",  mobilePushJogTask.getUrl());
			payload.addCustomDictionary("type", mobilePushJogTask.getOpenType());
			payload.addCustomDictionary("id", mobilePushJogTask.getObjectId()==null?"":String.valueOf(mobilePushJogTask.getObjectId()));
			logger.info("in .....1");
			logger.info(payload.getPayload().toString());
			logger.info(mobilePushJogTask.getIosPushData().toString());
			logger.info("payload size is :"+payload.getPayloadSize()+ " jsonSize:"+mobilePushJogTask.getIosPushData().toString().getBytes("UTF-8").length);
			if(payload.getPayloadSize()<=256){
				PayloadPerDevice pay = new PayloadPerDevice(payload, mobilePushSendTask.getPushObjectId());// 将要推送的消息和手机唯一标识绑定
				IPADNotificationQueueManager.getInstance().addQueue(pay);
				logger.info("IPAD推送成功.....");
			} else {
				logger.info("push content limit 256 byte");
			}
			
		} catch (Exception e) {
			logger.error(e);
			throw new Exception("IPAD推送失败",e);
		}
		logger.info("after invoke notificationPush2IPAD method.....");
	}

	/**
	 *  * 保持 sendNo 的唯一性是有必要的  * It is very important to keep sendNo unique.  * @return
	 * sendNo  
	 */
	public static int getRandomSendNo() {
		int max = Integer.MAX_VALUE;
		int min = max / 2;
		return (int) (min + Math.random() * (max - min));
	}


}
