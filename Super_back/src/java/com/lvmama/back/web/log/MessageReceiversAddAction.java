package com.lvmama.back.web.log;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.pub.ComMessageReceivers;
import com.lvmama.comm.pet.service.pub.ComMessageService;

/**
 * 跳转显示系统警告新增对像.
 * 
 * @author huangl
 */
public class MessageReceiversAddAction  extends BaseAction{
	/**
	 * 消息service.
	 */
	private ComMessageService comMessageService;
	/**
	 * 操作对像.
	 */
	private ComMessageReceivers comMessageReceivers; 
	/**
	 * 编号.
	 */
	private Long messageTypeId;
	@SuppressWarnings("unchecked")
	public void doBefore(){
		Map paramMap=new HashMap();
		paramMap.put("messageTypeId", messageTypeId);
		paramMap.put("skipResults",0);
		paramMap.put("maxResults",1);
		comMessageReceivers=comMessageService.queryComMessageReceiverByParam(paramMap).get(0);
	}
	
	public void updateMsgReceiver(ComMessageReceivers comMessageReceivers){
		this.comMessageService.updateComMessageReceiversByPK(comMessageReceivers);
		this.refreshParent("search");
		this.closeWindow();
	}

	public ComMessageService getComMessageService() {
		return comMessageService;
	}

	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}

	public ComMessageReceivers getComMessageReceivers() {
		return comMessageReceivers;
	}

	public void setComMessageReceivers(ComMessageReceivers comMessageReceivers) {
		this.comMessageReceivers = comMessageReceivers;
	}

	public Long getMessageTypeId() {
		return messageTypeId;
	}

	public void setMessageTypeId(Long messageTypeId) {
		this.messageTypeId = messageTypeId;
	}

}
