package com.lvmama.push.web;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.bee.po.eplace.EbkUser;
import com.lvmama.comm.bee.po.pass.PassDevice;
import com.lvmama.comm.bee.service.ebkpush.IEbkPushService;
import com.lvmama.comm.bee.service.eplace.EbkUserService;
import com.lvmama.comm.bee.service.ord.OrderPerformService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.ord.OrdOrderPerformResourceVO;
import com.lvmama.comm.pet.po.ebkpush.EbkPushMessage;
import com.lvmama.comm.pet.po.ebkpush.ModelUtils;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.push.model.ClientSessionInfo;
import com.lvmama.push.model.PushDevice;
import com.lvmama.push.model.PushMessage;
import com.lvmama.push.model.SessionManager;
import com.lvmama.push.processer.CallbackMsg;
import com.lvmama.push.util.ConstantPush;
import com.lvmama.push.util.SyncLogicUtils;
import com.lvmama.push.util.ZipUtil2;
@Results({@Result(name = "deviceList", location = "/WEB-INF/pages/device/device_list.jsp"),
	@Result(name = "sessionList", location = "/WEB-INF/pages/session/list.jsp")})
public class DeviceManagerAction extends BaseAction{
	 private static final Logger LOGGER = LoggerFactory.getLogger(DeviceManagerAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 198393183691457474L;

	private EbkUserService ebkUserService;
	
	private PassCodeService passCodeService;

	private IEbkPushService ebkPushService;

	private String command;
	
	private String addCode;
	
	private String date;
	
	private String udid;
	
	private String notificationMsg;
	private OrderPerformService orderPerformProxy;
	@Action("/manager/device")
	public String index() {
		return "deviceList";
	}
	
	@Action("/manager/loadDevices")
	public void loadDevices(){
		
		List<PassDevice> list = 	passCodeService.getDeviceListByProviderId(Long.valueOf(ConstantPush.getProperty("push.qr")));
		List<PassDevice> list2 = 	passCodeService.getDeviceListByProviderId(Long.valueOf(ConstantPush.getProperty("push.aw")));
		list.addAll(list2);
		List<PushDevice> rows = new ArrayList<PushDevice>();
		for (PassDevice passDevice : list) {
			ClientSessionInfo csi = SessionManager.getInstance().getSessions().get(passDevice.getDeviceNo());
			
			PushDevice pushDevice = new PushDevice();
			pushDevice.setUdid(passDevice.getDeviceNo());
			String path =this.getRequest().getContextPath();
			if(csi==null||!csi.isOnline()){
				pushDevice.setIsOnline("<img src='"+path+"/images/user-offline.png'>不在线");
			
			} else if(csi!=null){
				pushDevice.setRemoteIp(csi.getRemoteIp());
				pushDevice.setIsOnline("<img src='"+path+"/images/user-online.png'>在线");
				EbkUser ebkUser = 	ebkUserService.getEbkUserById(Long.valueOf(csi.getUserId()));
				Long todayNum = ebkPushService.countTodayMsgByDeviceId(passDevice.getDeviceNo());
				Long timeOutNum = ebkPushService.countTodyTimeOutMsgByDeviceId(passDevice.getDeviceNo());
				pushDevice.setNetWorkType(csi.getNetWorkType());
				pushDevice.setTodayMstNum(todayNum);
				pushDevice.setMsgTimeOutNum(timeOutNum);
				pushDevice.setAdminName(ebkUser.getUserName());
				rows.add(pushDevice);
			}
			
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rows", rows);
		this.sendAjaxMsg(JSONObject.fromObject(map).toString());
	}
	
	@Action("/manager/pushMsg")
	public void pushMsg(){
		
	}
	@Action("/manager/asyncNewDevice")
	public void asyncNewDevice(){
		Map<String, String> params = new HashMap<String, String> (); 
		params.put("udid", udid); 
		ClientSessionInfo csi = SessionManager.getInstance().getSessions().get(udid);
		if(csi!=null&&csi.isOnline()){
		CallbackMsg msg = new CallbackMsg("1","命令已发送");
		JSONObject callJsonObject = JSONObject.fromObject(msg);
		this.sendAjaxMsg(callJsonObject.toString());
		List<String> items = passCodeService.getAddCodesByEBK(params);
		List<PushMessage> datas = new ArrayList<PushMessage>();
	
		List<Long> pushIds = new ArrayList<Long>();
		List<OrdOrderPerformResourceVO> performResourcesList = new ArrayList<OrdOrderPerformResourceVO>();
		
		for (int i=0;i<items.size();i++) {
			String addCode = items.get(i);
			EbkPushMessage epm = new EbkPushMessage();
			PushMessage pm = new PushMessage(command);
			pm.setAddCode(addCode);
			epm.setCommand("PULL");
			epm.setCommandType("PASSCODE_APPLY_SUCCESS");
			epm.setIsSuccess("N");
			epm.setDeviceId(udid);
			epm.setAddInfo(addCode);
			Long pushId = ebkPushService.getMessageIdSeq();
			epm.setId(pushId);
			JSONObject json = JSONObject.fromObject(pm);
			epm.setPushContent(json.toString());
			ebkPushService.insertEbkPushMessage(epm);	
			datas.add(pm);

			pushIds.add(epm.getId());
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("udid", csi.getUdid());
			param.put("addCode", addCode);
			List<OrdOrderPerformResourceVO> performResources = orderPerformProxy.queryOrderPerformByEBK(param);
			if (performResources!=null &&performResources.size()!=0){
				performResourcesList.add(performResources.get(0));
			}
		}
		

		
		
		IoSession session = csi.getSession();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("command", ConstantPush.PUSH_MSG_TYPE.SYNC_DATAS.name());
		result.put("datas", ModelUtils.buildSendDatas(performResourcesList));
		SyncLogicUtils.sync(session, result, pushIds, csi.getUdid());
		
	} else {
		CallbackMsg msg = new CallbackMsg("-1","设备不在线");
		JSONObject jsonObject = JSONObject.fromObject(msg);
		this.sendAjaxMsg(jsonObject.toString());
	}

	}
	
	@Action("/manager/executeCommand")
	public void executeCommand() {
		
		ClientSessionInfo csi = SessionManager.getInstance().getSessions().get(udid);
		if(csi!=null&&csi.isOnline()){
			EbkPushMessage epm = new EbkPushMessage();
			PushMessage pm = new PushMessage(command);
			if(ConstantPush.PUSH_MSG_TYPE.PULL.name().equals(command)){
				pm.setAddCode(addCode);
			} else if(ConstantPush.PUSH_MSG_TYPE.DELET_HISTORY_ORDER.name().equals(command)){
				pm.setDateStr(date);
			} else if(ConstantPush.PUSH_MSG_TYPE.NOTIFICATION.name().equals(command)){
				pm.setNotificationMsg(notificationMsg);
			} else if(ConstantPush.PUSH_MSG_TYPE.RESTART_DEVICE.name().equals(command)){
				
			} else if(ConstantPush.PUSH_MSG_TYPE.UPLOAD_LOG.name().equals(command)){
				pm.setDateStr(date);
			}
			epm.setCommand(command);
			epm.setCommandType("BACK_OPERATOR");
			epm.setDeviceId(udid);
			Long pushId = ebkPushService.getMessageIdSeq();
			epm.setId(pushId);
			pm.setPushId(String.valueOf(pushId));

			JSONObject jsonObject = JSONObject.fromObject(pm);
			String json  = jsonObject.toString();
			LOGGER.info("json is "+json);
			epm.setPushContent(json);
			ebkPushService.insertEbkPushMessage(epm);
			
			List<Long> pushIds = new ArrayList<Long>();
			pushIds.add(epm.getId());
			
			String fingerSource = jsonObject.toString();
			String callBackMD5ID="";
			try {
				callBackMD5ID = MD5.encode(json.toString());
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			csi.getSession().setAttribute(callBackMD5ID, pushIds);
			
			WriteFuture future = null;
			try {
				future = csi.getSession().write(ZipUtil2.compress(fingerSource));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			future.addListener(new IoFutureListener<WriteFuture>() {

				@Override
				public void operationComplete(WriteFuture future) {
					// TODO Auto-generated method stub
					if (future.isWritten()){
						LOGGER.info("Written success");
						
						
					} else {
						LOGGER.info("Written failure");
					}
				}
			});
			CallbackMsg msg = new CallbackMsg("1","命令已发送");
			JSONObject callJsonObject = JSONObject.fromObject(msg);
			this.sendAjaxMsg(callJsonObject.toString());
		} else {
			CallbackMsg msg = new CallbackMsg("-1","设备不在线");
			JSONObject jsonObject = JSONObject.fromObject(msg);
			this.sendAjaxMsg(jsonObject.toString());
		}
		
	}
	@Action("/manager/viewMSG")
	public void viewMSG(){
		Map<String, String> params = new HashMap<String, String> ();
		if(!StringUtils.isEmpty(udid)){
			params.put("udid", udid);
		}
		if(!StringUtils.isEmpty(date)){
			params.put("date", date);
		}else{
			params.put("date", DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		}
		List<EbkPushMessage> pms = ebkPushService.selectPushMsg(params);
		this.sendAjaxResultByJson(JSONArray.fromObject(pms).toString());
	}
	
	@Action("/manager/deleteHistoryDate")
	public void deleteHistoryDate(){
		int count = ebkPushService.deleteHistoryDate(udid);
	}
	
	public void syncDateForNewDevice(){
		
	}
	
	public void setEbkUserService(EbkUserService ebkUserService) {
		this.ebkUserService = ebkUserService;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public void setEbkPushService(IEbkPushService ebkPushService) {
		this.ebkPushService = ebkPushService;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getAddCode() {
		return addCode;
	}

	public void setAddCode(String addCode) {
		this.addCode = addCode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNotificationMsg() {
		return notificationMsg;
	}

	public void setNotificationMsg(String notificationMsg) {
		this.notificationMsg = notificationMsg;
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public void setOrderPerformProxy(OrderPerformService orderPerformProxy) {
		this.orderPerformProxy = orderPerformProxy;
	}
}
