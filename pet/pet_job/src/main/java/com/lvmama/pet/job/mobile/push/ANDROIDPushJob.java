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

public class ANDROIDPushJob {
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
		param.put("plaform", Constant.MOBILE_PLATFORM.ANDROID.name());
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
						if(mobilePushSendTask.isPush2Android()){
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
