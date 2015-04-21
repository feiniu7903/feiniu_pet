package com.lvmama.pet.job.mobile.push;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.mobile.MobilePushDevice;
import com.lvmama.comm.pet.po.mobile.MobilePushJogTask;
import com.lvmama.comm.pet.po.mobile.MobilePushSendTask;
import com.lvmama.comm.pet.service.mobile.MobileClientService;
import com.lvmama.comm.vo.Constant;


public class SyncMobilePushJob {
	private final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private MobileClientService mobileClientService;

	public void syncMobilePush() {
		if(Constant.getInstance().isJobRunnable()) {
		logger.info("before invoke syncMobilePush method...");
		/**
		 * 同步按设备或者用户id发送的
		 */
		this.syncJobToSendTask();
		
		this.executeDeleteInvalidToken();
		logger.info("after invoke syncMobilePush method...");
		} else {
			logger.info("job.enable = false");
		}
	}

	public void executeDeleteInvalidToken(){
		
		logger.info("execute delete invlid token ");
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,String> map = NotificationQueueManager.getInstance().getInavalidTokenMap();
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		logger.info("find list size: "+NotificationQueueManager.getInstance().getInavalidTokenMap().size());
		while(it.hasNext()){
			List<String> list = new ArrayList<String>();
			Entry<String, String> en = it.next();
			list.add(en.getKey());
			logger.info("begin delete token ["+en.getKey()+"] the key is invalid");
			param.put("objectIdList", list);
			mobileClientService.deleteByDeviceTokens(param);
		}
		if(!NotificationQueueManager.getInstance().getInavalidTokenMap().isEmpty()){
			NotificationQueueManager.getInstance().removeInvlidTokenList();
			logger.info("invoked");
		} else {
			logger.info("ingore reason empty param list");
		}
		
	}


	private void syncJobToSendTask() {
		List<MobilePushJogTask> list = mobileClientService.selectAllJobs();
		logger.info("查询到 size "+list.size()+"数据");
		for (MobilePushJogTask mobilePushJogTask : list) {
				if(!mobilePushJogTask.getPushDeviceList().isEmpty()){
					Map<String,Object> param = new HashMap<String,Object>();
					param.put("deviceList", mobilePushJogTask.getPushDeviceList());
					List<MobilePushDevice> deviceList = mobileClientService.getPushTargetIds(param);
					logger.info("query deviceSize:"+deviceList.size());
					this.addSendTask(deviceList, mobilePushJogTask);
				}  else if(!mobilePushJogTask.getPushUserList().isEmpty()){
					Map<String,Object> param = new HashMap<String,Object>();
					param.put("userList", mobilePushJogTask.getPushUserList());
					List<MobilePushDevice> deviceList = mobileClientService.getPushTargetIds(param);
					logger.info("query deviceSize:"+deviceList.size());
					this.addSendTask(deviceList, mobilePushJogTask);
				} else if(mobilePushJogTask.getPushDeviceList().isEmpty()&&mobilePushJogTask.getPushUserList().isEmpty()){
					if(mobilePushJogTask.isPush2Android()){
						/**
						 * android 推送所有 只存一条数据,objectid 为null;
						 */
						MobilePushSendTask task = new MobilePushSendTask();
						task.setMobilePushJogTaskId(mobilePushJogTask.getMobilePushJogTaskId());
						task.setPlaform(mobilePushJogTask.getPlaform());
						mobileClientService.insertMobilePushSendTask(task);
					} else if(mobilePushJogTask.isPush2Iphone()){
						mobileClientService.syncJobToSendTaskByTaskId(mobilePushJogTask.getMobilePushJogTaskId());
					}
				}
				mobilePushJogTask.setIsValid("N");
				mobileClientService.updateMobilePushJogTask(mobilePushJogTask);
		}
	}
	
	
	private void addSendTask(List<MobilePushDevice> deviceList,MobilePushJogTask mobilePushJogTask){
		for (MobilePushDevice mobilePushDevice : deviceList) {
			MobilePushSendTask task = new MobilePushSendTask();
			task.setMobilePushJogTaskId(mobilePushJogTask.getMobilePushJogTaskId());
			task.setPlaform(mobilePushDevice.getPlaform());
			task.setPushObjectId(mobilePushDevice.getObjectId());
			mobileClientService.insertMobilePushSendTask(task);
		}
	}

	
}
