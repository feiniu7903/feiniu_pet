package com.lvmama.back.sweb.user.message;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.pet.po.pub.ComAnnouncement;
import com.lvmama.comm.pet.po.pub.ComMessage;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.utils.DateUtil;
/**
 * 工具栏提醒是否消息、任务、公告过来，进行进度条变色提醒.
 * @author huangli
 *
 */
@Results({
	@Result(name = "index", type="redirect", location = "index.do")
	})
@SuppressWarnings("unchecked")
public class ViewMessageAction extends BaseAction {
	private static final String UNCHECKED = "unchecked";
	/**
	 * 消息service.
	 */
	private ComMessageService comMessageService;
	private String sysMessage;
	
	@Action("/msg/initMessage")
	public void initMessage(){
		if(super.isLogined()) {
			try {
				sysMessage="";
				this.queryMessage();
				this.queryAnnounce();
				this.queryTask();
				sendAjaxResult(sysMessage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	}
	/**
	 * 查询当前发送给自己的公告.
	 * @return
	 */
	public void queryAnnounce(){
		Map searchMessageMap=new HashMap();
		searchMessageMap.put("msg_skipResults",0);
		searchMessageMap.put("msg_maxResults",5);
		searchMessageMap.put("userId",getSessionUser().getUserId());
//		searchMessageMap.put("loginName",this.getOperatorName());
		searchMessageMap.put("expiredTime", DateUtil.getDateTime("yyyy-MM-dd HH:mm", new Date()));
		List list=this.comMessageService.queryComAnnouncementByUserIdOrgId(searchMessageMap);
		for (int i = 0; i < list.size(); i++) {
				ComAnnouncement comAnnouncement=(ComAnnouncement)list.get(i);
				sysMessage+=" <div style='display:block;'><a  id='"+comAnnouncement.getAnnounGroupId()+"_announ' href='javascript:jumpUrl(\"log/viewAnnounceQuery.zul\");'>"+comAnnouncement.getSub20Content()+"</a></div></br>";
		}
	}
	/**
	 * 查询当前发送给自己的任务.
	 * @return
	 */
	public void queryTask(){
		Map searchMessageMap=new HashMap();
		searchMessageMap.put("skipResults",0);
		searchMessageMap.put("maxResults",5);
		searchMessageMap.put("receiver", this.getOperatorName());
		searchMessageMap.put("status","CREATE");
		searchMessageMap.put("notNullBeginTime","not null");
		searchMessageMap.put("beginStartDate",new Date());
		searchMessageMap.put("beginEndDate",DateUtil.DsDay_Minute(new Date(), 5));
		List<ComMessage> list=this.comMessageService.queryComMessageByParam(searchMessageMap);
		if(CollectionUtils.isEmpty(list))
			return;
		for (ComMessage comMessage:list) {	
				sysMessage+=" <div style='display:block;'><a  id='"+comMessage.getMessageId()+"_msg' href='javascript:jumpUrl(\"log/viewTaskQuery.zul\");'>"+comMessage.getSub20Content()+"</a>";
				sysMessage+=" <a class='floatRight' href='#' id='"+comMessage.getMessageId()+"_finish' onClick='updateMsgRecreiver("+comMessage.getMessageId()+")'>不再提醒</a></div></br>";
		}
	}
	
	/**
	 * 查询当前发送给自己的消息.
	 * @return
	 */
	public void queryMessage(){
		Map searchMessageMap=new HashMap();
		searchMessageMap.put("skipResults",0);
		searchMessageMap.put("maxResults",5);
		searchMessageMap.put("receiver", this.getOperatorName());
		searchMessageMap.put("status","CREATE");
		searchMessageMap.put("nullBeginTime","null");
		List<ComMessage> list=this.comMessageService.queryComMessageByParam(searchMessageMap);
		if(CollectionUtils.isEmpty(list))
			return;
		for (ComMessage comMessage:list) {				
				sysMessage+="<div style='display:block;'> <a  id='"+comMessage.getMessageId()+"_msg' href='javascript:jumpUrl(\"/super_back/log/viewMessageQuery.zul\");'>"+comMessage.getSub20Content()+"</a>";
				sysMessage+=" <a class='floatRight' href='#' id='"+comMessage.getMessageId()+"_finish' onClick='updateMsgRecreiver("+comMessage.getMessageId()+")'>不再提醒</a></div></br>";
		}
	}
	
	@Action("/msg/updateMsgRecreiver")
	public void updateMsgRecreiver(){
		String msgId=this.getRequest().getParameter("msgId");
		ComMessage comMessage=this.comMessageService.getComMessageByPk(Long.valueOf(msgId));
		comMessage.setStatus("FINISHED");
		this.comMessageService.updateComMessage(comMessage);
	}

	public ComMessageService getComMessageService() {
		return comMessageService;
	}
	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}
	public String getSysMessage() {
		return sysMessage;
	}
	public void setSysMessage(String sysMessage) {
		this.sysMessage = sysMessage;
	}
}
