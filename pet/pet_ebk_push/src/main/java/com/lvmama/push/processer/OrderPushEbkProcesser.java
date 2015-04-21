package com.lvmama.push.processer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lvmama.comm.bee.service.ebkpush.IEbkPushService;
import com.lvmama.comm.bee.service.ord.OrderPerformService;
import com.lvmama.comm.bee.vo.ord.OrdOrderPerformResourceVO;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.ebkpush.EbkPushMessage;
import com.lvmama.comm.pet.po.ebkpush.ModelUtils;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.push.model.ClientSessionInfo;
import com.lvmama.push.model.PushMessage;
import com.lvmama.push.model.SessionManager;
import com.lvmama.push.util.ConstantPush;
import com.lvmama.push.util.SyncLogicUtils;




public class OrderPushEbkProcesser implements MessageProcesser{
	 private static final Logger LOGGER = LoggerFactory.getLogger(MessageProcesser.class);
	private IEbkPushService ebkPushService;
	private OrderPerformService orderPerformProxy;
	@Override
	public void process(Message message) {
		LOGGER.info("message is :"+message.getAddition());
		if(message.isPasscodeApplySuccessMsg()||message.isPassCodeDestoryMsg()||message.isPassportUsedMsg()){
			PushMessage ms = new PushMessage(ConstantPush.PUSH_MSG_TYPE.PULL.name());
			String ad = message.getAddition();
			if(ad==null){
				return;
			}
			String[] ads = ad.split("\\|");
			
			if(ads.length==2){
				ms.setAddCode(ads[0]);
				String[] devices = ads[1].split(",");
				
				LOGGER.info("this message has "+devices.length+" device target ready to send");
				
				for (int i = 0; i < devices.length; i++) {
					
					if(!StringUtil.isEmptyString(devices[i])){
						final String deviceId = devices[i];
						EbkPushMessage pm = new EbkPushMessage();
						pm.setCommand(ms.getCommand());
						pm.setDeviceId(deviceId);
						
						
						
						
						if(message.isPasscodeApplySuccessMsg()){
							pm.setCommandType("PASSCODE_APPLY_SUCCESS");
						} else if(message.isPassCodeDestoryMsg()){
							pm.setCommandType("PASSCODE_DESTORY");
						} else if(message.isPassportUsedMsg()){
							pm.setCommandType("PASSCODE_USED");
						}
						
						pm.setAddInfo(ads[0]);
		
						Long pushId = ebkPushService.getMessageIdSeq();
						pm.setId(pushId);
						LOGGER.info("push id is  "+pushId );
						ms.setPushId(String.valueOf(pushId));
						
	
					    List<Long> pushIds = new ArrayList<Long>();
						List<OrdOrderPerformResourceVO> performResourcesList = new ArrayList<OrdOrderPerformResourceVO>();
					    Map<String,Object> param = new HashMap<String,Object>();
						param.put("udid", pm.getDeviceId());
						param.put("addCode", pm.getAddInfo());
						pushIds.add(pm.getId());
						
						List<OrdOrderPerformResourceVO> performResources = orderPerformProxy.queryOrderPerformByEBK(param);
						if (performResources!=null &&performResources.size()!=0){
							performResourcesList.add(performResources.get(0));
						}
						Map<String,Object> resultDatas = new HashMap<String,Object>();
						ClientSessionInfo clientSession = SessionManager.getInstance().getSessions().get(deviceId);	
						LOGGER.info("设备号："+deviceId);
						
						pm.setPushContent(JSONObject.fromObject(resultDatas).toString());
						resultDatas.put("command", ConstantPush.PUSH_MSG_TYPE.SYNC_DATAS.name());
						resultDatas.put("datas", ModelUtils.buildSendDatas(performResourcesList));
					    ebkPushService.insertEbkPushMessage(pm);
					    
						if(clientSession!= null && clientSession.getSession()!=null&&clientSession.getSession().isConnected()){
							/**
							 * important code
							 */
							SyncLogicUtils.sync(clientSession.getSession(), resultDatas, pushIds, clientSession.getUdid());
						} else {
							LOGGER.info("设备号："+deviceId+"不在线");
						}
						
					}
				}
			}  else {
				LOGGER.info("message length error ");
			}
		} 
		
		
	}
	public void setEbkPushService(IEbkPushService ebkPushService) {
		this.ebkPushService = ebkPushService;
	}
	public void setOrderPerformProxy(OrderPerformService orderPerformProxy) {
		this.orderPerformProxy = orderPerformProxy;
	}


}
