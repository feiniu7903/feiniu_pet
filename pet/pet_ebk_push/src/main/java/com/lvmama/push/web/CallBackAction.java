package com.lvmama.push.web;


import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.bee.service.ebkpush.IEbkPushService;
import com.lvmama.comm.pet.po.ebkpush.EbkPushMessage;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.push.processer.CallbackMsg;

public class CallBackAction extends BaseAction{
	 private static final Logger LOGGER = LoggerFactory.getLogger(CallBackAction.class);
	/**
	 * 消息回调接口
	 */
	private static final long serialVersionUID = -8377915882645787102L;
	
	private String pushId;
	
	private IEbkPushService ebkPushService;
	
	@Action("/callback")
	public void callback(){
		LOGGER.info("收到pushId:"+pushId);
		if(!StringUtil.isEmptyString(pushId)){
			EbkPushMessage epm = ebkPushService.selectMessageByPK(Long.valueOf(pushId));
			if(epm!=null){
				epm.setIsSuccess("Y");
			
				ebkPushService.updateEbkPushMessage(epm);
				LOGGER.info("更新状态pushId:"+pushId);
				CallbackMsg msg = new CallbackMsg("0","success");
				JSONObject jsonObject = JSONObject.fromObject(msg);
				this.sendAjaxMsg(jsonObject.toString());
			} else {
				CallbackMsg msg = new CallbackMsg("-1","error");
				JSONObject jsonObject = JSONObject.fromObject(msg);
				this.sendAjaxMsg(jsonObject.toString());
			}
		} else {
			CallbackMsg msg = new CallbackMsg("-1","pushId is required");
			JSONObject jsonObject = JSONObject.fromObject(msg);
			this.sendAjaxMsg(jsonObject.toString());
		}
	}
	
	public String getPushId() {
		return pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}

	public void setEbkPushService(IEbkPushService ebkPushService) {
		this.ebkPushService = ebkPushService;
	}

}
