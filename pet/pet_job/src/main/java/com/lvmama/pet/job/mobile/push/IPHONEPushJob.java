package com.lvmama.pet.job.mobile.push;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationPayload;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.mobile.MobilePushJogTask;
import com.lvmama.comm.pet.po.mobile.MobilePushSendTask;
import com.lvmama.comm.pet.po.mobile.MobilePushSendTaskLog;
import com.lvmama.comm.pet.service.mobile.MobileClientService;
import com.lvmama.comm.vo.Constant;

public class IPHONEPushJob {
	private final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private MobileClientService mobileClientService;

	private static String LOCK = "LOCK";

	public void sendMobilePush() {
		if (Constant.getInstance().isJobRunnable()) {
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

		} else {
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
		param.put("plaform", Constant.MOBILE_PLATFORM.IPHONE.name());
		while (true) {
			List<MobilePushSendTask> mobilePushSendTaskList = mobileClientService
					.pagedQueryMobilePushSendTask(param);
			logger.info("send size :" + mobilePushSendTaskList.size());

			if (mobilePushSendTaskList == null
					|| mobilePushSendTaskList.isEmpty()) {
				break;
			}

			for (MobilePushSendTask mobilePushSendTask : mobilePushSendTaskList) {
				MobilePushJogTask mobilePushJogTask = mobileClientService
						.selectByPrimaryKey(mobilePushSendTask
								.getMobilePushJogTaskId());
				// 调用发送函数
				logger.info("push invoke push ");
				if (mobilePushSendTask.isPush2Iphone()
						&& !mobilePushSendTask.isPushToAll()) {
					notificationPush2ISO(mobilePushJogTask, mobilePushSendTask);
				}

				MobilePushSendTaskLog mobilePushSendTaskLog = new MobilePushSendTaskLog();
				mobilePushSendTaskLog.setMobilePushJogTaskId(mobilePushJogTask
						.getMobilePushJogTaskId());
				mobilePushSendTaskLog
						.setMobilePushSendTaskId(mobilePushSendTask
								.getMobilePushSendTaskId());
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

	private void notificationPush2ISO(MobilePushJogTask mobilePushJogTask,
			MobilePushSendTask mobilePushSendTask) throws Exception {
		logger.info("before invoke notificationPush2ISO.....");
		try {
			PushNotificationPayload payload = new PushNotificationPayload();
			payload.addAlert(mobilePushJogTask.getContent());
			payload.addSound("default");// 声音
			payload.addCustomDictionary("title",
					mobilePushJogTask.getTitle() == null ? ""
							: mobilePushJogTask.getTitle());
			payload.addCustomDictionary("url", mobilePushJogTask.getUrl());
			payload.addCustomDictionary("type", mobilePushJogTask.getOpenType());
			payload.addCustomDictionary(
					"id",
					mobilePushJogTask.getObjectId() == null ? "" : String
							.valueOf(mobilePushJogTask.getObjectId()));
			logger.info("in .....1");
			logger.info(payload.getPayload().toString());
			logger.info(mobilePushJogTask.getIosPushData().toString());
			logger.info("payload size is :"
					+ payload.getPayloadSize()
					+ " jsonSize:"
					+ mobilePushJogTask.getIosPushData().toString()
							.getBytes("UTF-8").length);
			if (payload.getPayloadSize() <= 256) {
				PayloadPerDevice pay = new PayloadPerDevice(payload,
						mobilePushSendTask.getPushObjectId());// 将要推送的消息和手机唯一标识绑定
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

}
